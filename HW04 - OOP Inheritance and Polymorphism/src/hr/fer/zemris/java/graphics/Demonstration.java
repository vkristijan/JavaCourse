package hr.fer.zemris.java.graphics;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Circle;
import hr.fer.zemris.java.graphics.shapes.Ellipse;
import hr.fer.zemris.java.graphics.shapes.Rectangle;
import hr.fer.zemris.java.graphics.views.RasterView;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;

/**
 * A simple demonstration class for the {@code GeometricShape}s,
 * {@code RasterView} and {@code BWRaster}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class Demonstration {
	/**
	 * The program entry point.
	 * @param args console arguments
	 */
	public static void main(String[] args) {
		Rectangle rect1 = new Rectangle(0, 0, 4, 4);
		Rectangle rect2 = new Rectangle(1, 1, 2, 2);
		BWRaster raster = new BWRasterMem(6, 5);
		
		raster.enableFlipMode();
		rect1.draw(raster);
		rect2.draw(raster);
		
		RasterView view = new SimpleRasterView();
		
		view.produce(raster);
		view.produce(raster);
		
		System.out.println();
		RasterView view2 = new SimpleRasterView('X', '_');
		view2.produce(raster);
		
		BWRaster raster2 = new BWRasterMem(50, 50);
		
		Ellipse el1 = new Ellipse(25, 25, 5, 10);
		Circle cir = new Circle(5, 3, 10);
		el1.draw(raster2);
		cir.draw(raster2);
		
		view.produce(raster2);
	}

}
