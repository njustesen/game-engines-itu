package engine.math;




public class Point3D {

	public static final Point3D Zero = new Point3D(0.0,0.0,0.0);
	private double x,y,z,w;
	
	public Point3D(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = 1;
	}
	
	public Point3D(Point3D p) {
		this.x = p.getX();
		this.y = p.getY();
		this.z = p.getZ();
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
	public double getW(){
		return w;
	}
	
	public void setX(double newX){
		x = newX;
	}
	
	public void setY(double newY){
		y = newY;
	}
	
	public void setZ(double newZ){
		z = newZ;
	}
	public void setW(double newW){
		w = newW;
	}
	public Vector3D toVector(){
		return new Vector3D(x,y,z);
	}
	
	public Matrix toMatrix(){
		Matrix newMatrix = new Matrix(4,1);
		newMatrix.matrixData[0][0]=this.x;
		newMatrix.matrixData[1][0]=this.y;
		newMatrix.matrixData[2][0]=this.z;
		newMatrix.matrixData[3][0]=1;
		return newMatrix;
	}
	
	/**
	 * Subtracting the input vector from this vector.
	 **/
	public Point3D add(Point3D p){
		Point3D newPoint = new Point3D(x+p.x, y+p.y, z+p.z);
		return newPoint;	
	}
	
	/**
	 * Subtracting the input vector from this vector.
	 **/
	public Point3D subtract(Point3D p){
		Point3D newPoint = new Point3D(x-p.x, y-p.y, z-p.z);
		return newPoint;	
	}
	
	/**
	 * Subtracting the input vector from this vector.
	 **/
	public Point3D multiply(Point3D p){
		Point3D newPoint = new Point3D(x*p.x, y*p.y, z*p.z);
		return newPoint;	
	}
	
	/**
	 * Subtracting the input vector from this vector.
	 **/
	public Point3D divide(Point3D p){
		Point3D newPoint = new Point3D(x/p.x, y/p.y, z/p.z);
		return newPoint;	
	}

	public Point3D subtract(Vector3D v) {
		// TODO Auto-generated method stub
		return new Point3D(x - v.getX(), y - v.getY(), z - v.getZ());
	}

	public Point3D add(Vector3D v) {
		// TODO Auto-generated method stub
		return new Point3D(x + v.getX(), y + v.getY(), z + v.getZ());
	}

}
