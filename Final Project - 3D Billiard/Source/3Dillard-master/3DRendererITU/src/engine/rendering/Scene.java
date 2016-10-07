package engine.rendering;

import engine.objects.Camera;
import engine.objects.GameObject;
import engine.objects.Light;

import java.util.ArrayList;

public abstract class Scene {

	protected ArrayList <GameObject> objects;
	protected ArrayList <Light> lights;
	protected Camera camera;
	
	public Scene(){
		objects = new ArrayList<GameObject>();
		lights = new ArrayList<Light>();
		buildShapes();
		setCamera();
	}
	
	public abstract void buildShapes();
	public abstract void setCamera();

	public ArrayList<GameObject> getObjects() {
		return objects;
	}
	
	public ArrayList<Light> getLights() {
		return lights;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public void setObjects(ArrayList<GameObject> objects) {
		this.objects = objects;
	}
	
}
