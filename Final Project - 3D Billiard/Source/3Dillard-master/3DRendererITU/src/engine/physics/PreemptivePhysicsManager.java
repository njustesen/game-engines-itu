package engine.physics;

import java.util.ArrayList;

import math.geom2d.Point2D;
import math.geom2d.conic.Circle2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.line.StraightLine2D;
import engine.math.Vector2D;
import engine.math.Vector3D;
import engine.objects.Rail;
import engine.objects.BilliardBall;
import engine.objects.BilliardTable;
import engine.objects.GameObject;
import engine.rendering.Scene;

public class PreemptivePhysicsManager extends PhysicsManager {

	private static final double g = 9.81;	// gravity
	private ArrayList<BilliardBall> balls;
	private BilliardTable table;
	
	public PreemptivePhysicsManager(){
		
		balls = new ArrayList<BilliardBall>();
		
	}

	@Override
	public void move(long delta) {
			
		//long nano = System.nanoTime();
		moveBalls(delta);
		//nano = System.nanoTime() - nano;
		//double ms = nano / 1000000.0;
		//System.out.println(ms);
		
	}

	public void moveBalls(double ms){
		
		double sec = ms/1000.0;

		// Move balls
		for(BilliardBall ball : balls){
			
			// Skip balls in pocket
			if (ball.inPocket()){
				continue;
			}
			
			// Friction
			double speed = ball.getVelocity().getVectorLength();
			double u = table.getCloth().getFriction(speed);
			double deacceleration = g * u * sec; // ??
			
			if (speed != 0){
				speed = speed * 1;
			}
			
			if (deacceleration >= speed){
				speed = 0;
			} else {
				speed -= deacceleration;
			}

			Vector3D unit = ball.getVelocity().getUnitVector();
			ball.setVelocity( unit.multiply(speed) );
			
			moveBall(ball, sec);
			
		}
		
	}

	private void moveBall(BilliardBall ball, double sec) {
		
		Vector3D moveVector = ball.getVelocity().multiply( sec * 100 );
		
		if (moveVector.getVectorLength() == 0.0){
			return;
		}
		
		Point2D from = new Point2D(ball.getPosition().getX(), ball.getPosition().getY());
		Point2D to = new Point2D(from.x() + moveVector.getX(), from.y() + moveVector.getY());
		LineSegment2D moveLine = new LineSegment2D(from, to);
		
		ArrayList<BallCollision> collisionPoints = new ArrayList<BallCollision>();
		
		// 
		
		
		// Balls
		for(BilliardBall other : balls){
			
			if (other == ball || other.inPocket()){
				continue;
			}
			
			// Find intersection point
			Circle2D otherCollisionCircle = new Circle2D(other.getPosition().getX(), other.getPosition().getY(), other.getRadius() * 2);
			
			ArrayList<Point2D> intersections = (ArrayList<Point2D>) Circle2D.lineCircleIntersections(moveLine, otherCollisionCircle);
			
			// Add collisions
			for (Point2D p : intersections){
				
				// Is right direction?
				double minX = Math.min(from.x(), to.x()) - ball.getRadius();
				double minY = Math.min(from.y(), to.y()) - ball.getRadius();
				double maxX = Math.max(from.x(), to.x()) + ball.getRadius();
				double maxY = Math.max(from.y(), to.y()) + ball.getRadius();
				
				if (p.x() >= minX && p.x() <= maxX && p.y() <= maxY && p.y() >= minY){
					collisionPoints.add(new BallCollision(p, ball, other));
				}
				
			}
			
		}
		
		// Find closest
		BallCollision closest = null;
		double closestDistance = 0.0;
		for (BallCollision bc : collisionPoints){
			
			double distance = Point2D.distance(from, bc.getLocation());
			
			// In reach?
			if (distance <= moveVector.getVectorLength()){
				
				// Closest found?
				if (closest == null || distance < closestDistance){
					
					// Angle less than 90?
					Vector3D vectorBetween3D = bc.getBallB().getPosition().subtract( bc.getBallA().getPosition() ).toVector();
					Vector3D unitBetween3D = vectorBetween3D.getUnitVector();
					Vector2D unitBetween2D = new Vector2D(unitBetween3D);
					Vector2D velocityA2D = new Vector2D(moveVector);

					double angle = Vector2D.angleBetween(unitBetween2D, velocityA2D);
						
					if(angle <= 90){
						closest = bc;
						closestDistance = distance;
					}
					
				}
				
			}
			
		}
		
		// Collision found?
		if (closest == null){
			
			// Move freely
			ball.move(moveVector);
			
		} else {
			
			// Move to collision
			Vector3D unit = moveVector.getUnitVector();
			
			ball.move(unit.multiply(closestDistance));
			
			ballCollision(closest.getBallA(), closest.getBallB());
			
		}

	}
	
	private void checkCollisions() {
		
		// In pocket
		for(BilliardBall ball : balls){
					
			// Test collision with rails
			checkRailCollisions(ball);
			
			if (outOfBounds(ball)){
				
				ball.putInPocket();
					
			}
			
		}
		
	}

	private boolean outOfBounds(BilliardBall ball) {

		if (ball.getPosition().getX() >= table.getCloth().getWidth() / 2){
			return true;
		}
				
		if (ball.getPosition().getX() <= -table.getCloth().getWidth() / 2){
			return true;
		}
		
		if (ball.getPosition().getY() >= table.getCloth().getHeight() / 2){
			return true;
		}
		
		if (ball.getPosition().getY() <= -table.getCloth().getHeight() / 2){
			return true;
		}
		
		return false;
		
	}
	
