package engine.physics;

import java.util.ArrayList;
import java.util.HashMap;

import engine.math.Vector2D;
import engine.math.Vector3D;
import engine.objects.Rail;
import engine.objects.BilliardBall;
import engine.objects.BilliardTable;
import engine.objects.GameObject;
import engine.rendering.Scene;

public class ExperimentalPhysicsManager {

	//private static final double velocityLimit = 0.00001;	// 1 cm/sec
	private ArrayList<BilliardBall> balls;
	private BilliardTable table;
	private int iterationsPerSecond;
	private BilliardPhysicsManager manager;
	private ArrayList<HashMap<BilliardBall, Vector3D>> positionList;
	
	public ExperimentalPhysicsManager(BilliardPhysicsManager manager, int iterationsPerSecond){
		this.manager = manager;
		balls = new ArrayList<BilliardBall>();
		this.iterationsPerSecond = iterationsPerSecond;
		positionList = new ArrayList<HashMap<BilliardBall, Vector3D>>();
		
	}

	public void move(long delta) {
		
		boolean move = true;
		
		while(move){
			
			manager.move(delta);
			
			move = false;
			
			for (BilliardBall b : manager.getBalls()){
				
				HashMap<BilliardBall, Vector3D> positions;
				
				//positions 
				
				if (b.getVelocity().getVectorLength() != 0){
					
					move = true;
					
					
				}
				
			}
			
			
		}
		
	}

	public void moveBalls(double ms){

		// Move balls
		for(BilliardBall ball : balls){
			
			// Skip balls in pocket
			if (ball.inPocket()){
				continue;
			}
			
			// Friction
			double speed = ball.getVelocity().getVectorLength();
			double c = table.getCloth().getDeaccelerationCoefficient() / 20;
			//double deacceleration = table.getCloth().getDeaccelerationCoefficient() * speed;
			double deacceleration = c * ms;
			
			if (deacceleration >= speed){
				speed = 0;
			} else {
				speed -= deacceleration;
			}
			/*
			if (speed < velocityLimit && speed != 0){
				speed = 0;
			}
			*/
			Vector3D unit = ball.getVelocity().getUnitVector();
			ball.setVelocity( unit.multiply(speed) );

			
			// Move ball - in cm/s
			ball.move(ball.getVelocity().multiply( ms/1000.0 * 100 ));
			
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
						
			// Test collision with balls
			for(int i = balls.indexOf(ball); i < balls.size(); i++){
				
				// Skip balls in pocket
				if (balls.get(i).inPocket()){
					continue;
				}
				
				if (checkBallCollision(ball, balls.get(i))){
					
					ballCollision(ball, balls.get(i));
					
				}
				
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

	private boolean checkBallCollision(BilliardBall ball, BilliardBall other) {
		
		if (ball == other) return false;
		
		Vector3D vectorBetween = ball.getPosition().subtract( other.getPosition() ).toVector();
		
		if (vectorBetween.getVectorLength() < ball.getRadius() + other.getRadius()) return true;
		
		return false;
	}
	
	private void ballCollision(BilliardBall a, BilliardBall b) {
		
		// Correct positions
		Vector3D vectorBetween3D = a.getPosition().subtract( b.getPosition() ).toVector();
		Vector3D unitBetween3D = vectorBetween3D.getUnitVector();
		a.setPosition( b.getPosition().add(unitBetween3D.multiply(a.getRadius() * 2.0) ) );
		
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
