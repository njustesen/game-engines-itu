package engine.rendering;

import engine.math.Point2D;

public class Screen {

	int width;
	int height;
	
	public Screen(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	/**
	 * Returns true if the given 2D point is outside of the screen.
	 * @param p
	 * @return
	 */
	public boolean isOutsideScreen(Point2D p) {
		if (p.getX() < 0 || p.getX() >= width){
			return true;
		}
		if (p.getY() < 0 || p.getY() >= height){
			return true;
		}
		return false;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getScreenRatio() {
		return (double)width/(double)height;
	}
	
}
