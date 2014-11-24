import processing.core.PApplet;
import processing.core.PImage;


public class GraphsPanel extends Panel {
	
	public static int graphPanelWidth = 1024;
	public static int graphsPanelHeight = 400;
	double xAngle;
	double xAngleSpeed;
	PImage bgPanelMenu;
	
	PanelAngleHistogram panelanglehistogram;
	
	public GraphsPanel(int x, int y, PApplet p) {
		super(x, y, graphPanelWidth, graphsPanelHeight, p);
		panelanglehistogram = new PanelAngleHistogram(x, y+21, graphPanelWidth, 300, p);
		// TODO Auto-generated constructor stub
		bgPanelMenu = p.loadImage("img/Graphs_top.jpg");
		
	}

	@Override
	public void drawPanel() {
		// TODO Auto-generated method stub
		super.drawPanelFrame();
		parent.image(bgPanelMenu, panelXPos, parent.height-panelHeight, panelWidth, 20);
		panelanglehistogram.drawHistogram();
	}
	
	void update(double angle, double angleSpeed, double gyroAngle, double accAngle, int motor1Speed, int motor2Speed) {
	    xAngle = angle;
	    xAngleSpeed = angleSpeed;
	    panelanglehistogram.push(angle, gyroAngle, accAngle);
	  }

}
