package jenes.tutorials.problem3;
 
 import jenes.population.Fitness;
 import jenes.chromosome.DoubleChromosome;
 import jenes.population.Individual;

 public class EntropyFitness extends Fitness<DoubleChromosome> {

    public static EntropyFitness MAX = new EntropyFitness(true);
    public static EntropyFitness MIN = new EntropyFitness(false);

     private EntropyFitness(boolean maximize) {
        super(maximize);
    }

    @Override
     public void evaluate(Individual<DoubleChromosome> individual) {
         DoubleChromosome chrom = individual.getChromosome();
 
         int length = chrom.length();
 
        double sum = 0.0;
         for (int i = 0; i < length; ++i) {
             sum += chrom.getValue(i);
        }
 
        double entropy = 0.0;
        for (int i = 0; i < length; ++i) {
             double pxi = chrom.getValue(i) / sum;
             chrom.setValue(i, pxi);
 
             entropy -= (pxi * Math.log(pxi) / Math.log(2));
         }
 
        individual.setScore(entropy);
     }
 }