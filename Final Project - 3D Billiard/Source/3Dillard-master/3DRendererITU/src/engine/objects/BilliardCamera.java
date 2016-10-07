package engine.objects;

import engine.math.Point3D;
import engine.math.Vector3D;

public class BilliardCamera extends Camera{

	public BilliardCamera(Point3D position, Point3D lookPoint, Vector3D up, int angle) {
		super(position, lookPoint, up, angle);
		
	}

	public Vector3D getShootingDirection() {
		
		Vector3D force = lookPoint.subtract(position).toVector();
		force = force.getUnitVector();
		force.setZ(0);
		
		return force;
	}
	
}
