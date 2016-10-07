package test;

import engine.BilliardGame;
import engine.math.Point3D;
import engine.math.TransformManager;
import engine.math.Vector3D;
import engine.rendering.ScanlineRenderer;

@SuppressWarnings("serial")
public class Test extends BilliardGame {
	
	
	/**
	 * Constructor for Game.
	 */
	public Test(){
		
	}

	@Override
	protected void init() {
		
		scene = new SphereScene();
		
		physicsManager.setupFromScene(scene);
		
		// Move camera
		//scene.getCamera().setLookpoint(  );
		
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
		
	}

}
