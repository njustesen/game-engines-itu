package engine.physics;

import engine.math.Point3D;
import engine.math.Vector3D;

public interface Movable {

	public Vector3D getVelocity();

	public void setVelocity(Vector3D velocity);
	
	public void addVelocity(Vector3D amount);
	
	public void move(Vector3D move);
	
	public Point3D getPosition();
	
	public void setPosition(Point3D position);
	
}
