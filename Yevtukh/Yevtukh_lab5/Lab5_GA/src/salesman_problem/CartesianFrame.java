package salesman_problem;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;


	
class CartesianFrame extends JFrame {
 CartesianPanel panel;
 
 public CartesianFrame() {
  panel = new CartesianPanel();
  add(panel);
 }
 
 public void showUI() {
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setTitle("Final Results");
  setSize(700, 700);
  setVisible(true);
 }
}

class CartesianPanel extends JPanel {
 // x-axis координати
 public static final int X_AXIS_FIRST_X_COORD = 50;
 public static final int X_AXIS_SECOND_X_COORD = 600;
 public static final int X_AXIS_Y_COORD = 600;
 
 // y-axis координами
 public static final int Y_AXIS_FIRST_Y_COORD = 50;
 public static final int Y_AXIS_SECOND_Y_COORD = 600;
 public static final int Y_AXIS_X_COORD = 50;

 public static final int FIRST_LENGHT = 10;
 public static final int SECOND_LENGHT = 5;
 

 public static final int ORIGIN_COORDINATE_LENGHT = 6;

 public static final int AXIS_STRING_DISTANCE = 20;
 
 // нумерація осей 
 int xCoordNumbers = 21;
 int yCoordNumbers = 21;
 int xLength = (X_AXIS_SECOND_X_COORD - X_AXIS_FIRST_X_COORD)/ xCoordNumbers;
 int yLength = (Y_AXIS_SECOND_Y_COORD - Y_AXIS_FIRST_Y_COORD)/ yCoordNumbers;
 
 public void paintComponent(Graphics g) {
  
  super.paintComponent(g);
  
  Graphics2D g2 = (Graphics2D) g;
  
  g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    RenderingHints.VALUE_ANTIALIAS_ON);
  
  // x-axis
  g2.drawLine(X_AXIS_FIRST_X_COORD, X_AXIS_Y_COORD,
     X_AXIS_SECOND_X_COORD, X_AXIS_Y_COORD);
  // y-axis
  g2.drawLine(Y_AXIS_X_COORD, Y_AXIS_FIRST_Y_COORD,
     Y_AXIS_X_COORD, Y_AXIS_SECOND_Y_COORD);
  
  // x-axis стрілка
  g2.drawLine(X_AXIS_SECOND_X_COORD - FIRST_LENGHT,
     X_AXIS_Y_COORD - SECOND_LENGHT,
     X_AXIS_SECOND_X_COORD, X_AXIS_Y_COORD);
  g2.drawLine(X_AXIS_SECOND_X_COORD - FIRST_LENGHT,
    X_AXIS_Y_COORD + SECOND_LENGHT,
    X_AXIS_SECOND_X_COORD, X_AXIS_Y_COORD);
  
  // y-axis стрілка
  g2.drawLine(Y_AXIS_X_COORD - SECOND_LENGHT,
     Y_AXIS_FIRST_Y_COORD + FIRST_LENGHT,
     Y_AXIS_X_COORD, Y_AXIS_FIRST_Y_COORD);
  g2.drawLine(Y_AXIS_X_COORD + SECOND_LENGHT, 
     Y_AXIS_FIRST_Y_COORD + FIRST_LENGHT,
     Y_AXIS_X_COORD, Y_AXIS_FIRST_Y_COORD);
  
  // малює початок координат
  g2.fillOval(
    X_AXIS_FIRST_X_COORD - (ORIGIN_COORDINATE_LENGHT / 2), 
    Y_AXIS_SECOND_Y_COORD - (ORIGIN_COORDINATE_LENGHT / 2),
    ORIGIN_COORDINATE_LENGHT, ORIGIN_COORDINATE_LENGHT);
  
  // малює надписи "Х" "У"
  g2.drawString("X", X_AXIS_SECOND_X_COORD - AXIS_STRING_DISTANCE / 2,
     X_AXIS_Y_COORD + AXIS_STRING_DISTANCE);
  g2.drawString("Y", Y_AXIS_X_COORD - AXIS_STRING_DISTANCE,
     Y_AXIS_FIRST_Y_COORD + AXIS_STRING_DISTANCE / 2);
  g2.drawString("(0, 0)", X_AXIS_FIRST_X_COORD - AXIS_STRING_DISTANCE,
     Y_AXIS_SECOND_Y_COORD + AXIS_STRING_DISTANCE);
  
  // нумерація осі х
  for(int i = 1; i < xCoordNumbers; i++) {
   g2.drawLine(X_AXIS_FIRST_X_COORD+ (i * xLength),
     X_AXIS_Y_COORD - SECOND_LENGHT,
     X_AXIS_FIRST_X_COORD + (i * xLength),
     X_AXIS_Y_COORD + SECOND_LENGHT);
   
   g2.drawString(Integer.toString(i*10), 
     X_AXIS_FIRST_X_COORD + (i * xLength) - 3,
     X_AXIS_Y_COORD + AXIS_STRING_DISTANCE);
  }
  
  //нумерація осі у
  for(int i = 1; i < yCoordNumbers; i++) {
   g2.drawLine(Y_AXIS_X_COORD - SECOND_LENGHT,
     Y_AXIS_SECOND_Y_COORD - (i * yLength), 
     Y_AXIS_X_COORD + SECOND_LENGHT,
     Y_AXIS_SECOND_Y_COORD - (i * yLength));
   g2.drawString(Integer.toString(i*10), 
     Y_AXIS_X_COORD - AXIS_STRING_DISTANCE, 
     Y_AXIS_SECOND_Y_COORD - (i * yLength));
  }

//відобрадження міст на графіку
 int x0=X_AXIS_FIRST_X_COORD - (ORIGIN_COORDINATE_LENGHT / 2);
 int y0=Y_AXIS_SECOND_Y_COORD - (ORIGIN_COORDINATE_LENGHT / 2);
 
 for(int i=0;i<TSP.nsity;i++){
	 	   
	 int x=x0+(TSP.x[i]*xLength)/10;
	 int y=y0-(TSP.y[i]*yLength)/10;
	 
	 g2.fillOval(x,y,ORIGIN_COORDINATE_LENGHT, ORIGIN_COORDINATE_LENGHT);
	  
 }
 
//відображення лініями знайденого марштуру 
 for(int i=0;i<TSP.nsity;i++){ 
	 int x1,x2,y1,y2;
	 if(i+1!=TSP.nsity){
		  x1=x0+(TSP.xline[i]*xLength)/10;
		  x2=x0+(TSP.xline[i+1]*xLength)/10;
		  y1=y0-(TSP.yline[i]*yLength)/10;
		  y2=y0-(TSP.yline[i+1]*yLength)/10; 
	 }
	 else{
		 x1=x0+(TSP.xline[i]*xLength)/10;
		 x2=x0+(TSP.xline[0]*xLength)/10;
		 y1=y0-(TSP.yline[i]*yLength)/10;
		 y2=y0-(TSP.yline[0]*yLength)/10; 
	 }
	 g2.drawLine(x1,y1, x2, y2);
  }
 }
 
}
 