package salesman_problem;

public class City {
    int x;
    int y;
    
     // Створює місто в обраних х, у координатах
    public City(int x, int y){
        this.x = x;
        this.y = y;
    }
    
   
    public int getX(){
        return this.x;
    }
    

    public int getY(){
        return this.y;
    }
    
    // Отримує відстань до даного міста
    public double distanceTo(City city){
        int xDistance = Math.abs(getX() - city.getX());
        int yDistance = Math.abs(getY() - city.getY());
        double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );
        
        return distance;
    }
    
    
    public String toString(){
        return getX()+", "+getY();
    }
}