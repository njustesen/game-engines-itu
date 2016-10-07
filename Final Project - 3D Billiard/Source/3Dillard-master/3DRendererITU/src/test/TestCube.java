package test;

import engine.math.Point3D;
import engine.objects.GameObject;
import engine.shapes.Cube;

public class TestCube extends GameObject{
	
	public TestCube(Point3D position){
		super(position);
		build();
	}
	
	@Override
	public void build() {
		
		addShape(new Cube(Point3D.Zero,100,100,100));
		
	}
	
}
