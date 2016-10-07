package engine.objects;

import engine.math.Point3D;

public class BilliardTable extends GameObject {
	
	double width, height, depth, railsWidth, pocketRadius;
	double rollFriction, slideFriction;
	Rail railTopA;
	Rail railTopB;
	Rail railLeft;
	Rail railRight;
	Rail railBottomA;
	Rail railBottomB;
	Cloth cloth;

	public BilliardTable(Point3D position, double height, double width, double depth, double railssWidth, double pocketRadius, double rollFriction, double slideFriction) {
		super(position);
		this.width = width; 
		this.height = height;
		this.depth = depth;
		this.railsWidth = railssWidth;
		this.rollFriction = rollFriction;
		this.slideFriction = slideFriction;
		this.pocketRadius = pocketRadius;
		build();
	}

	@Override
	protected void build() {
		
		double pocketDiameter = pocketRadius * 2;
		
		// SURFACE
		cloth = new Cloth(Point3D.Zero, height, width, depth, rollFriction, slideFriction);
		addShape(cloth);
		
		// TOP A
		railTopA = new Rail(new Point3D(width / 4, height/2 + railsWidth/2, depth/4), railsWidth, width/2 - pocketDiameter, depth*1.5);
		railTopA.setxDir(0);
		railTopA.setyDir(1);
		addShape(railTopA);
		
		// TOP B
		railTopB = new Rail(new Point3D(-width / 4, height/2 + railsWidth/2, depth/4), railsWidth, width/2 - pocketDiameter, depth*1.5);
		railTopB.setxDir(0);
		railTopB.setyDir(1);
		addShape(railTopB);
		
		// BOTTOM A
		railBottomA = new Rail(new Point3D(width / 4, -height/2 - railsWidth/2, depth/4), railsWidth, width/2 - pocketDiameter, depth*1.5);
		railBottomA.setxDir(0);
		railBottomA.setyDir(-1);
		addShape(railBottomA);
		
		// BOTTOM B
		railBottomB = new Rail(new Point3D(-width / 4, -height/2 - railsWidth/2, depth/4), railsWidth, width/2 - pocketDiameter, depth*1.5);
		railBottomB.setxDir(0);
		railBottomB.setyDir(-1);
		addShape(railBottomB);
		
		// LEFT
		railLeft = new Rail(new Point3D(-width/2 - railsWidth/2, 0, depth/4), height - pocketDiameter, railsWidth, depth*1.5);
		railLeft.setxDir(-1);
		railLeft.setyDir(0);
		addShape(railLeft);
		
		// RIGHT
		railRight = new Rail(new Point3D(width/2 + railsWidth/2, 0, depth/4), height - pocketDiameter, railsWidth, depth*1.5);
		railRight.setxDir(1);
		railRight.setyDir(0);
		addShape(railRight);
		
	}
	
	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getDepth() {
		return depth;
	}

	public void setDepth(double depth) {
		this.depth = depth;
	}

	public double getRailsWidth() {
		return railsWidth;
	}

	public void setRailsWidth(double railsWidth) {
		this.railsWidth = railsWidth;
	}

	public Rail getRailTopA() {
		return railTopA;
	}

	public void setRailTopA(Rail railTop) {
		this.railTopA = railTop;
	}
	
	public Rail getRailTopB() {
		return railTopB;
	}

	public void setRailTopB(Rail railTop) {
		this.railTopB = railTop;
	}

	public Rail getRailLeft() {
		return railLeft;
	}

	public void setRailLeft(Rail railLeft) {
		this.railLeft = railLeft;
	}

	public Rail getRailRight() {
		return railRight;
	}

	public void setRailRight(Rail railRight) {
		this.railRight = railRight;
	}

	public Rail getRailBottomA() {
		return railBottomA;
	}

	public void setRailBottomA(Rail railBottom) {
		this.railBottomA = railBottom;
	}
	
	public Rail getRailBottomB() {
		return railBottomB;
	}

	public void setRailBottomB(Rail railBottom) {
		this.railBottomB = railBottom;
	}

	public Cloth getCloth() {
		return cloth;
	}
	
}
