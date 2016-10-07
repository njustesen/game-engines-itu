package engine.rendering;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import javax.swing.JPanel;

import engine.objects.GameObject;
import engine.rendering.Scene;
import engine.math.Matrix;
import engine.math.Point3D;
import engine.math.Vector3D;
import engine.rendering.Screen;
import engine.shapes.Shape3D;
import engine.shapes.Triangle3D;
import engine.objects.Light;

public class ScanlineRenderer extends Renderer {
	
	private PriorityQueue<Triangle3D> pq;
	private double[][] zbuffer;
	
	public ScanlineRenderer(){
		
	//	lastRendering = new ArrayList<Triangle2D>();
		pq = new PriorityQueue<Triangle3D>();
		
	}

	/**
	 * Renders a 3D scene.
	 * The rendered triangles are stored locally.
	 * @param scene
	 * @param screen
	 */
	public void render(Scene scene, Screen screen, JPanel panel){
		
		
		zbuffer = new double[screen.getWidth()][screen.getHeight()];
		//ArrayList<Triangle2D> newRendering = new ArrayList<Triangle2D>();
		BufferedImage newRendering = new BufferedImage(screen.getWidth(), screen.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		//SETTING THE CAMERA LOCATION
		Matrix camTransMatrix = new Matrix(new double[][] {{1,0,0,-scene.getCamera().getX()},
				  							   {0,1,0,-scene.getCamera().getY()},
				  							   {0,0,1,-scene.getCamera().getZ()},
				  							   {0,0,0,1}});
		
		//POINTING AND ORIENTING THE CAMERA
		Vector3D povZ = new Vector3D( scene.getCamera().getLookPointX()-scene.getCamera().getX(),
									scene.getCamera().getLookPointY()-scene.getCamera().getY(),
									scene.getCamera().getLookPointZ()-scene.getCamera().getZ()).getUnitVector();
		
		Vector3D newX = scene.getCamera().getUpVector().getCrossProduct(povZ).getUnitVector();
		Vector3D newY = povZ.getCrossProduct(newX);
		
		Matrix camLookMatrix = new Matrix(new double[][]  {{newX.getX(),newX.getY(),newX.getZ(),0},
				   										   {newY.getX(),newY.getY(),newY.getZ(),0},
				   										   {povZ.getX(),povZ.getY(),povZ.getZ(),0},
				   										   {0,0,0,1}});
		
		//PERSPECTIVE/PROJECTION
		double near = 1;
		double far = 100000;
		
		double viewPlaneWidth = (Math.tan(scene.getCamera().getAngle()/2)*near)*2;
		double viewPlaneHeight = viewPlaneWidth/screen.getScreenRatio();

		
		Matrix projectionMatrix = new Matrix(new double[][]  {{(2*near)/viewPlaneWidth,0,0,0},
				   											  {0,(2*near)/viewPlaneHeight,0,0},
				   											  {0,0,-(far+near)/(far-near),(-2*far*near)/(far-near)},
				   											  {0,0,-1,0}});
		
		
		for(GameObject obj : scene.getObjects()){
			for(Shape3D shape: obj.getShapes()){
				for(Triangle3D t: shape.getTriangles()){
					
					int newColor = 0;
					
					// Triangle to object view
					Triangle3D objectTriangle = toObjectView(t, shape);
				
					// Triangle to world view
					Triangle3D worldTriangle = toWorldView(objectTriangle, obj);
					
					Triangle3D viewspaceTriangle = createViewspaceTriangle(worldTriangle, screen, camLookMatrix, camTransMatrix);
					
					//double az = viewspaceTriangle.getPointA().getZ();
					//double bz = viewspaceTriangle.getPointB().getZ();
					//double cz = viewspaceTriangle.getPointC().getZ();
					
					for(Light light: scene.getLights()){
						
						Vector3D lightV3D = light.getPosition().toVector().subtract(t.getCenter().toVector()).getUnitVector();
						
						newColor += (int)((t.getSurfaceNormal().getUnitVector().getDotProduct(lightV3D))*light.getIntensity());
	
					}
					
					//Triangle3D perspectiveTriangle = viewspaceTriangle;
					
					Triangle3D perspectiveTriangle = createPerspectiveTriangle(viewspaceTriangle, screen, projectionMatrix);
					/*
					perspectiveTriangle.getPointA().setZ(az);
					perspectiveTriangle.getPointB().setZ(bz);
					perspectiveTriangle.getPointC().setZ(cz);
					*/				
					
					perspectiveTriangle.getPointA().setZ(-perspectiveTriangle.getPointA().getZ());
					perspectiveTriangle.getPointB().setZ(-perspectiveTriangle.getPointB().getZ());
					perspectiveTriangle.getPointC().setZ(-perspectiveTriangle.getPointC().getZ());
					
					
					perspectiveTriangle.setColor(newColor);
					
					renderTriangle(perspectiveTriangle, screen, newRendering);
				
					
				}
			}
		}
		
		lastRendering = newRendering;
		
		panel.repaint();
		
	}

	private void renderTriangle(Triangle3D t, Screen screen, BufferedImage bi) {
		
		Point3D a2 = screenPoint(screen, t.getPointA());
		Point3D b2 = screenPoint(screen, t.getPointB());
		Point3D c2 = screenPoint(screen, t.getPointC());
		
		Triangle3D screenTriangle = new Triangle3D(a2,b2,c2);
		screenTriangle.setColor(t.getColor());
		Rectangle bounds = screenTriangle.getBoundingRectangle();
		
		//new
		int [] rgb = new int[3];
		rgb[0] = screenTriangle.getColor().getRed();
		rgb[1] = screenTriangle.getColor().getGreen();
		rgb[2] = screenTriangle.getColor().getBlue();
		
		/*
		Random r = new Random();
		rgb[0] = r.nextInt(255);
		rgb[1] = r.nextInt(255);
		rgb[2] = r.nextInt(255);
		*/

		for(int y = bounds.y; y <= bounds.y + bounds.height; y++){		
			Point3D pa = null;
			Point3D pb = null;
			Point3D a = screenTriangle.getPointA();
			Point3D b = screenTriangle.getPointB();
			Point3D c = screenTriangle.getPointC();
			Set<Integer> intersectionLinesBefore = new HashSet<Integer>();
			
			for(int x = bounds.x; x <= bounds.x + bounds.width; x++){		
				
				Point3D p = new Point3D(x, y, 0);
				Set<Integer> intersectionLinesNow = new HashSet<Integer>();
				
				if (lineIntersection(a, b, x, y)){	
					p.setZ(getZvalue(p, a, b));
					intersectionLinesNow.add(1);
				}
				
				if (lineIntersection(a, c, x, y)){
					p.setZ(getZvalue(p, a, c));
					intersectionLinesNow.add(2);
				}
				
				if (lineIntersection(b, c, x, y)){
					p.setZ(getZvalue(p, b, c));
					intersectionLinesNow.add(3);
				}
				
				if(intersectionLinesBefore.isEmpty() && !intersectionLinesNow.isEmpty()){
					pa = p;
				}

				boolean newIntersection = false;
				for(int i : intersectionLinesNow){
					if (!intersectionLinesBefore.contains(i)){
						newIntersection = true;
						break;
					}
				}
				
				if((!intersectionLinesBefore.isEmpty() && newIntersection)){
					pb = p;
				} 
				if (x == bounds.x + bounds.width && pb == null){
					
					pb = pa;
				}
				
				intersectionLinesBefore.addAll(intersectionLinesNow);
				
				if (pa != null && pb != null){
					break;
				}
			}
			
			fillLine(pa,pb, screen, rgb, bi);
			
		}
	//image = bi;
	pq.clear();
		
	}
	
	private void fillLine(Point3D pa, Point3D pb, Screen screen, int[] rgb, BufferedImage bi) {
		
		if (pa == null || pb == null){
			return;
		}

		int pax = (int) pa.getX();
		int pbx = (int) Math.ceil(pb.getX());
		int y = (int) pa.getY();
		
		for(int x = pax; x <= pbx; x++){
			if(x >= 0 && x < screen.getWidth() && y >= 0 && y < screen.getHeight()){
				
				double zValue = getZvalue(new Point3D(x,y,0), pa, pb);
				if(zbuffer[x][y] > zValue){
					
					zbuffer[x][y] = zValue;
					bi.getRaster().setPixel(x, y, rgb);
				}
			}
		}
	}

	private double distance(Point3D a, Point3D b){
		return Math.sqrt((a.getX()-b.getX())*(a.getX()-b.getX())+(a.getY()-b.getY())*(a.getY()-b.getY()));
	}
	
	private double getZvalue(Point3D p, Point3D a, Point3D b){
		
		double lineLength = distance(a,b);
		double ap = distance(a,p);
		double bp = distance(b,p);

		if (ap == 0) return a.getZ();
		if (bp == 0) return b.getZ();
		
		return (bp/lineLength*a.getZ())+(ap/lineLength*b.getZ());
	}
	
	private boolean lineIntersection(Point3D a, Point3D b, int x, int y){
		
		double XDif = Math.abs(a.getX() - b.getX());
		double YDif = Math.abs(a.getY() - b.getY());
		
		if(XDif > YDif){
			return lineIntersectionY(a,b,x,y);
		}else{
			return lineIntersectionX(a,b,x,y);
		}
		
	}
	
	private boolean lineIntersectionY(Point3D a, Point3D b, int x, int y){
		
		int ax = (int) Math.round(a.getX());
		int ay = (int) Math.round(a.getY());
		int bx = (int) Math.round(b.getX());
		int by = (int) Math.round(b.getY());
		
		if(x > Math.max(ax, bx) || x < Math.min(ax, bx)
				|| y > Math.max(ay, by) || y < Math.min(ay, by)){
			return false;
		}
		//return y == (by-ay)/(bx-ax)*(x-ax)+ay;
		return y == (int) Math.round((b.getY()-a.getY())/(b.getX()-a.getX())*(x-a.getX())+a.getY());
	}
	
	private boolean lineIntersectionX(Point3D a, Point3D b, int x, int y){
		
		int ax = (int) Math.round(a.getX());
		int ay = (int) Math.round(a.getY());
		int bx = (int) Math.round(b.getX());
		int by = (int) Math.round(b.getY());
		
		if(x > Math.max(ax, bx) || x < Math.min(ax, bx)
				|| y > Math.max(ay, by) || y < Math.min(ay, by)){
			return false;
		}
		//return x == (y-ay)/(by-ay)*(bx-ax)+ax;
		return x == (int) Math.round((y-a.getY())/(b.getY()-a.getY())*(b.getX()-a.getX())+a.getX());
	}
}
