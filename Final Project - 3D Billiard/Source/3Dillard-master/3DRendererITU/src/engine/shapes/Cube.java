package engine.shapes;

import engine.math.Point3D;

public class Cube extends Shape3D {

	double height;
	double width;
	double depth;
	
	public Cube(Point3D anchor, double height, double width, double depth) {
		super(anchor);
		this.height = height;
		this.width = width;
		this.depth = depth;
		build();
	}

	@Override
	public void build() {
		
		Point3D a = new Point3D(width/2, height/2, depth/2);
		Point3D b = new Point3D(-width/2, height/2, depth/2);
		Point3D c = new Point3D(-width/2, height/2, -depth/2);
		Point3D d = new Point3D(width/2, height/2, -depth/2);
		
		Point3D e = new Point3D(width/2, -height/2, depth/2);
		Point3D f = new Point3D(-width/2, -height/2, depth/2);
		Point3D g = new Point3D(-width/2, -height/2, -depth/2);
		Point3D h = new Point3D(width/2, -height/2, -depth/2);
		
		triangles.add(new Triangle3D(a,b,d));
		triangles.add(new Triangle3D(b,c,d));
		
		triangles.add(new Triangle3D(c,b,g));
		triangles.add(new Triangle3D(b,f,g));
		
		triangles.add(new Triangle3D(a,d,e));
		triangles.add(new Triangle3D(d,h,e));
		
		triangles.add(new Triangle3D(d,c,h));
		triangles.add(new Triangle3D(c,g,h));
		
		triangles.add(new Triangle3D(b,a,f));
		triangles.add(new Triangle3D(a,e,f));
		
		triangles.add(new Triangle3D(h,g,e));
		triangles.add(new Triangle3D(g,f,e));

	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getDepth() {
		return depth;
	}

	public void setDepth(double depth) {
		this.depth = depth;
	}
	
}
