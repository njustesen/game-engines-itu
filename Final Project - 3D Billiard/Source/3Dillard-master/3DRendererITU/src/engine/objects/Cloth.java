package engine.objects;

import engine.math.Point3D;
import engine.shapes.Cube;

public class Cloth extends Cube  {
	
	static final double slideSpeed = 1.5;
	double rollFriction;
	double slideFriction;
	double deacceleration;
	
	public Cloth(Point3D anchor, double height, double width, double depth, double rollFriction, double slideFriction) {
		super(anchor, height, width, depth);
		
		this.rollFriction = rollFriction;
		this.slideFriction = slideFriction;
		
	}

	public double getFriction(double speed) {
		
		if (speed >= slideSpeed){
			
			return slideFriction;
			
		}
		
		return rollFriction;
	}

	public double getDeaccelerationCoefficient() {
		return deacceleration;
	}
	

}
