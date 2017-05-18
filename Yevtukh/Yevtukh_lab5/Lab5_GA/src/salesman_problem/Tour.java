package salesman_problem;

import java.util.ArrayList;
import java.util.Collections;

public class Tour{

    private ArrayList tour = new ArrayList<City>();
    private double fitness = 0;
    private int distance = 0;
    
 
    public Tour(){
        for (int i = 0; i < TourManager.numberOfCities(); i++) {
            tour.add(null);
        }
    }
    
    public Tour(ArrayList tour){
        this.tour = tour;
    }

 
    public void generateIndividual() {
        
    	        for (int cityIndex = 0; cityIndex < TourManager.numberOfCities(); cityIndex++) {
          setCity(cityIndex, TourManager.getCity(cityIndex));
        }
        
        Collections.shuffle(tour);
    }


    public City getCity(int tourPosition) {
        return (City)tour.get(tourPosition);
    }

    
    public void setCity(int tourPosition, City city) {
        tour.set(tourPosition, city);
        fitness = 0;
        distance = 0;
    }
    
   
 
    public double getFitness() {
        if (fitness == 0) {
            fitness = 1/(double)getDistance();
        }
        return fitness;
    }
    
    
  
    public int getDistance(){
        if (distance == 0) {
            int tourDistance = 0;
            
            //Перебір міст проходу
            for (int cityIndex=0; cityIndex < tourSize(); cityIndex++) {
                          	
                City fromCity = getCity(cityIndex);
                
                City destinationCity;
                            
                if(cityIndex+1 < tourSize()){
                    destinationCity = getCity(cityIndex+1);
                }
                else{
                    destinationCity = getCity(0);
                }
                
                //Отримання дистаннції між двома містами
                tourDistance += fromCity.distanceTo(destinationCity);
            }
            distance = tourDistance;
        }
        return distance;
    }

       public int tourSize() {
        return tour.size();
    }
    
   
    public boolean containsCity(City city){
        return tour.contains(city);
    }
    
    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < tourSize(); i++) {
            geneString += getCity(i)+"|";
        }
        return geneString;
    }
}
