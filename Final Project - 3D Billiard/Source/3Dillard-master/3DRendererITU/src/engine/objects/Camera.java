package engine.objects;

import engine.math.Point3D;
import engine.math.Vector3D;


public class Camera {

	protected Point3D position;
	protected Point3D lookPoint;
	protected Vector3D up;
	protected int angle;
	protected int movementSpeed;
	protected int rotationSpeed;
	
	public Camera(Point3D position, Point3D lookPoint, Vector3D up, int angle){
		this.position = position;
		this.lookPoint = lookPoint;
		this.up = up;
		this.angle = angle;
	}
	
	public double getX(){
		return position.getX();
	}
	
	public double getY(){
		return position.getY();
	}
	
	public double getZ(){
		return position.getZ();
	}
	
	public double getAngle(){
		return angle;
	}
	
	public void setX(double newX){
		this.position.setX(newX);
	}
	
	public void setY(double newY){
		this.position.setY(newY);
	}
	
	public void setZ(double newZ){
		this.position.setZ(newZ);
	}
	
	public void setLookpointX(double newX){
		this.lookPoint.setX(newX);
	}
	
	public void setLookpointY(double newY){
		this.lookPoint.setY(newY);
	}
	
	public void setLookpointZ(double newZ){
		this.lookPoint.setZ(newZ);
	}
	
	public double getLookPointX(){
		return lookPoint.getX();
	}
	
	public double getLookPointY(){
		return lookPoint.getY();
	}
	
	public double getLookPointZ(){
		return lookPoint.getZ();
	}
	
	public Point3D getLookPoint(){
		return lookPoint;
	}
	
	public Vector3D getUpVector(){
		return up;
	}
	
	public Point3D getPosition(){
		return position;
	}

	public int getMovementSpeed() {
		return movementSpeed;
	}

	public int getRotationSpeed() {
		return rotationSpeed;
	}

	public void setMovementSpeed(int movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	public void setRotationSpeed(int rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public void moveX(int i) {
		position.setX(position.getX() + i);
		
	}

	public void moveY(int i) {
		position.setY(position.getY() + i);
	}

	public void moveLookpointX(int i) {
		lookPoint.setX(lookPoint.getX() + i);
	}

	public void moveLookpointY(int i) {
		lookPoint.setY(lookPoint.getY() + i);
	}

	public void setLookpoint(Point3D p) {
		lookPoint.setX(p.getX());
		lookPoint.setY(p.getY());
		lookPoint.setZ(p.getZ());
	}

	public void setPosition(Point3D p) {
		this.position = p;
	}

}
