package test;


import engine.math.Point3D;
import engine.objects.GameObject;
import engine.shapes.Sphere;

public class TestSphere extends GameObject{
	
	public TestSphere(Point3D position){
		super(position);
		build();
	}
	
	@Override
	public void build() {
		
		addShape(new Sphere(Point3D.Zero, 50, 32));
		
	}
	
}
