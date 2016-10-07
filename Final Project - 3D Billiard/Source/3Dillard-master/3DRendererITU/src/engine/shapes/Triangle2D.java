package engine.shapes;

import java.awt.Color;

import engine.math.Point2D;

public class Triangle2D {

	Point2D a;
	Point2D b;
	Point2D c;
	Color color;
	
	public Triangle2D(Point2D a, Point2D b, Point2D c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public Triangle2D(Point2D a, Point2D b, Point2D c, Color color) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.color = color;
	}
	
	public Point2D getA() {
		return a;
	}
	
	public void setA(Point2D a) {
		this.a = a;
	}
	
	public Point2D getB() {
		return b;
	}
	
	public void setB(Point2D b) {
		this.b = b;
	}
	
	public Point2D getC() {
		return c;
	}
	
	public void setC(Point2D c) {
		this.c = c;
	}	
	
	public Color getColor(){
		return this.color;	
	}
	
	public void setColor(Color newColor){
		this.color = newColor;
	}
	
	public void setColor(int r, int g, int b){
		this.color = new Color(r, g, b);
	}
	
	public void setColor(int c){
		if(c <= 255 && c > 0)
			this.color = new Color(c, c, c);
		else if(c <= 0){
			this.color = new Color(0, 0, 0);
		}else this.color = new Color(255, 255, 255);
	}
}
