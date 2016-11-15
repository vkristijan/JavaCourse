package hr.fer.zemris.java.raytracer.model;

/**
 * A {@link GraphicalObject} in the shape of a sphere.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class Sphere extends GraphicalObject {
	/** The center of the sphere. */
	private Point3D center;
	/** The radius of the sphere. */
	private double radius;
	/**
	 * Coefficient for diffuse component for red color. Used in lightning model
	 * to calculate point color. Legal values are [0.0,1.0].
	 */
	private double kdr;
	/**
	 * Coefficient for diffuse component for green color. Used in lightning
	 * model to calculate point color. Legal values are [0.0,1.0].
	 */
	private double kdg;
	/**
	 * Coefficient for diffuse component for blue color. Used in lightning model
	 * to calculate point color. Legal values are [0.0,1.0].
	 */
	private double kdb;
	/**
	 * Coefficient for reflective component for red color. Used in lightning
	 * model to calculate point color. Legal values are [0.0,1.0].
	 */
	private double krr;
	/**
	 * Coefficient for reflective component for green color. Used in lightning
	 * model to calculate point color. Legal values are [0.0,1.0].
	 */
	private double krg;
	/**
	 * Coefficient for reflective component for blue color. Used in lightning
	 * model to calculate point color. Legal values are [0.0,1.0].
	 */
	private double krb;
	/**
	 * Coefficient <code>n</code> for reflective component. Used in lightning
	 * model to calculate point color. Legal values are [0.0,1.0].
	 */
	private double krn;

	/**
	 * Creates a new {@link Sphere} with the given parameters.
	 * 
	 * @param center
	 *            The center of the sphere.
	 * @param radius
	 *            The radius of the sphere.
	 * @param kdr
	 *            Coefficient for diffuse component for red color. Used in
	 *            lightning model to calculate point color. Legal values are
	 *            [0.0,1.0].
	 * @param kdg
	 *            Coefficient for diffuse component for green color. Used in
	 *            lightning model to calculate point color. Legal values are
	 *            [0.0,1.0].
	 * @param kdb
	 *            Coefficient for diffuse component for blue color. Used in
	 *            lightning model to calculate point color. Legal values are
	 *            [0.0,1.0].
	 * @param krr
	 *            Coefficient for reflective component for red color. Used in
	 *            lightning model to calculate point color. Legal values are
	 *            [0.0,1.0].
	 * @param krg
	 *            Coefficient for reflective component for green color. Used in
	 *            lightning model to calculate point color. Legal values are
	 *            [0.0,1.0].
	 * @param krb
	 *            Coefficient for reflective component for blue color. Used in
	 *            lightning model to calculate point color. Legal values are
	 *            [0.0,1.0].
	 * @param krn
	 *            Coefficient <code>n</code> for reflective component. Used in
	 *            lightning model to calculate point color. Legal values are
	 *            [0.0,1.0].
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D direction = ray.direction;
		double a = direction.scalarProduct(direction);

		Point3D source = ray.start.sub(center);
		double b = 2 * direction.scalarProduct(source);
		double c = source.scalarProduct(source) - radius * radius;

		double discriminant = b * b - 4 * a * c;

		if (discriminant < 0) return null;

		discriminant = Math.sqrt(discriminant);
		double sol1 = (-b + discriminant) / 2 * a;
		double sol2 = (-b - discriminant) / 2 * a;

		Point3D p = null;
		if (sol1 > 0 && sol1 < sol2) {
			p = ray.start.add(direction.scalarMultiply(sol1));
		} else if (sol2 > 0) {
			p = ray.start.add(direction.scalarMultiply(sol2));
		} else {
			return null;
		}

		Point3D vector = ray.start.sub(p);
		double distance = vector.scalarProduct(vector);
		distance = Math.sqrt(distance);

		return new RayIntersectionImpl(p, distance, true);
	}

	/**
	 * Implementation of {@link RayIntersection}.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 *
	 */
	private class RayIntersectionImpl extends RayIntersection {
		/**
		 * Creates a new {@link RayIntersectionImpl} with the given arguments.
		 * 
		 * @param point
		 *            point of intersection between ray and object
		 * @param distance
		 *            distance between start of ray and intersection
		 * @param outer
		 *            specifies if intersection is outer
		 */
		protected RayIntersectionImpl(Point3D point, double distance, boolean outer) {
			super(point, distance, outer);
		}

		@Override
		public Point3D getNormal() {
			return this.getPoint().sub(center).normalize();
		}

		@Override
		public double getKrr() {
			return krr;
		}

		@Override
		public double getKrn() {
			return krn;
		}

		@Override
		public double getKrg() {
			return krg;
		}

		@Override
		public double getKrb() {
			return krb;
		}

		@Override
		public double getKdr() {
			return kdr;
		}

		@Override
		public double getKdg() {
			return kdg;
		}

		@Override
		public double getKdb() {
			return kdb;
		}
	}
}
