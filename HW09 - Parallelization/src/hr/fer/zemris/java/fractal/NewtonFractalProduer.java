package hr.fer.zemris.java.fractal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractal.complex.Complex;
import hr.fer.zemris.java.fractal.complex.ComplexPolynomial;
import hr.fer.zemris.java.fractal.complex.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * Implementation of {@link IFractalProducer} capable of calculating the data
 * for <a href="http://en.wikipedia.org/wiki/Newton_fractal">Newton fractal</a>.
 * The method works on multiple cores of the processor.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class NewtonFractalProduer implements IFractalProducer {
	/**
	 * The threshold for convergence. Once the given threshold is met, the
	 * calculation will stop.
	 */
	private static double CONVERGENCE_TRESHOLD = 0.001;
	/**
	 * The threshold for finding the root.
	 */
	private static double ROOT_TRESHOLD = 0.002;
	/**
	 * The maximum number of iterations before the calculation process is
	 * terminated.
	 */
	private static double MAX_ITERATION = 64;

	/**
	 * The polynomial used to calculate the fractal.
	 */
	private ComplexPolynomial polynomial;
	/**
	 * The derivation of the polynomial used to calculate the fractal.
	 */
	private ComplexPolynomial derived;
	/**
	 * The polynomial used to calculate the fractal, but stored in the form of a
	 * {@link ComplexRootedPolynomial}.
	 */
	private ComplexRootedPolynomial rootedPolynomial;
	/**
	 * Thread pool used to assign jobs to threads calculating the fractals.
	 */
	private ExecutorService pool;

	/**
	 * Creates a new {@link NewtonFractalProduer}, that can compute the data for
	 * a fractal. The polynomial defining the fractal is given in the argument.
	 * 
	 * @param rootedPolynomial
	 *            the polynomial used to calculate the fractal.
	 * @throws NullPointerException
	 *             if the given {@link ComplexRootedPolynomial} is null.
	 */
	public NewtonFractalProduer(ComplexRootedPolynomial rootedPolynomial) {
		Objects.requireNonNull(rootedPolynomial);

		this.rootedPolynomial = rootedPolynomial;
		this.polynomial = rootedPolynomial.toComplexPolynom();
		this.derived = this.polynomial.derive();

		int numberOfCores = Runtime.getRuntime().availableProcessors();
		pool = Executors.newFixedThreadPool(numberOfCores, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			}
		});
	}

	@Override
	public void produce(
		double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
		IFractalResultObserver observer) {
		System.out.println("Zapocinjem izracun...");

		short[] data = new short[width * height];
		int numberOfCores = Runtime.getRuntime().availableProcessors();
		int numberOfJobs = numberOfCores * 8;

		int numberOfLines = height / numberOfJobs;

		List<Future<Void>> results = new ArrayList<>();
		for (int i = 0; i < numberOfJobs; i++) {
			int yMin = i * numberOfLines;
			int yMax = (i + 1) * numberOfLines - 1;
			if (i == numberOfLines - 1) {
				yMax = height - 1;
			}
			Execution job = new Execution(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data);
			results.add(pool.submit(job));
		}
		for (Future<Void> job : results) {
			try {
				job.get();
			} catch (InterruptedException | ExecutionException e) {}
		}
		System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
		observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
	}

	/**
	 * A {@link Callable} class capable of calculating the data for a
	 * <a href="http://en.wikipedia.org/wiki/Newton_fractal">Newton fractal</a>.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 *
	 */
	public class Execution implements Callable<Void> {
		/**
		 * The minimal value for the real part of the {@link Complex} number.
		 */
		private double reMin;
		/**
		 * The maximal value for the real part of the {@link Complex} number.
		 */
		private double reMax;
		/**
		 * The minimal value for the imaginary part of the {@link Complex}
		 * number.
		 */
		private double imMin;
		/**
		 * The maximal value for the imaginary part of the {@link Complex}
		 * number.
		 */
		private double imMax;
		/**
		 * The width of the screen where the fractal will be displayed, as
		 * measured in number of pixels.
		 */
		private int width;
		/**
		 * The height of the screen where the fractal will be displayed, as
		 * measured in number of pixels.
		 */
		private int height;
		/**
		 * The minimum value of y that has to be calculated.
		 */
		private int yMin;
		/**
		 * The maximum value of y that should be calculated.
		 */
		private int yMax;
		/**
		 * The data array in which the computed values should be stored.
		 */
		private short[] data;

		/**
		 * Creates a new execution unit that can calculate the data for a
		 * fractal.
		 * 
		 * @param reMin
		 *            The minimal value for the real part of the {@link Complex}
		 *            number.
		 * @param reMax
		 *            The maximal value for the real part of the {@link Complex}
		 *            number.
		 * @param imMin
		 *            The minimal value for the imaginary part of the
		 *            {@link Complex} number.
		 * @param imMax
		 *            The maximal value for the imaginary part of the
		 *            {@link Complex} number.
		 * @param width
		 *            The width of the screen where the fractal will be
		 *            displayed, as measured in number of pixels.
		 * @param height
		 *            The height of the screen where the fractal will be
		 *            displayed, as measured in number of pixels.
		 * @param yMin
		 *            The minimum value of y that should be calculated.
		 * @param yMax
		 *            The maximum value of y that should be calculated.
		 * @param data
		 *            The data array in which the computed values should be
		 *            stored.
		 */
		public Execution(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, short[] data) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
		}

		@Override
		public Void call() {
			int offset = yMin * width;
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {

					Complex zn = mapToComplexPlain(x, y);
					double module = 0;
					int iteration = 0;

					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex fraction = numerator.divide(denominator);

						Complex zn1 = zn.sub(fraction);
						module = zn1.sub(zn).module();
						zn = zn1;
						iteration++;
					} while (module > CONVERGENCE_TRESHOLD && iteration < MAX_ITERATION);

					int index = rootedPolynomial.indexOfClosestRootFor(zn, ROOT_TRESHOLD);
					if (index == -1) {
						data[offset++] = 0;
					} else {
						data[offset++] = (short) (index + 1);
					}
				}
			}

			return null;
		}

		/**
		 * Maps a pixel from the screen to the complex plain. The arguments
		 * describe the position of the pixel, and the result is a new
		 * {@link Complex} number with coordinates in the complex plain.
		 * 
		 * @param x
		 *            the x coordinate of the pixel.
		 * @param y
		 *            the y coordinate of the pixel.
		 * @return a new {@link Complex} number with the coordinates in the
		 *         complex plain.
		 */
		private Complex mapToComplexPlain(double x, double y) {
			double real = x / (width - 1.0) * (reMax - reMin) + reMin;
			double imaginary = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;

			return new Complex(real, imaginary);
		}
	}
}
