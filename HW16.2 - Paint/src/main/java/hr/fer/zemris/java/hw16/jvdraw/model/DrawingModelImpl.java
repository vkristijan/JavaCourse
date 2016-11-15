package hr.fer.zemris.java.hw16.jvdraw.model;

import java.awt.Rectangle;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * Implementation of the {@link DrawingModel}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class DrawingModelImpl implements DrawingModel {
	
	/** The objects. */
	private List<GeometricalObject> objects;
	
	/** The listeners. */
	private List<DrawingModelListener> listeners;
	
	/** The path. */
	private Path path;

	/**
	 * Boolean flag used to remember if the document was changed without saving.
	 */
	private boolean hasChanged;
	
	/**
	 * Instantiates a new drawing model.
	 */
	public DrawingModelImpl() {
		objects = new ArrayList<>();
		listeners = new ArrayList<>();
	}

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		if (index < 0 || index >= getSize()){
			throw new IndexOutOfBoundsException("No element with the given index exists.");
		}
		
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		hasChanged = true;
		
		objects.add(object);
		int index = objects.indexOf(object);
		
		List<DrawingModelListener> listenersTmp = new ArrayList<>(listeners);
		for (DrawingModelListener listener : listenersTmp){
			listener.objectsAdded(this, index, index);
		}
	}

	@Override
	public void remove(GeometricalObject object) {
		hasChanged = true;
		
		int index = objects.indexOf(object);
		if (index < 0){
			return;
		}
		
		objects.remove(object);
		
		List<DrawingModelListener> listenersTmp = new ArrayList<>(listeners);
		for (DrawingModelListener listener : listenersTmp){
			listener.objectsRemoved(this, index, index);
		}
	}
	
	@Override
	public void change(GeometricalObject object) {
		hasChanged = true;
		
		int index = objects.indexOf(object);
		if (index < 0){
			return;
		}
		
		List<DrawingModelListener> listenersTmp = new ArrayList<>(listeners);
		for (DrawingModelListener listener : listenersTmp){
			listener.objectsChanged(this, index, index);
		}
	}
	
	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public boolean hasChanged() {
		return hasChanged;
	}

	@Override
	public Path getPath() {
		return path;
	}

	@Override
	public void setPath(Path path) {
		this.path = path;
	}

	@Override
	public void save() {
		StringBuilder data = new StringBuilder();
		
		for (GeometricalObject object : objects){
			data.append(object.export()).append("\n");
		}
		String text = data.toString();
		
		try {
			Files.write(path, text.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			System.err.println("Unable to write file!");
		}
		
		hasChanged = false;
	}

	@Override
	public void clear() {
		objects.clear();
		
		List<DrawingModelListener> listenersTmp = new ArrayList<>(listeners);
		for (DrawingModelListener listener : listenersTmp){
			listener.objectsRemoved(this, 0, 0);
		}
	}

	@Override
	public void setChanged(boolean hasChanged) {
		this.hasChanged = hasChanged;
	}

	@Override
	public Rectangle getBoundingBox() {
		Rectangle box = null;
		
		for (GeometricalObject object : objects){
			if (box == null){
				box = object.getBoundingBox();
			} else {
				Rectangle tmpBox = object.getBoundingBox();
				
				box.x = Math.min(box.x, tmpBox.x);
				box.y = Math.min(box.y, tmpBox.y);
				box.width = Math.max(box.width, tmpBox.width);
				box.height = Math.max(box.height, tmpBox.height);
			}
		}
		
		return box;
	}


}
