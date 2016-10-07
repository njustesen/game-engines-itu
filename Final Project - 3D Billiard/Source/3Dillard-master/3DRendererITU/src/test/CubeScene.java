package test;

import java.awt.Color;

import engine.objects.Camera;
import engine.rendering.Scene;
import engine.math.Point3D;
import engine.math.Vector3D;
import engine.objects.Light;

public class CubeScene extends Scene {

	@Override
	public void buildShapes() {
		
		objects.add(new TestCube(new Point3D(0,0,0)));
		
		Light light = new Light(new Point3D(0,1000,600), 100, Color.white);
		lights.add(light);

	}

	@Override
	public void setCamera() {
		
		camera = new Camera(new Point3D(0,0,600),
							new Point3D(0,0,0),
							new Vector3D(0,1,0), 70);
		
		camera.setMovementSpeed(30);
		camera.setRotationSpeed(30);
		
	}

}
