package engine.input;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import engine.math.Point2D;


public class InputManager implements KeyListener, MouseListener, MouseMotionListener {
	
	boolean leftDown;
	boolean rightDown;
	boolean upDown;
	boolean downDown;
	boolean spaceDown;
	boolean shiftDown;
	boolean wDown;
	boolean sDown;
	boolean aDown;
	boolean dDown;
	boolean Down1;
	boolean Down2;
	boolean Down3;
	boolean controlDown;
	Point2D mousePosition;
	long mouseDownTimeStart;
	long mouseDownTime;
	boolean mouseRightDown;
	boolean mouseLeftDown;

	public InputManager() {
		
	}

	public int getHorizontalArrows(){
		int h = 0;
		if (leftDown) h--;
		if (rightDown) h++;
		return h;
	}
	
	public int getVerticalArrows(){
		int v = 0;
		if (upDown) v--;
		if (downDown) v++;
		return v;
	}
	
	public int getHorizontalWASD(){
		int h = 0;
		if (aDown) h--;
		if (dDown) h++;
		return h;
	}
	
	public int getVerticalWASD(){
		int v = 0;
		if (wDown) v--;
		if (sDown) v++;
		return v;
	}
	
	public Point2D getMousePosition() {
		return new Point2D(MouseInfo.getPointerInfo().getLocation().getX(), MouseInfo.getPointerInfo().getLocation().getY());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT: leftDown = true; break;
			case KeyEvent.VK_RIGHT: rightDown = true; break;
			case KeyEvent.VK_UP: upDown = true; break;
			case KeyEvent.VK_DOWN: downDown = true; break;
			case KeyEvent.VK_W: wDown = true; break;
			case KeyEvent.VK_S: sDown = true; break;
			case KeyEvent.VK_A: aDown = true; break;
			case KeyEvent.VK_D: dDown = true; break;
			case KeyEvent.VK_1: Down1 = true; break;
			case KeyEvent.VK_2: Down2 = true; break;
			case KeyEvent.VK_3: Down3 = true; break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT: leftDown = false; break;
			case KeyEvent.VK_RIGHT: rightDown = false; break;
			case KeyEvent.VK_UP: upDown = false; break;
			case KeyEvent.VK_DOWN: downDown = false; break;
			case KeyEvent.VK_W: wDown = false; break;
			case KeyEvent.VK_S: sDown = false; break;
			case KeyEvent.VK_A: aDown = false; break;
			case KeyEvent.VK_D: dDown = false; break;
			case KeyEvent.VK_1: Down1 = false; break;
			case KeyEvent.VK_2: Down2 = false; break;
			case KeyEvent.VK_3: Down3 = false; break;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseLeftDown = true;
		mouseDownTimeStart = System.currentTimeMillis();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseLeftDown = false;
		mouseDownTime = System.currentTimeMillis() - mouseDownTimeStart;
		mouseDownTimeStart = 0;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePosition = new Point2D(e.getPoint().getX(), e.getPoint().getY());
		
	}

	public long getMouseDownTime() {
		return mouseDownTime;
	}
	
	public boolean isMouseLeftDown(){
		return mouseLeftDown;
	}

	public boolean isDown1() {
		return Down1;
	}

	public boolean isDown2() {
		return Down2;
	}

	public boolean isDown3() {
		return Down3;
	}
	
}
