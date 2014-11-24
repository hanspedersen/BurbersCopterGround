import processing.core.PApplet;
import processing.core.PImage;

/* Base class for all panels*/

public abstract class Panel {
	// All panels have a width and a height
	int panelWidth;
	int panelHeight;
	// And a position (upper left corner)
	int panelXPos;
	int panelYPos;
	public static final int panelMargin = 10;
	// Background color
	int backgroundColor;
	// Processing parent.
	PApplet parent;
	
	
	Panel(int x, int y, int width, int height, PApplet p) {
		panelWidth = width;
		panelHeight =height;
		panelXPos=x;
		panelYPos=y;
		parent = p;
		backgroundColor = parent.color(255);
		
	}
	
	
	
	public int getPanelWidth() {
		return panelWidth;
	}
	public void setPanelWidth(int panelWidth) {
		this.panelWidth = panelWidth;
	}
	public int getPanelHeight() {
		return panelHeight;
	}
	public void setPanelHeight(int panelHeight) {
		this.panelHeight = panelHeight;
	}
	
	public abstract void drawPanel();
	
	public void drawPanelFrame() {
		parent.stroke(parent.color(200));
		parent.fill(backgroundColor);
		parent.rect(panelXPos, parent.height-panelHeight-1, panelWidth, panelHeight);
		
	}
	
	
}
