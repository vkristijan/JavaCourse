package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This program is a a simplified version of a ray-tracer for rendering of 3D
 * scenes.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class RayCaster {
	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
			new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Creates a new {@link IRayTracerProducer} that will be used to show the
	 * objects on the screen.
	 * 
	 * @return new {@link IRayTracerProducer} that will be used to show the
	 *         objects on the screen.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducerImpl();
	}

	/**
	 * An implementation of {@link IRayTracerProducer} that uses
	 * <a href="https://en.wikipedia.org/wiki/Phong_reflection_model">Phong
	 * reflection model</a> for object shading.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 *
	 */
	private static class IRayTracerProducerImpl implements IRayTracerProducer {
		@Override
		public void produce(
			Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width, int height,
			long requestNo, IRayTracerResultObserver observer) {
			System.out.println("Započinjem izračune...");
			short[] red = new short[width * height];
			short[] green = new short[width * height];
			short[] blue = new short[width * height];

			Point3D zAxis = calculateZ(eye, view);
			Point3D yAxis = calculateY(zAxis, viewUp);
			Point3D xAxis = calculateX(zAxis, yAxis);

			Point3D screenCorner = calculateCorner(xAxis, yAxis, view, horizontal, vertical);
			Scene scene = RayTracerViewer.createPredefinedScene();

			ForkJoinPool pool = new ForkJoinPool();
			calculate(scene, eye, screenCorner, xAxis, yAxis, width, height, horizontal, vertical, red, green, blue,
				pool);

			System.out.println("Izračuni gotovi...");
			observer.acceptResult(red, green, blue, requestNo);
			System.out.println("Dojava gotova...");
		}

		/**
		 * Calculates the lightning for the objects on the given scene, using
		 * multiple processor cores.
		 * 
		 * @param scene
		 *            the {@link Scene} that contains the
		 *            {@link GraphicalObject}s and {@link LightSource}s.
		 * @param eye
		 *            the coordinates of the watcher.
		 * @param screenCorner
		 *            the coordinates of the corner of the screen.
		 * @param xAxis
		 *            the xAxis of the coordinate system.
		 * @param yAxis
		 *            the yAxis of the coordinate system.
		 * @param width
		 *            the width of the screen in pixels.
		 * @param height
		 *            the height of the screen in pixels.
		 * @param horizontal
		 *            the width of the screen.
		 * @param vertical
		 *            the height of the screen.
		 * @param red
		 *            array of intensity values for the red color. Allowed
		 *            values are from 0 to 255.
		 * @param green
		 *            array of intensity values for the green color. Allowed
		 *            values are from 0 to 255.
		 * @param blue
		 *            array of intensity values for the blue color. Allowed
		 *            values are from 0 to 255.
		 * @param pool
		 *            the {@link ForkJoinPool} that is used in this calculation.
		 */
		private void calculate(
			Scene scene, Point3D eye, Point3D screenCorner, Point3D xAxis, Point3D yAxis, int width, int height,
			double horizontal, double vertical, short[] red, short[] green, short[] blue, ForkJoinPool pool) {

			/**
			 * A <code>Recursive action</code> implementation that is used to
			 * calculate the lighting of the objects on a scene. The calculation
			 * will be executed multi-threaded.
			 * 
			 * @author Kristijan Vulinovic
			 * @version 1.0
			 */
			class Job extends RecursiveAction {
				/**
				 * The default serial version id number.
				 */
				private static final long serialVersionUID = -4149093524895564400L;
				/**
				 * The terminal difference between the lower and upper bound.
				 * Once the difference between them is less than the terminal
				 * number, the calculation will be done directly without further
				 * recursion calls.
				 */
				private static final int TERMINAL_NUMBER = 32;
				/**
				 * The lower bound for the height in the calculation. (included
				 * in the calculation)
				 */
				private int from;
				/**
				 * The upper bound for the height in the calculation. (not
				 * included in the calculation)
				 */
				private int to;

				/**
				 * Creates a new job that will calculate the values for heights
				 * in range from <code>from</code> to <code>to</code>
				 * 
				 * @param from
				 *            the lower bound for the height in the calculation.
				 * @param to
				 *            the upper bound for the height in the calculation.
				 */
				public Job(int from, int to) {
					this.from = from;
					this.to = to;
				}

				/**
				 * The computation method that will calculate the result
				 * directly.
				 */
				private void computeDirect() {
					short[] rgb = new short[3];
					int offset = from * width;
					for (int y = from; y < to; y++) {
						for (int x = 0; x < width; x++) {
							Point3D screenPoint = calculatePoint(screenCorner, xAxis, yAxis, x, y, width, height,
								horizontal, vertical);
							Ray ray = Ray.fromPoints(eye, screenPoint);

							tracer(scene, ray, rgb);

							red[offset] = rgb[0] > 255 ? 255 : rgb[0];
							green[offset] = rgb[1] > 255 ? 255 : rgb[1];
							blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
							offset++;
						}
					}
				}

				@Override
				protected void compute() {
					if (to - from <= TERMINAL_NUMBER) {
						computeDirect();
						return;
					}

					Job job1 = new Job(from, (from + to) / 2);
					Job job2 = new Job((from + to) / 2, to);
					invokeAll(job1, job2);
				}

			}

			Job job = new Job(0, height);
			pool.invoke(job);
		}

		/**
		 * Calculates the coordinate of a point on the screen, from the given x
		 * and y coordinate used on the screen coordinate system.
		 * 
		 * @param corner
		 *            the coordinate of the corner of the screen.
		 * @param xAxis
		 *            the xAxis of the coordinate system.
		 * @param yAxis
		 *            the yAxis of the coordinate system.
		 * @param x
		 *            the x coordinate of the point on the screen.
		 * @param y
		 *            the y coordinate of the point on the screen.
		 * @param width
		 *            the width of the screen in pixels.
		 * @param height
		 *            the height of the screen in pixels.
		 * @param horizontal
		 *            the width of the screen.
		 * @param vertical
		 *            the height of the screen.
		 * @return the coordinate of a point on the screen.
		 */
		private Point3D calculatePoint(
			Point3D corner, Point3D xAxis, Point3D yAxis, int x, int y, int width, int height, double horizontal,
			double vertical) {

			Point3D horizontalP = xAxis.scalarMultiply(((double) x / (width - 1)) * horizontal);
			Point3D verticalP = yAxis.scalarMultiply(((double) y / (height - 1)) * vertical);

			Point3D point = corner.add(horizontalP);
			point = point.sub(verticalP);
			return point;
		}

		/**
		 * Calculates the coordinate of the corner of the screen.
		 * 
		 * @param xAxis
		 *            the xAxis of the coordinate system.
		 * @param yAxis
		 *            the yAxis of the coordinate system.
		 * @param view
		 *            the coordinates of the screen.
		 * @param horizontal
		 *            the width of the screen.
		 * @param vertical
		 *            the height of the screen.
		 * @return the coordinate of the corner of the screen.
		 */
		private Point3D calculateCorner(
			Point3D xAxis, Point3D yAxis, Point3D view, double horizontal, double vertical) {
			Point3D horizontalP = xAxis.scalarMultiply(horizontal / 2);
			Point3D verticalP = yAxis.scalarMultiply(vertical / 2);

			Point3D corner = view.sub(horizontalP);
			corner = corner.add(verticalP);
			return corner;
		}

		/**
		 * Calculates the xAxis of the coordinate system.
		 * 
		 * @param zAxis
		 *            the zAxis of the coordinate system.
		 * @param yAxis
		 *            the yAxis of the coordinate system.
		 * @return the xAxis of the coordinate system.
		 */
		private Point3D calculateX(Point3D zAxis, Point3D yAxis) {
			Point3D vector = zAxis.vectorProduct(yAxis);
			vector = vector.normalize();
			return vector;
		}

		/**
		 * Calculates the yAxis of the coordinate system.
		 * 
		 * @param zAxis
		 *            the zAxis of the coordinate system.
		 * @param viewUp
		 *            a vector defining the up view.
		 * @return the yAxis of the coordinate system.
		 */
		private Point3D calculateY(Point3D zAxis, Point3D viewUp) {
			viewUp = viewUp.normalize();
			Point3D vector = zAxis.scalarMultiply(zAxis.scalarProduct(viewUp));
			vector = viewUp.sub(vector);
			vector = vector.normalize();
			return vector;
		}

		/**
		 * Calculates the zAxis of the coordinate system.
		 * 
		 * @param eye
		 *            the coordinates of the watcher.
		 * @param view
		 *            the coordinates of the screen.
		 * @return the zAxis of the coordinate system.
		 */
		private Point3D calculateZ(Point3D eye, Point3D view) {
			Point3D vector = view.sub(eye);
			vector = vector.normalize();
			return vector;
		}

		/**
		 * Calculates the light for the given ray.
		 * 
		 * @param scene
		 *            the scene with the {@link GraphicalObject}s ans
		 *            {@link LightSource}s.
		 * @param ray
		 *            the ray from the intersection to the screen pixel.
		 * @param rgb
		 *            array containing 3 values. The values for the colors red,
		 *            green and blue.
		 */
		private void tracer(Scene scene, Ray ray, short[] rgb) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;

			RayIntersection intersection = findClosestIntersection(scene, ray);
			if (intersection == null) return;

			rgb[0] = 15;
			rgb[1] = 15;
			rgb[2] = 15;

			List<LightSource> lights = scene.getLights();
			for (LightSource light : lights) {
				Ray lightRay = Ray.fromPoints(intersection.getPoint(), light.getPoint());

				if (findClosestIntersection(scene, lightRay) != null) {
					continue;
				}

				calculateDiffuseComponent(rgb, lightRay, intersection, light);
				calculateReflectedComponent(rgb, ray, lightRay, intersection, light);
			}
		}

		/**
		 * Calculates the colors for the diffused component of light.
		 * 
		 * @param rgb
		 *            array containing 3 values. The values for the colors red,
		 *            green and blue.
		 * @param lightRay
		 *            the ray from the intersection to the light source.
		 * @param intersection
		 *            the {@link RayIntersection} for which the component should
		 *            be calculated.
		 * @param light
		 *            the {@link LightSource} used for the calculation.
		 */
		private void calculateDiffuseComponent(
			short[] rgb, Ray lightRay, RayIntersection intersection, LightSource light) {

			Point3D normal = intersection.getNormal();
			double coef = lightRay.direction.normalize().scalarProduct(normal);
			coef = Math.max(coef, 0);
			rgb[0] += light.getR() * intersection.getKdr() * coef;
			rgb[1] += light.getG() * intersection.getKdg() * coef;
			rgb[2] += light.getB() * intersection.getKdb() * coef;
		}

		/**
		 * Calculates the colors for the reflected component of light.
		 * 
		 * @param rgb
		 *            array containing 3 values. The values for the colors red,
		 *            green and blue.
		 * @param ray
		 *            the ray from the intersection to the screen pixel.
		 * @param lightRay
		 *            the ray from the intersection to the light source.
		 * @param intersection
		 *            the {@link RayIntersection} for which the component should
		 *            be calculated.
		 * @param light
		 *            the {@link LightSource} used for the calculation.
		 */
		private void calculateReflectedComponent(
			short[] rgb, Ray ray, Ray lightRay, RayIntersection intersection, LightSource light) {
			Point3D normal = intersection.getNormal();
			Point3D lightDirection = lightRay.direction.normalize();
			double coef = 2 * normal.scalarProduct(lightDirection);
			Point3D reflected = normal.scalarMultiply(coef).sub(lightDirection);
			Point3D v = ray.direction.negate();

			coef = reflected.scalarProduct(v);
			coef = Math.max(coef, 0);
			coef = Math.pow(coef, intersection.getKrn());
			rgb[0] += light.getR() * intersection.getKrr() * coef;
			rgb[1] += light.getG() * intersection.getKrg() * coef;
			rgb[2] += light.getB() * intersection.getKrb() * coef;
		}

		/**
		 * Finds the closest {@link GraphicalObject} in front of the given
		 * {@link Ray}.
		 * 
		 * @param scene
		 *            the scene with the {@link GraphicalObject}s.
		 * @param ray
		 *            the {@link Ray} that should be used.
		 * @return closest intersection of this object and given ray that is in
		 *         front of observer of null if such intersection does not
		 *         exists.
		 */
		private RayIntersection findClosestIntersection(Scene scene, Ray ray) {
			RayIntersection intersection = null;
			Double bestDistance = null;

			List<GraphicalObject> objects = scene.getObjects();
			for (GraphicalObject object : objects) {

				RayIntersection tmpIntersection = object.findClosestRayIntersection(ray);
				if (tmpIntersection == null) continue;

				if (bestDistance == null) {
					bestDistance = tmpIntersection.getDistance();
					intersection = tmpIntersection;
				} else {
					double distance = tmpIntersection.getDistance();

					if (distance < bestDistance) {
						bestDistance = distance;
						intersection = tmpIntersection;
					}
				}
			}

			return intersection;
		}

	}
}
