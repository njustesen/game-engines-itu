package engine.objects;

import java.util.ArrayList;

import engine.math.Point3D;
import engine.shapes.Shape3D;

public abstract class GameObject {

	protected ArrayList<Shape3D> shapes;
	protected Point3D position;
	protected boolean visible;
	
	public GameObject(Point3D position){
		this.shapes = new ArrayList<Shape3D>();
		this.position = position;
		this.visible = true;
	}
	
	protected abstract void build();

	public ArrayList<Shape3D> getShapes() {
		return shapes;
	}

	public void setShape(ArrayList<Shape3D> shapes) {
		this.shapes = shapes;
	}
	
	public void addShape(Shape3D shape){
		shapes.add(shape);
	}

	public Point3D getPosition() {
		return position;
	}

	public void setPosition(Point3D position) {
		this.position = position;
	}

	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible (boolean visible){
	
		this.visible = visible;
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameObject other = (GameObject) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}
	
	
	
}
