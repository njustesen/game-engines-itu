package game;

import java.awt.Color;
import engine.math.Point3D;
import engine.math.Vector3D;
import engine.objects.Light;
import engine.objects.BilliardBall;
import engine.objects.BilliardCamera;
import engine.objects.BilliardTable;
import engine.rendering.Scene;

public class EightBallScene extends Scene {
	
	BilliardBall cueBall;

	@Override
	public void buildShapes() {
		
		double triangleX = -80;
		double diameter = 5.4;
		double offset = 0.95;
		double depth = 0;
		
		cueBall = new BilliardBall(0, diameter/2, new Point3D(100, 0, depth));
		
		objects.add( cueBall );
				
		objects.add( new BilliardBall(1, diameter/2, new Point3D(triangleX-diameter*offset, 0, depth)) );
		
		objects.add( new BilliardBall(2, diameter/2, new Point3D(triangleX-diameter*2*offset, diameter/2, depth)) );
		objects.add( new BilliardBall(3, diameter/2, new Point3D(triangleX-diameter*2*offset, -diameter/2, depth)) );
		
		objects.add( new BilliardBall(2, diameter/2, new Point3D(triangleX-diameter*3*offset, diameter, depth)) );
		objects.add( new BilliardBall(3, diameter/2, new Point3D(triangleX-diameter*3*offset, 0, depth)) );
		objects.add( new BilliardBall(2, diameter/2, new Point3D(triangleX-diameter*3*offset, -diameter, depth)) );
		
		objects.add( new BilliardBall(2, diameter/2, new Point3D(triangleX-diameter*4*offset, diameter + diameter/2, depth)) );
		objects.add( new BilliardBall(3, diameter/2, new Point3D(triangleX-diameter*4*offset, diameter/2, depth)) );
		objects.add( new BilliardBall(2, diameter/2, new Point3D(triangleX-diameter*4*offset, -diameter/2, depth)) );
		objects.add( new BilliardBall(2, diameter/2, new Point3D(triangleX-diameter*4*offset, -(diameter + diameter/2), depth)) );
		
		objects.add( new BilliardBall(2, diameter/2, new Point3D(triangleX-diameter*5*offset, diameter*2, depth)) );
		objects.add( new BilliardBall(3, diameter/2, new Point3D(triangleX-diameter*5*offset, diameter, depth)) );
		objects.add( new BilliardBall(2, diameter/2, new Point3D(triangleX-diameter*5*offset, 0, depth)) );
		objects.add( new BilliardBall(2, diameter/2, new Point3D(triangleX-diameter*5*offset, -diameter, depth)) );
		objects.add( new BilliardBall(2, diameter/2, new Point3D(triangleX-diameter*5*offset, -diameter*2, depth)) );
		
		BilliardTable table = new BilliardTable(new Point3D(0,0,-diameter*1.5), 140, 270, 10, 20, diameter*1.8, 0.010, 0.2);
		objects.add(table);
		
		Light lightA = new Light(new Point3D(0,0,-100), 100, Color.white);
		lights.add(lightA);
		
		Light lightB = new Light(new Point3D(0,160,-80), 60, Color.white);
		lights.add(lightB);
		
		Light lightC = new Light(new Point3D(0,-160,-80), 60, Color.white);
		lights.add(lightC);
		
	}

	@Override
	public void setCamera() {
		
		camera = new BilliardCamera(new Point3D(400,0,200),
							new Point3D(0,0,0),
							new Vector3D(0,0,1), 70);
		
		camera.setMovementSpeed(30);
		camera.setRotationSpeed(30);
		
	}

	public BilliardBall getCueBall() {
		return cueBall;
	}

}
