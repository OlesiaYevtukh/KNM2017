import java. util .Comparator;
import net.sf . jclec .IFitness;
import net.sf . jclec .IIndividual ;
import net.sf . jclec .base.AbstractEvaluator;
import net.sf . jclec .exprtree.ExprTree;

import net.sf . jclec .exprtree.ExprTreeIndividual;
import net.sf . jclec .exprtree.fun.ExprTreeFunction;
import net.sf . jclec . fitness .SimpleValueFitness;
import net.sf . jclec . fitness .ValueFitnessComparator;

public class SymregEvaluator extends AbstractEvaluator
{
	private static final long serialVersionUID = 1L;
	
	 private static final Comparator<IFitness> COMPARATOR = new ValueFitnessComparator(true);
	
	private static final double [] xvalues = {-2., -1., 0., 1., 2.};
	
	private static final double [] yvalues = {10., 0., 0., 4., 30.};
	
	@Override
	protected void evaluate(IIndividual ind)
	{
		ExprTree genotype = ((ExprTreeIndividual) ind).getGenotype();
		
		ExprTreeFunction function = new ExprTreeFunction(genotype);
		
		double [] y = new double[xvalues.length];
		
		for(int i = 0; i<xvalues.length; i++)
			y[i ] = function.<Double>execute(xvalues[i]);
		
		double rms = 0.0;
		
		for (int i=0; i<yvalues.length; i++) 
		{
			double diff = y[i] - yvalues[i ];
			rms += diff*diff ;
		}
		
			rms = Math.sqrt(rms);

			ind.setFitness(new SimpleValueFitness(rms));
		}
		
		@Override
		public Comparator<IFitness> getComparator() 
		{
			return COMPARATOR;
		}
}
