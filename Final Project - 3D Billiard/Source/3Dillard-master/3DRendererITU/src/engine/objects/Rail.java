package engine.objects;

import engine.math.Point3D;
import engine.shapes.Cube;

public class Rail extends Cube {
	
	int xDir;
	int yDir;
	
	public Rail(Point3D anchor, double height, double width, double depth) {
		super(anchor, height, width, depth);
		
		this.xDir = 0;
		this.yDir = 0;
		
	}

	public Rail(Point3D anchor, double height, double width, double depth, int xDir, int yDir) {
		super(anchor, height, width, depth);
		
		this.xDir = xDir;
		this.yDir = yDir;
		
	}

	public int getxDir() {
		return xDir;
	}

	public void setxDir(int xDir) {
		this.xDir = xDir;
	}

	public int getyDir() {
		return yDir;
	}

	public void setyDir(int yDir) {
		this.yDir = yDir;
	}

	
	
}
