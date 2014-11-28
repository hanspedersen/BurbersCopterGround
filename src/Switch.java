import processing.core.PApplet;
import processing.core.PFont;


public class Switch extends PApplet{
	
	private boolean status;
	private PApplet parent;
	
	private int radius = 4;
	private int height = 22;
	private int width = 8;
	private int xPos;
	private int yPos;
	private String label; 
	
	public Switch(PApplet p, boolean set, int x, int y, String l){
		status = set;
		parent = p;
		xPos = x;
		yPos = y;
		label = l;
		
	}
	
	public void draw() {
		//Draw frame
		parent.stroke(10);
		parent.fill(130);
		parent.rect(xPos, yPos, width, height, radius, radius, radius, radius);
		if(status) {
			parent.fill(121, 242, 149);
			parent.rect(xPos, yPos+2, width, width, radius, radius, radius, radius);
		}else{
			
		}
		PFont myFont = createFont("Verdana", 12);
		parent.textFont(myFont, 10);
		parent.textSize(10);
		parent.fill(40);
		parent.text(label, xPos+width+8, yPos+(height/2));
	}

}
