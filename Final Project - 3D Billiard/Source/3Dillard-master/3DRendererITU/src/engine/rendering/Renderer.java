package engine.rendering;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import engine.rendering.Screen;
import engine.math.Matrix;
import engine.math.Point2D;
import engine.math.Point3D;
import engine.objects.GameObject;
import engine.shapes.Shape3D;
import engine.shapes.Triangle3D;

public abstract class Renderer {
	
	protected BufferedImage lastRendering;

	public abstract void render(Scene scene, Screen screen, JPanel panel);

	public BufferedImage getLastRendering() {
		return lastRendering;
	}
	
	/**
	 * Renders a vertex.
	 * @param p
	 * @param screen
	 * @param projectionMatrix
	 * @param cameraLookMatrix
	 * @param cameraTransMatrix
	 * @return
	 */
	protected Triangle3D createViewspaceTriangle(	Triangle3D t, 
												Screen screen,  
												Matrix cameraLookMatrix, 
												Matrix cameraTransMatrix){
		
		Matrix viewspaceMatrix = cameraLookMatrix.multiplication(cameraTransMatrix);

		Point3D viewspacePointA = viewspaceMatrix.multiplication(t.getPointA());
		Point3D viewspacePointB = viewspaceMatrix.multiplication(t.getPointB());
		Point3D viewspacePointC = viewspaceMatrix.multiplication(t.getPointC());
		
		Triangle3D viewspaceTriangle = new Triangle3D(viewspacePointA, viewspacePointB, viewspacePointC);
		
		return viewspaceTriangle;
	}
	
	protected Triangle3D createPerspectiveTriangle(Triangle3D t, 
											Screen screen, 
											Matrix perspectiveMatrix){

		Point3D viewspacePointA = perspectiveMatrix.multiplication(t.getPointA());
		Point3D viewspacePointB = perspectiveMatrix.multiplication(t.getPointB());
		Point3D viewspacePointC = perspectiveMatrix.multiplication(t.getPointC());
		
		viewspacePointA.setX(viewspacePointA.getX()/viewspacePointA.getW());
		viewspacePointA.setY(viewspacePointA.getY()/viewspacePointA.getW());
		viewspacePointA.setZ(viewspacePointA.getZ()/viewspacePointA.getW());
		
		viewspacePointB.setX(viewspacePointB.getX()/viewspacePointB.getW());
		viewspacePointB.setY(viewspacePointB.getY()/viewspacePointB.getW());
		viewspacePointB.setZ(viewspacePointB.getZ()/viewspacePointB.getW());
		
		viewspacePointC.setX(viewspacePointC.getX()/viewspacePointC.getW());
		viewspacePointC.setY(viewspacePointC.getY()/viewspacePointC.getW());
		viewspacePointC.setZ(viewspacePointC.getZ()/viewspacePointC.getW());
			
		return new Triangle3D(viewspacePointA, viewspacePointB, viewspacePointC);
	}
	
	protected Triangle3D toObjectView(Triangle3D t, Shape3D shape) {
		return new Triangle3D(new Point3D(t.getPointA().getX() + shape.getAnchor().getX(),
											t.getPointA().getY() + shape.getAnchor().getY(),
												t.getPointA().getZ() + shape.getAnchor().getZ()),
							  new Point3D(t.getPointB().getX() + shape.getAnchor().getX(),
									  		t.getPointB().getY() + shape.getAnchor().getY(),
									  			t.getPointB().getZ() + shape.getAnchor().getZ()),
							  new Point3D(t.getPointC().getX() + shape.getAnchor().getX(),
									  		t.getPointC().getY() + shape.getAnchor().getY(),
									  			t.getPointC().getZ() + shape.getAnchor().getZ()));
	}

	protected Triangle3D toWorldView(Triangle3D t, GameObject obj) {
		
		return new Triangle3D(new Point3D(t.getPointA().getX() + obj.getPosition().getX(),
											t.getPointA().getY() + obj.getPosition().getY(),
												t.getPointA().getZ() + obj.getPosition().getZ()),
							  new Point3D(t.getPointB().getX() + obj.getPosition().getX(),
											t.getPointB().getY() + obj.getPosition().getY(),
												t.getPointB().getZ() + obj.getPosition().getZ()),
							  new Point3D(t.getPointC().getX() + obj.getPosition().getX(),
											t.getPointC().getY() + obj.getPosition().getY(),
												t.getPointC().getZ() + obj.getPosition().getZ())
							);
	}

	/**
	 * Renders a vertex.
	 * @param p
	 * @param screen
	 * @param projectionMatrix
	 * @param cameraLookMatrix
	 * @param cameraTransMatrix
	 * @return
	 */
	protected Triangle3D createViewspaceTriangle(	Triangle3D t, 
												Screen screen, 
												Matrix projectionMatrix, 
												Matrix cameraLookMatrix, 
												Matrix cameraTransMatrix){
		
		Matrix viewspaceMatrix = projectionMatrix.multiplication(cameraLookMatrix.multiplication(cameraTransMatrix));

		Point3D viewspacePointA = viewspaceMatrix.multiplication(t.getPointA());
		Point3D viewspacePointB = viewspaceMatrix.multiplication(t.getPointB());
		Point3D viewspacePointC = viewspaceMatrix.multiplication(t.getPointC());
			
		viewspacePointA.setX(viewspacePointA.getX()/viewspacePointA.getW());
		viewspacePointA.setY(viewspacePointA.getY()/viewspacePointA.getW());
		viewspacePointA.setZ(viewspacePointA.getZ()/viewspacePointA.getW());
		
		viewspacePointB.setX(viewspacePointB.getX()/viewspacePointB.getW());
		viewspacePointB.setY(viewspacePointB.getY()/viewspacePointB.getW());
		viewspacePointB.setZ(viewspacePointB.getZ()/viewspacePointB.getW());
		
		viewspacePointC.setX(viewspacePointC.getX()/viewspacePointC.getW());
		viewspacePointC.setY(viewspacePointC.getY()/viewspacePointC.getW());
		viewspacePointC.setZ(viewspacePointC.getZ()/viewspacePointC.getW());
		
		Triangle3D viewspaceTriangle = new Triangle3D(viewspacePointA, viewspacePointB, viewspacePointC);
		
		return viewspaceTriangle;
	}
	
	protected Point2D make2D(Screen screen, Point3D viewspacePoint){
		
		int x = (int) (viewspacePoint.getX()*(screen.getWidth()/2)+(screen.getWidth()/2));
		int y = (int) (viewspacePoint.getY()*(screen.getHeight()/2)+(screen.getHeight()/2));
		
		return new Point2D(x,y);
		
	}
	
	protected Point3D screenPoint(Screen screen, Point3D viewspacePoint){
		
		int x = (int) (viewspacePoint.getX()*(screen.getWidth()/2)+(screen.getWidth()/2));
		int y = (int) (viewspacePoint.getY()*(screen.getHeight()/2)+(screen.getHeight()/2));
		double z = viewspacePoint.getZ();
		
		return new Point3D(x, y, z);
		
	}
	
}
