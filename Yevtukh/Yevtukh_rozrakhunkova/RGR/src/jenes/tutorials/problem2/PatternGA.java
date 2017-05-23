package jenes.tutorials.problem2;

import jenes.population.Fitness;
import jenes.GeneticAlgorithm;
import jenes.chromosome.IntegerChromosome;
import jenes.population.Individual;
import jenes.population.Population;

 public class PatternGA extends GeneticAlgorithm<IntegerChromosome> {

    private PatternFitness fitness = new PatternFitness();
 
    public PatternGA(Population<IntegerChromosome> pop, int numGen) {
       super(pop, numGen);
       this.setFitness(fitness);
    }
    
  @Override
    protected boolean end() {
        jenes.population.Population.Statistics stat = this.getCurrentPopulation().getStatistics();
        return stat.getGroup(Population.LEGALS).getMin()[0] <= this.fitness.precision;
    }
    
    public class PatternFitness extends Fitness<IntegerChromosome> {
 
         private int[] target = null;
        private int precision = 0;
         
        private PatternFitness() {
           super(false);
        }
   
        @Override
       public void evaluate(Individual<IntegerChromosome> individual) {
         IntegerChromosome chrom = individual.getChromosome();
            int diff = 0;
            int length = chrom.length();
             for (int i = 0; i < length; i++) {
                diff += Math.abs(chrom.getValue(i) - target[i]);
           }
      individual.setScore(diff);
        }
        
         public void setTarget(int[] target) {
           this.target = target;
         }
        
         public void setPrecision(int precision) {
             this.precision = precision;
         }
    }
}