	private void ballCollision(BilliardBall a, BilliardBall b) {
		
		Vector3D newVelA = new Vector3D(0,0,0);
		Vector3D newVelB = new Vector3D(0,0,0);
		
		Vector3D aToB = getTransferedForce(a, b);
		newVelB = newVelB.add(aToB);
		newVelA = newVelA.subtract(aToB);
		
		Vector3D bToA = getTransferedForce(b, a);
		newVelA = newVelA.add(bToA);
		newVelB = newVelB.subtract(bToA);
		
		newVelA = newVelA.add(a.getVelocity());
		newVelB = newVelB.add(b.getVelocity());
		a.setVelocity(newVelA);
		b.setVelocity(newVelB);
		
	}
		
	private Vector3D getTransferedForce(BilliardBall a, BilliardBall b) {
		
		Vector3D vectorBetween3D = a.getPosition().subtract( b.getPosition() ).toVector();
		Vector3D unitBetween3D = vectorBetween3D.getUnitVector();
		Vector2D unitBetween2D = new Vector2D(unitBetween3D);
		Vector2D velocityA2D = new Vector2D(a.getVelocity());
		
		if (!velocityA2D.equals(Vector2D.Zero)){
		
			// The angle between the two balls and the ball velocity
			double collisionAngle = Vector2D.angleBetween(unitBetween2D, velocityA2D);
			
			if(collisionAngle > 90){
				
				double impactOther = (collisionAngle - 90) / 90;
				double forceOther = velocityA2D.getVectorLength() * impactOther;

				Vector2D newVelB2D = unitBetween2D.multiply(forceOther).multiply( -1 );
				return new Vector3D(newVelB2D);
				
			}
			
		}
		
		return new Vector3D(0,0,0);
		
	}

	private void checkRailCollisions(BilliardBall ball) {
		
		if (checkRailCollision(table.getRailTopA(), ball)) 
			railsCollision(table.getRailTopA(), ball);
		if (checkRailCollision(table.getRailTopB(), ball)) 
			railsCollision(table.getRailTopB(), ball);
		if (checkRailCollision(table.getRailRight(), ball))
			railsCollision(table.getRailRight(), ball);
		if (checkRailCollision(table.getRailLeft(), ball))
			railsCollision(table.getRailLeft(), ball);
		if (checkRailCollision(table.getRailBottomA(), ball))
			railsCollision(table.getRailBottomA(), ball);
		if (checkRailCollision(table.getRailBottomB(), ball))
			railsCollision(table.getRailBottomB(), ball);
		
	}

	private boolean checkRailCollision(Rail rail, BilliardBall ball) {

		// Test
		double distanceX = Math.abs( ball.getPosition().getX() - rail.getAnchor().getX() );
		double distanceY = Math.abs( ball.getPosition().getY() - rail.getAnchor().getY() );
		
		if (distanceX > rail.getWidth() / 2 + ball.getRadius() * Math.abs(rail.getxDir())){
			return false;
		}
		if (distanceY > rail.getHeight() / 2 + ball.getRadius() * Math.abs(rail.getyDir())){
			return false;
		}
		
		if (distanceX <= rail.getWidth() / 2){
			return true;
		}
		if (distanceY <= rail.getHeight() / 2){
			return true;
		}
		
		
		
		//calculate distance between center of circle and rectangle corner (a^2 + b^2)
	    int cornerDistance = (int) (Math.pow((distanceX - rail.getWidth()/2), 2) +
	                         Math.pow(distanceY - rail.getHeight()/2, 2));
	    
	    //if distance between circle and corner is less than circle-radius return true (if a^2 + b^2 <= c^2)
	    return (cornerDistance <= (Math.pow(ball.getRadius(), 2)));
		
	}
	
	private void railsCollision(Rail rail, BilliardBall ball) {
		
		int x = 1;
		int y = 1;
		
		// If rail is right or left
		if (rail.getxDir() != 0){
			x = -1;
			if (rail.getxDir() > 0)
				ball.getPosition().setX(rail.getAnchor().getX() - (rail.getWidth()/2 + ball.getRadius()));
			if (rail.getxDir() < 0)
				ball.getPosition().setX(rail.getAnchor().getX() + (rail.getWidth()/2 + ball.getRadius()));
		}
		
		// If rail is right or left
		if (rail.getyDir() != 0){
			y = -1;
			if (rail.getyDir() > 0)
				ball.getPosition().setY(rail.getAnchor().getY() - (rail.getHeight()/2 + ball.getRadius()));
			if (rail.getyDir() < 0)
				ball.getPosition().setY(rail.getAnchor().getY() + (rail.getHeight()/2 + ball.getRadius()));
		}
		
		// Change velocity
		Vector3D newVelocity = ball.getVelocity();
		newVelocity.setX( newVelocity.getX() * x );
		newVelocity.setY( newVelocity.getY() * y );
		ball.setVelocity(newVelocity);
		
	}

	public ArrayList<BilliardBall> getBalls() {
		return balls;
	}

	public void setBalls(ArrayList<BilliardBall> balls) {
		this.balls = balls;
	}
	
	public void addBall(BilliardBall ball){
		this.balls.add(ball);
		
	}

	public BilliardTable getTable() {
		return table;
	}

	public void setTable(BilliardTable table) {
		this.table = table;
	}

	@Override
	public void setupFromScene(Scene scene) {
		
		for(GameObject obj : scene.getObjects()){
			
			if (obj instanceof BilliardBall){
				
				addBall((BilliardBall)obj);
				
			} else if (obj instanceof BilliardTable){
				
				setTable((BilliardTable)obj);
				
			}
		}
		
	}

}
