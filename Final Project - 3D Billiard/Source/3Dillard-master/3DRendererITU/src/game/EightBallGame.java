package game;

import engine.BilliardGame;
import engine.math.Point3D;
import engine.math.TransformManager;
import engine.math.Vector3D;
import engine.objects.BilliardCamera;
import engine.physics.PreemptivePhysicsManager;
import engine.rendering.ScanlineRenderer;


@SuppressWarnings("serial")
public class EightBallGame extends BilliardGame {
	
	private boolean shooting;
	
	/**
	 * Constructor for Game.
	 */
	public EightBallGame(){
		
	}

	@Override
	protected void init() {
		
		scene = new EightBallScene();
		
		physicsManager = new PreemptivePhysicsManager();
		
		physicsManager.setupFromScene(scene);
		
		// Move camera
		scene.getCamera().setLookpoint( ((EightBallScene)scene).getCueBall().getPosition() );
		
		shooting = false;
		
		setRender(new ScanlineRenderer());
		
	}


	@Override
	protected void update(long delta) {
		Point3D cam = scene.getCamera().getPosition();
		Point3D look = scene.getCamera().getLookPoint();
		
		Vector3D camVector = cam.subtract(look).toVector();
		
		double zoom = inputManager.getVerticalWASD();
		double y = inputManager.getVerticalArrows();
		double z = inputManager.getHorizontalArrows();
		
		// Zoom
		if (zoom != 0){
			double zoomEffect = zoom * 0.1 * delta;
			double length = camVector.getVectorLength();
			length = length + zoomEffect;
			Vector3D unit = camVector.getUnitVector();
			Point3D newCam = unit.multiply(length).toPoint();
			scene.getCamera().setPosition(look.add(newCam));
			cam = scene.getCamera().getPosition();
			look = scene.getCamera().getLookPoint();
			camVector = cam.subtract(look).toVector();
		}
			
		// Rotate
		double speed = scene.getCamera().getRotationSpeed() * (Math.PI / 180) * 0.001 * delta;
		Vector3D rotated = TransformManager.rotateVector(camVector, 0, y * speed, z * speed);
		rotated = look.add(rotated.toPoint()).toVector();
		scene.getCamera().setPosition(rotated.toPoint());
				
		// Add force to ball
		shoot();

		// Simulate physics
		physicsManager.move(delta);
		
	}
	
	private void shoot(){
		
		if (inputManager.isMouseLeftDown() && ((EightBallScene)scene).getCueBall().getVelocity().equals(Vector3D.Zero)){
			
			shooting = true;
			
		} else {
		
			if (shooting && inputManager.getMouseDownTime() > 0){
						
				Vector3D force = ((BilliardCamera) scene.getCamera()).getShootingDirection();
				double power = Math.min(16, inputManager.getMouseDownTime() / 100);
				force = force.multiply(power);
						
				((EightBallScene)scene).getCueBall().addVelocity( force );
				
				shooting = false;
				
			} else {
	
				// Move camera
				scene.getCamera().setLookpoint( ((EightBallScene)scene).getCueBall().getPosition() );
				
			}
			
		}
		
	}

}
