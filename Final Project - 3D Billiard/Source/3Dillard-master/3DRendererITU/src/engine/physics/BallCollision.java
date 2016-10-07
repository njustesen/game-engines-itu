package engine.physics;

import math.geom2d.Point2D;
import engine.objects.BilliardBall;

public class BallCollision {

	private Point2D location;
	private BilliardBall ballA;
	private BilliardBall ballB;
	
	public BallCollision(Point2D location, BilliardBall ballA, BilliardBall ballB) {
		this.location = location;
		this.ballA = ballA;
		this.ballB = ballB;
	}

	public Point2D getLocation() {
		return location;
	}

	public void setLocation(Point2D location) {
		this.location = location;
	}

	public BilliardBall getBallA() {
		return ballA;
	}

	public void setBallA(BilliardBall ballA) {
		this.ballA = ballA;
	}

	public BilliardBall getBallB() {
		return ballB;
	}

	public void setBallB(BilliardBall ballB) {
		this.ballB = ballB;
	}
	
}
