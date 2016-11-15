package hr.fer.zemris.java.graphics;

import java.util.Scanner;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Circle;
import hr.fer.zemris.java.graphics.shapes.Ellipse;
import hr.fer.zemris.java.graphics.shapes.GeometricShape;
import hr.fer.zemris.java.graphics.shapes.Rectangle;
import hr.fer.zemris.java.graphics.shapes.Square;
import hr.fer.zemris.java.graphics.views.RasterView;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;

/**
 * A simple demonstration class for the {@code GeometricShape}s,
 * {@code RasterView} and {@code BWRaster}. <br>
 * Allows the user to specify the size of a raster using command line arguments.
 * It's allowed to give just one argument that is both the width and height of
 * the raster, or two arguments where the first one is the width and second one
 * the height of the raster. <br>
 * In the first line of input the user is expected to write the number of
 * commands that he wants to execute.<br>
 * In the following lines the user can write one of the following commands: <br>
 * <ul>
 * <li><strong>flip</strong> to change the flip state of the raster</li>
 * <li><strong>square</strong> followed with three numbers to draw a square with
 * the given arguments</li>
 * <li><strong>rectangle</strong> followed with four numbers to draw a rectangle
 * with the given arguments</li>
 * <li><strong>circle</strong> followed with three numbers to draw a circle with
 * the given arguments</li>
 * <li><strong>ellipse</strong> followed with four numbers to draw a ellipse with
 * the given arguments</li>
 * </ul>
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class Demo {

	/**
	 * The program entry point.
	 * 
	 * @param args
	 *            the command line arguments.
	 */
	public static void main(String[] args) {
		try {
			BWRaster raster = createRaster(args);
			Scanner sc = new Scanner(System.in);
			GeometricShape[] shapes = getShapes(sc);
			showshapes(raster, shapes);
		} catch (Exception e) {
			System.out.println("There was an error with the input!");
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Shows the shapes using the given raster.
	 * 
	 * @param raster
	 *            the raster on which the shapes should be printed.
	 * @param shapes
	 *            the shapes to be printed on the raster.
	 */
	private static void showshapes(BWRaster raster, GeometricShape[] shapes) {
		boolean isFlipped = false;
		for (GeometricShape shape : shapes) {
			if (shape == null) {
				if (isFlipped) {
					raster.disableFlipMode();
					isFlipped = false;
				} else {
					raster.enableFlipMode();
					isFlipped = true;
				}
			} else {
				shape.draw(raster);
			}
		}

		RasterView view = new SimpleRasterView('*', '.');
		view.produce(raster);
	}

	/**
	 * Creates a list of {@code GeometricShape}s by reading the parameters from
	 * the scanner given as argument.
	 * 
	 * @param sc
	 *            the {@code Scanner} that is used to read the arguments.
	 * @return a list of {@code GeometricShape}s.
	 */
	private static GeometricShape[] getShapes(Scanner sc) {
		int count = sc.nextInt();
		sc.nextLine();
		GeometricShape[] shapes = new GeometricShape[count];

		for (int i = 0; i < count; ++i) {
			String line = sc.nextLine();
			line.trim();

			String[] lineArgs = line.split(" +");
			if (lineArgs[0].toUpperCase().equals("FLIP")) {
				shapes[i] = null;
			} else if (lineArgs[0].toUpperCase().equals("SQUARE")) {
				shapes[i] = getSquare(lineArgs);
			} else if (lineArgs[0].toUpperCase().equals("RECTANGLE")) {
				shapes[i] = getRectangle(lineArgs);
			} else if (lineArgs[0].toUpperCase().equals("CIRCLE")) {
				shapes[i] = getCircle(lineArgs);
			} else if (lineArgs[0].toUpperCase().equals("ELLIPSE")) {
				shapes[i] = getEllipse(lineArgs);
			} else {
				throw new IllegalArgumentException("The given shape is not recognized!");
			}

		}

		return shapes;
	}

	/**
	 * Creates a new {@code Square} from the parameters in the line argument.
	 * 
	 * @param lineArgs
	 *            the arguments to create this {@code GeometricShape}
	 * @return the new created {@code GeometricShape}
	 */
	private static GeometricShape getSquare(String[] lineArgs) {
		if (lineArgs.length != 4) {
			throw new IllegalArgumentException("Wrong number of arguments for square!");
		}

		int x = Integer.parseInt(lineArgs[1]);
		int y = Integer.parseInt(lineArgs[2]);
		int size = Integer.parseInt(lineArgs[3]);

		return new Square(x, y, size);
	}

	/**
	 * Creates a new {@code Rectangle} from the parameters in the line argument.
	 * 
	 * @param lineArgs
	 *            the arguments to create this {@code GeometricShape}
	 * @return the new created {@code GeometricShape}
	 */
	private static GeometricShape getRectangle(String[] lineArgs) {
		if (lineArgs.length != 5) {
			throw new IllegalArgumentException("Wrong number of arguments for rectangle!");
		}

		int x = Integer.parseInt(lineArgs[1]);
		int y = Integer.parseInt(lineArgs[2]);
		int width = Integer.parseInt(lineArgs[3]);
		int height = Integer.parseInt(lineArgs[4]);

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Creates a new {@code Circle} from the parameters in the line argument.
	 * 
	 * @param lineArgs
	 *            the arguments to create this {@code GeometricShape}
	 * @return the new created {@code GeometricShape}
	 */
	private static GeometricShape getCircle(String[] lineArgs) {
		if (lineArgs.length != 4) {
			throw new IllegalArgumentException("Wrong number of arguments for circle!");
		}

		int x = Integer.parseInt(lineArgs[1]);
		int y = Integer.parseInt(lineArgs[2]);
		int radius = Integer.parseInt(lineArgs[3]);

		return new Circle(x, y, radius);
	}

	/**
	 * Creates a new {@code Ellipse} from the parameters in the line argument.
	 * 
	 * @param lineArgs
	 *            the arguments to create this {@code GeometricShape}
	 * @return the new created {@code GeometricShape}
	 */
	private static GeometricShape getEllipse(String[] lineArgs) {
		if (lineArgs.length != 5) {
			throw new IllegalArgumentException("Wrong number of arguments for ellipse!");
		}

		int x = Integer.parseInt(lineArgs[1]);
		int y = Integer.parseInt(lineArgs[2]);
		int horizontalRadius = Integer.parseInt(lineArgs[3]);
		int verticalRadius = Integer.parseInt(lineArgs[4]);

		return new Ellipse(x, y, horizontalRadius, verticalRadius);
	}

	/**
	 * Creates a {@code BWRaster} with the size specified in the args array.
	 * 
	 * @param args
	 *            string array containing the dimension for the {@code BWRaster}
	 * @return the new created {@code BWRaster}
	 */
	private static BWRaster createRaster(String[] args) {
		if (args.length == 1) {
			try {
				int height = Integer.parseInt(args[0]);
				return new BWRasterMem(height, height);
			} catch (NumberFormatException e) {
				System.out.println("Argument could not be recognized as number!");
				System.exit(1);
			}
		}

		if (args.length == 2) {
			try {
				int width = Integer.parseInt(args[0]);
				int height = Integer.parseInt(args[1]);
				return new BWRasterMem(width, height);
			} catch (NumberFormatException e) {
				System.out.println("Argument could not be recognized as number!");
				System.exit(1);
			}
		}

		System.out.println("Wrong number of arguments!");
		System.exit(1);
		return null;
	}

}
