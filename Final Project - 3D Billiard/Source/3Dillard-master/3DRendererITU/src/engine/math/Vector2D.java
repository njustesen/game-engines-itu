package engine.math;

public class Vector2D {

	public static final Vector2D Zero = new Vector2D(0,0);
	private double x, y;
	
	public static double angleBetween(Vector2D a, Vector2D b) {
		double dotProd = a.getDotProduct(b);
		double lenProd = a.getVectorLength() * b.getVectorLength();
		double divOperation = dotProd/lenProd;
		
		double angle = Math.acos( divOperation ) * (180.0 / Math.PI);
		
		return angle;
	}
	
	public Vector2D(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(Vector3D normalBetween3D) {
		this.x = normalBetween3D.getX();
		this.y = normalBetween3D.getY();
	}

	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	/**
	 * returns the length of the vector. |V| = sqrt(x^2 + y^2 + z^2).
	 **/
	public double getVectorLength(){
		return Math.sqrt((x*x)+(y*y));
	}
	
	/**
	 * Adding this vector with the input vector.
	 **/
	public Vector2D add(Vector2D v){
		return new Vector2D(x+v.x, y+v.y);
	}
	
	/**
	 * Subtracting the input vector from this vector.
	 **/
	public Vector2D subtract(Vector2D v){
		return new Vector2D(x-v.x, y-v.y);	
	}
	
	/**
	 * Subtracting the input vector from this vector.
	 **/
	public Vector2D multiply(Vector2D v){
		return new Vector2D(x*v.x, y*v.y);	
	}
	
	public Vector2D multiply(double other) {
		return new Vector2D(x*other, y*other);
	}
	
	/**
	 * Scaling a vector.
	 **/
	public Vector2D vectorScaling(double scalar){
		Vector2D newVector = new Vector2D(x*scalar, y*scalar);
		return newVector;	
	}
	
	/**
	 * Returns the dot-product of this and a given vector.
	 **/
	public double getDotProduct(Vector2D v){
		return x*v.x + y*v.y;
	}
	
	/**
	 * returns a vector with a length of 1 unit.
	 **/
	public Vector2D getUnitVector(){
		Vector2D newVector = new Vector2D(x/getVectorLength(), y/getVectorLength());
		return newVector;
	}
	
	public Matrix toMatrix(){
		Matrix newMatrix = new Matrix(2,1);
		newMatrix.matrixData[0][0]=this.x;
		newMatrix.matrixData[0][1]=this.y;
		return newMatrix;
	}
	
	public Point2D toPoint(){
		Point2D p = new Point2D(x,y);
		return p;	
	}
	
	public boolean equals(Object o) {
	    if (o instanceof Vector2D) {
	    	Vector2D v = (Vector2D) o;
	      	if (v.getX() == this.getX() && v.getY() == this.getY()) return true;
	    }
	    return false;
	}	
	
}
