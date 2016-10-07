package engine.physics;

import engine.rendering.Scene;

public abstract class PhysicsManager {

	public abstract void move(long delta);
	
	public abstract void setupFromScene(Scene scene);
	
}
