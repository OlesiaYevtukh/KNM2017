package salesman_problem;

public class Population {

 
    Tour[] tours;

    // Створити популцію
    public Population(int populationSize, boolean initialise) {
        tours = new Tour[populationSize];
        if (initialise) {
           
            for (int i = 0; i < populationSize(); i++) {
                Tour newTour = new Tour();
                newTour.generateIndividual();
                saveTour(i, newTour);
            }
        }
    }
    

    public void saveTour(int index, Tour tour) {
        tours[index] = tour;
    }
    
   
    public Tour getTour(int index) {
        return tours[index];
    }

  
    public Tour getFittest() {
        Tour fittest = tours[0];

        for (int i = 1; i < populationSize(); i++) {
            if (fittest.getFitness() <= getTour(i).getFitness()) {
                fittest = getTour(i);
            }
        }
        return fittest;
    }

    public int populationSize() {
        return tours.length;
    }
}
