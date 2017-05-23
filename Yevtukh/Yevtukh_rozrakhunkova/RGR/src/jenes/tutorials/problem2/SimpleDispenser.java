package jenes.tutorials.problem2;


 import jenes.chromosome.Chromosome;
 import jenes.population.Individual;
import jenes.population.Population;
 import jenes.stage.ExclusiveDispenser;
 
public class SimpleDispenser<T extends Chromosome> extends ExclusiveDispenser<T> {
    
   private int count;
     
    public SimpleDispenser(int span) {
         super(span);
   }
   
    public void preDistribute(Population<T> population){
        this.count = 0;
    }
    
    @Override
     public int distribute(Individual<T> ind) {
         return count++ % span;
     }
     
}
