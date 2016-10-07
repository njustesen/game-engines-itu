package engine.objects;

import java.awt.Color;
import engine.math.Point3D;

public class Light{

	private Point3D position;
	private int intensity;
	private Color color;
	
	public Light(Point3D position, int intensity, Color color){
		this.position = position;
		this.intensity = intensity;
		this.color = color;
	}
	
	public Point3D getPosition(){
		return position;
	}
	
	public float getIntensity(){
		return intensity;
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setPosition(Point3D newPosition){
		this.position = newPosition;
	}
	
	public void setIntensity(int newIntensity){
		this.intensity = newIntensity;
	}
	
	public void setColor(Color newColor){
		this.color = newColor;
	}
}
