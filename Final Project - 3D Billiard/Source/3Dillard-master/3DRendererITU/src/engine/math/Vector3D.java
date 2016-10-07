package engine.math;

/**
 * Vector class representing a vector (a direction) in 3d space
 **/
public class Vector3D {

	public static final Vector3D Zero = new Vector3D(0,0,0);
	private double x, y, z;
	
	public Vector3D(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D(Vector2D v) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = 0;
	}

	public Vector3D(Point3D point){
		this.x = point.getX();
		this.y = point.getY();
		this.z = point.getZ();
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getZ(){
		return z;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	/**
	 * returns the length of the vector. |V| = sqrt(x^2 + y^2 + z^2).
	 **/
	public double getVectorLength(){
		return Math.sqrt((x*x)+(y*y)+(z*z));
	}
	
	/**
	 * Adding this vector with the input vector.
	 **/
	public Vector3D add(Vector3D v){
		return new Vector3D(x+v.x, y+v.y, z+v.z);
	}
	
	/**
	 * Subtracting the input vector from this vector.
	 **/
	public Vector3D subtract(Vector3D v){
		return new Vector3D(x-v.x, y-v.y, z-v.z);	
	}
	
	/**
	 * Subtracting the input vector from this vector.
	 **/
	public Vector3D multiply(Vector3D v){
		return new Vector3D(x*v.x, y*v.y, z*v.z);	
	}
	
	public Vector3D multiply(double other) {
		return new Vector3D(x*other, y*other, z*other);
	}
	
	/**
	 * Scaling a vector.
	 **/
	public Vector3D vectorScaling(double scalar){
		Vector3D newVector = new Vector3D(x*scalar, y*scalar, z*scalar);
		return newVector;	
	}
	
	/**
	 * Returns the dot-product of this and a given vector.
	 **/
	public double getDotProduct(Vector3D v){
		return x*v.x + y*v.y + z*v.z;
	}
	
	/**
	 * Returns the cross-product of this and a given vector.
	 **/
	public Vector3D getCrossProduct(Vector3D v) {
	    return new Vector3D(y * v.z - z * v.y,
	                       z * v.x - x * v.z,
	                       x * v.y - y * v.x);
	}

	/**
	 * returns a vector with a length of 1 unit.
	 **/
	public Vector3D getUnitVector(){
		double length = getVectorLength();
		if (length == 0){
			return new Vector3D(0,0,0);
		}
		Vector3D newVector = new Vector3D(x/length, y/length, z/length);
		return newVector;
	}
	
	public Vector3D getInvertedVector(){
		Vector3D newVector = new Vector3D(-x, -y, -z);
		return newVector;
	}
	
	public Matrix toMatrix(){
		Matrix newMatrix = new Matrix(3,1);
		newMatrix.matrixData[0][0]=this.x;
		newMatrix.matrixData[0][1]=this.y;
		newMatrix.matrixData[0][2]=this.z;
		return newMatrix;
	}
	
	public Point3D toPoint(){
		Point3D p = new Point3D(x,y,z);
		return p;	
	}
	
	public boolean equals(Object o) {
	    if (o instanceof Vector3D) {
	    	Vector3D v = (Vector3D) o;
	      	if (v.getX() == this.getX() && v.getY() == this.getY() && v.getZ() == this.getZ()) return true;
	    }
	    return false;
	}	
	
}
