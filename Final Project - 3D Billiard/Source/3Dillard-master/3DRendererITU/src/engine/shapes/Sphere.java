package engine.shapes;

import java.util.ArrayList;

import engine.math.Point3D;

public class Sphere extends Shape3D{

	private double radius;
	private int gradient;
	
	public Sphere(Point3D anchor, double radius, int gradient) {
		super(anchor);
		this.radius = radius;
		this.gradient = gradient;
		build();
	}

	@Override
	public void build() {
		int N = gradient;
		int M = gradient;
		
		ArrayList<Point3D> points = new ArrayList<Point3D>();
		
		// M lines of latitude (horizontal)
		for(int m = 0; m < M; m++){
			
			// N lines of longitude (vertical)
			for(int n = 0; n < N; n++){
				
				// (x, y, z) = (sin(Pi * m/M) cos(2Pi * n/N), sin(Pi * m/M) sin(2Pi * n/N), cos(Pi * m/M))
				Point3D p = new Point3D(	radius * Math.sin(Math.PI * m/M) * Math.cos(2*Math.PI * n/N), 
											radius * Math.sin(Math.PI * m/M) * Math.sin(2*Math.PI * n/N), 
											radius * Math.cos(Math.PI * m/M));
				
				points.add(p);
				
			}
			
		}
		
		// Connect points
		for(Point3D p : points){
			
			int index = points.indexOf(p);
			
			if (index + N < points.size()){
				
				Point3D b = points.get(index+1);
				Point3D c = points.get(index+N);
				Triangle3D t = new Triangle3D(p, b, c);
				triangles.add(t);
				
			}
		}
		
		// Connect points
		for(Point3D p : points){
			
			int index = points.indexOf(p);
			
			if (index - N > 0){
				Point3D b = points.get(index-1);
				Point3D c = points.get(index-N);
				Triangle3D t = new Triangle3D(p, b, c);
				triangles.add(t);
			}
		}
		
	}

}
