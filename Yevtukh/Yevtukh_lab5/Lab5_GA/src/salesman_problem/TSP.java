package salesman_problem;

import java.awt.Graphics2D;
import java.util.Date;

import javax.swing.SwingUtilities;



public class TSP {
	    //кількість міст
	    static int nsity=20;
	    //популяція
    	static int npop=200;
    	
    	static int [] x=new int[nsity];
    	static int [] y=new int[nsity];
    	static int [] xline= new int[nsity];
    	static int [] yline= new int[nsity];
    	
    public static void main(String[] args) {

    	//створення координат міст
    	System.out.println("Кординати міст:");
    	for (int i = 0; i < nsity ; i++)
        {
         int newx = (int)(Math.random()*200);
         x[i]=newx;
         int newy = (int)(Math.random()*200);
         y[i]=newy;
         System.out.print("["+x[i]+","+y[i]+"],");
         if(i%10==9 ){
        	 System.out.println();
         }
        }
       
    	// додавання міст
    	for(int i = 0; i < x.length; i++){
    		TourManager.addCity(new City(x[i],y[i]));
    	}
    
        Population pop = new Population(npop, true);
        System.out.println("\nРозмір популяції: "+npop);
        Date currentTimeBefore = new Date();
        long timeBefore = currentTimeBefore.getTime();
    
        pop = GA.evolvePopulation(pop);
       
        for (int i = 0; i < 100; i++) {
        
            pop = GA.evolvePopulation(pop);
        }
        Date currentTimeAfter = new Date();
        long timeAfter= currentTimeAfter.getTime();;
        long time = timeAfter-timeBefore;
     
        //вивід результатів
        System.out.println("Довжина шляху: " + pop.getFittest().getDistance());
        System.out.println("Час виконання: " + time + " мс");
        System.out.println("Знайдений шлях:");
        
        Tour t=new Tour();
        t=pop.getFittest();
        for(int i=0;i<nsity;i++){
        	   xline[i]= t.getCity(i).x;
        	   yline[i]= t.getCity(i).y;
        }
        
       for (int i = 0; i < nsity ; i++)
        {
           System.out.print("["+xline[i]+","+yline[i]+"],");
           if(i%10==9 ){
        	   System.out.println();
         }
        }
        // відображення графіка
        SwingUtilities.invokeLater(new Runnable() {
                    
         @Override
         public void run() {
          CartesianFrame frame = new CartesianFrame();
          frame.showUI();;
         }
        });
        
    }
}