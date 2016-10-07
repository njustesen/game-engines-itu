package engine.objects;

import engine.math.Point3D;
import engine.math.TransformManager;
import engine.math.Vector3D;
import engine.physics.Movable;
import engine.shapes.Sphere;

public class BilliardBall extends GameObject implements Movable {

	private static final int gradient = 12;
	
	private int number;
	private double radius;
	private Vector3D velocity;
	private boolean inPocket;
	
	public BilliardBall(int number, double radius, Point3D position){
		super(position);
		this.number = number;
		this.radius = radius;
		this.velocity = Vector3D.Zero;
		this.inPocket = false;
		build();
	}
	
	@Override
	protected void build() {
		
		addShape(new Sphere(Point3D.Zero, radius, gradient));
		
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public Vector3D getVelocity() {
		return velocity;
	}

	@Override
	public void setVelocity(Vector3D velocity) {
		this.velocity = velocity;
	}

	@Override
	public void move(Vector3D move) {
		// Move
		position.setX(position.getX() + move.getX());
		position.setY(position.getY() + move.getY());
		position.setZ(position.getZ() + move.getZ());
		
		// Rotate
		double x = 0;
		double y = 0;
		double z = 0;
		if (move.getY() != 0){
			x = (radius * 2 * Math.PI) / -move.getY();
			x = (Math.PI*2)/x;
		} else {
			x = 0;
		}
		
		if (move.getX() != 0){
			y = (radius * 2 * Math.PI) / move.getX();
			y = (Math.PI*2)/y;
		} else {
			y = 0;
		}
		
		TransformManager.rotateObject(this, x, y, z);
	}

	@Override
	public void addVelocity(Vector3D amount) {
		velocity.setX(velocity.getX() + amount.getX());
		velocity.setY(velocity.getY() + amount.getY());
		velocity.setZ(velocity.getZ() + amount.getZ());
	}

	public boolean inPocket() {
		return inPocket;
	}

	public void putInPocket() {
		inPocket = true;
		visible = false;
	}
	
	
	
}
