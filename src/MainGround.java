import java.awt.Window;
import java.util.ArrayList;
import java.util.Iterator;

import processing.core.*;
import processing.serial.*;



public class MainGround extends PApplet {
	
  int averageX = 0;
  int averageY = 0;
  double[] angleBuffer;
  
  int initialWindowWidth = 1024;
  int initialWindowHeight = 760;

  Console console;
  
  Serial arduino;
  String value; // Value from raw serial read
  PFont font;
  PanelTop paneltop= new PanelTop(this);
  PanelMotors panelmotors = new PanelMotors(10, 90, 150, 150, this);
  PanelStabilizationX panelstabilizationX = new PanelStabilizationX(this);
  GraphsPanel graphsPanel = new GraphsPanel(0, initialWindowHeight-300, this);
  String buf="";
  public static Mode selectedMode;
  public ArrayList<String> commands;

  public void setup() {
	font = loadFont("fonts/Cambria-16.vlw");
	size(initialWindowWidth, initialWindowHeight);
	smooth();
	textFont(font);
	console = new Console(this);
	commands = new ArrayList<String>();
	
	// 4 angles for now
	  angleBuffer = new double[4];
	  angleBuffer[0] = 0.0;
	  angleBuffer[1] = 0.0;
	  angleBuffer[2] = 0.0;
	  angleBuffer[3] = 0.0;
	  
	  // Prints out the available serial ports.
	  arduino = new Serial(this, "COM3", 19200);
	  arduino.bufferUntil('\n');
	  
	  for(int k = 0; k<Serial.list().length; k++) {
		  System.out.println(Serial.list()[k]);
	  }
	
  }

  public void draw() {
	// Print commands
	  if(commands.size()>0) {
	    arduino.write(commands.remove(0));
	    arduino.write("\n");
	    print("Printing something to arduino");
	  }
	  
	// Draw the main frame
	background(249);
	
	// Check events
	paneltop.checkMousePanelTop();
	
	// Draw tools
	  paneltop.drawPanelTop();
	  console.drawConsole();
	  graphsPanel.drawPanel();
	// Draw motor panel
	  panelmotors.drawPanel();
	  
	// Draw stabilizationpanel X
	  panelstabilizationX.drawPanelStabilizationX();
    
  }
  
  
  public void serialEvent (Serial arduino) {
	  int startOfMessage = -1;
	  int endOfMessage = 0;
	  //Read from Arduino
	  value = arduino.readStringUntil('\n');
	  if(value!=null) {
	  String val2 = value.trim();
	  // Write to the console first.
	  console.textBuffer=val2;    
	          
	  // Process input from arduino correctly
	  for(int j=0; j<val2.length(); j++) {
	  if(val2.charAt(j)=='#') {
	    if(startOfMessage != -1){
	        endOfMessage = j;
	     }else{
	        startOfMessage = j; 
	     }
	   }
	   }
	          String messageToProc = "";
	          if((startOfMessage > -1) && (endOfMessage >-1)) {
	            messageToProc = val2.substring(startOfMessage+1, endOfMessage);
	          }
	          
	          if(messageToProc.length()>0){
	            if(messageToProc.substring(0, 2).equals("m1")) {
	               String speed = messageToProc.substring(3, messageToProc.length());
	               int intSpeed = parseInt(speed);
	               panelmotors.setIndividualMotorSpeed(0, intSpeed);
	            }else if(messageToProc.substring(0, 2).equals("m2")){
	               String speed = messageToProc.substring(3, messageToProc.length());
	               int intSpeed = parseInt(speed);
	               panelmotors.setIndividualMotorSpeed(1, intSpeed);
	            }else if(messageToProc.substring(0, 2).equals("xa")){
	              // Angle used measurement (Complementary or kalman)
	              String xAngle = messageToProc.substring(3, messageToProc.length());
	              double doubleAngle = (double)parseFloat(xAngle);
	              angleBuffer[0] = doubleAngle;
	            }else if(messageToProc.substring(0, 2).equals("xg")) {
	              // Gyro measurement
	              String xGyro = messageToProc.substring(3, messageToProc.length());
	              angleBuffer[1] = (double)parseFloat(xGyro);
	            }else if(messageToProc.substring(0, 2).equals("xc")) {
	              // Accelerometer measurement
	              String xAcc = messageToProc.substring(3, messageToProc.length());
	              angleBuffer[2] = (double)parseFloat(xAcc);
	            }
	            
	           panelstabilizationX.update( angleBuffer[0] , random(20), angleBuffer[1], angleBuffer[2], panelmotors.getIndividualMotorSpeed(0), panelmotors.getIndividualMotorSpeed(1));
	           graphsPanel.update( angleBuffer[0] , random(20), angleBuffer[1], angleBuffer[2], panelmotors.getIndividualMotorSpeed(0), panelmotors.getIndividualMotorSpeed(1));
	            
	          }
	          
	          if(val2.equals("#MODE#")) {
	                paneltop.selectMode(true);
	          }
	        }
	}
  

	public void mousePressed() {
	    paneltop.PanelTopPress();
	}

  
  
	  public void addCommand(String command) {
		  commands.add(command);
	  }
  
}