package engine.math;

import engine.objects.GameObject;
import engine.shapes.Shape3D;
import engine.shapes.Triangle3D;

public class TransformManager {
	
	public static void rotateObject(GameObject obj, double degreesX, double degreesY, double degreesZ){
		
		for(Shape3D shape : obj.getShapes()){
			
			for(Triangle3D triangle: shape.getTriangles()){
				
				if (degreesX != 0){
					triangle.setPointA( rotatePointX(triangle.getPointA(), degreesX) );
					triangle.setPointB( rotatePointX(triangle.getPointB(), degreesX) );
					triangle.setPointC( rotatePointX(triangle.getPointC(), degreesX) );
				}
				
				if (degreesY != 0){
					triangle.setPointA( rotatePointY(triangle.getPointA(), degreesY) );
					triangle.setPointB( rotatePointY(triangle.getPointB(), degreesY) );
					triangle.setPointC( rotatePointY(triangle.getPointC(), degreesY) );
				}
				
				if (degreesZ != 0){
					triangle.setPointA( rotatePointZ(triangle.getPointA(), degreesZ) );
					triangle.setPointB( rotatePointZ(triangle.getPointB(), degreesZ) );
					triangle.setPointC( rotatePointZ(triangle.getPointC(), degreesZ) );
				}
				
			}
		}
		
	}
	
	public static Vector3D rotateVector(Vector3D vector, double degreesX, double degreesY, double degreesZ){
				
		Vector3D result = new Vector3D(vector.getX(), vector.getY(), vector.getZ());
	
		if (degreesX != 0){
			result = rotateVectorX(result, degreesX);
		}
		
		if (degreesY != 0){
			result = rotateVectorY(result, degreesY);
		}
		
		if (degreesZ != 0){
			result = rotateVectorZ(result, degreesZ);
		}
		
		return result;
		
	}
	
	public static Point3D rotatePointX(Point3D point, double degrees){

		double cosD = Math.cos(degrees);
		double sinD = Math.sin(degrees);
		
		double[][] arrayA = 
			{
			{ 1.0,	0.0, 0.0, 0.0 },
			{ 0.0,	cosD, -sinD, 0.0 },
			{ 0.0,	sinD, cosD, 0.0 },
			{ 0.0,	0.0, 0.0, 1.0 },
			};
		
		Matrix matrixA = new Matrix(arrayA);

		double[][] arrayB = 
			{
			{ point.getX() },
			{ point.getY() },
			{ point.getZ() },
			{ 1.0f },
			};
		
		Matrix matrixB = new Matrix(arrayB);
		
		Matrix result = matrixA.multiplication(matrixB);

		return result.toPoint();
		
	}
	
	public static Point3D rotatePointY(Point3D point, double degrees){

		double cosD = Math.cos(degrees);
		double sinD = Math.sin(degrees);
		
		double[][] arrayA = 
			{
			{ cosD,	0.0f, sinD, 0.0f },
			{ 0.0f,	1.0f, 0.0f, 0.0f },
			{ -sinD,0.0f, cosD, 0.0f },
			{ 0.0f,	0.0f, 0.0f, 1.0f },
			};
		
		Matrix matrixA = new Matrix(arrayA);

		double[][] arrayB = 
			{
			{ point.getX() },
			{ point.getY() },
			{ point.getZ() },
			{ 1.0f },
			};
		
		Matrix matrixB = new Matrix(arrayB);
		
		Matrix result = matrixA.multiplication(matrixB);

		return result.toPoint();
		
	}
	
	public static Point3D rotatePointZ(Point3D point, double degrees){

		double cosD = Math.cos(degrees);
		double sinD = Math.sin(degrees);
		
		double[][] arrayA = 
			{
			{ cosD,	-sinD, 0.0f, 0.0f },
			{ sinD,	cosD, 0.0f, 0.0f },
			{ 0.0f, 0.0f, 1.0f, 0.0f },
			{ 0.0f,	0.0f, 0.0f, 1.0f },
			};
		
		Matrix matrixA = new Matrix(arrayA);

		double[][] arrayB = 
			{
			{ point.getX() },
			{ point.getY() },
			{ point.getZ() },
			{ 1.0f },
			};
		
		Matrix matrixB = new Matrix(arrayB);
		
		Matrix result = matrixA.multiplication(matrixB);

		return result.toPoint();
		
	}
	
	public static Vector3D rotateVectorX(Vector3D vector, double degrees){
		
		return rotatePointX(vector.toPoint(), degrees).toVector();
		
	}

	public static Vector3D rotateVectorY(Vector3D vector, double degrees){
		
		return rotatePointY(vector.toPoint(), degrees).toVector();
		
	}
	
	public static Vector3D rotateVectorZ(Vector3D vector, double degrees){
		
		return rotatePointZ(vector.toPoint(), degrees).toVector();
		
	}
	
}
