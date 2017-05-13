package problems;

import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.impl.DefaultBinarySolution;
import org.uma.jmetal.util.JMetalException;

import solution.MyBinarySolution;

import java.util.BitSet;

@SuppressWarnings("serial")
public class MyOneMax extends AbstractBinaryProblem{
	private int bits ;
	
	  /** Constructor */
	  public MyOneMax() {
	    this(256);
	  }

	  /** Constructor */
	  public MyOneMax(Integer numberOfBits) {
	    setNumberOfVariables(1);
	    setNumberOfObjectives(1);
	    setName("MyOneMax");

	    bits = numberOfBits ;
	  }

	  @Override
	  protected int getBitsPerVariable(int index) {
	  	if (index != 0) {
	  		throw new JMetalException("Problem MyOneMax has only a variable. Index = " + index) ;
	  	}
	  	return bits ;
	  }
	   
	  @Override
	  public BinarySolution createSolution() {
	    return new MyBinarySolution(this) ;
	  }

	  /** Evaluate() method */
	  public void evaluate(BinarySolution solution) {
		  
	    int counterOnes;

	    counterOnes = 0;

	    BitSet bitset = solution.getVariableValue(0) ;
	    for (int i = 0; i < bitset.length(); i++) {
	      if (bitset.get(i)) {
	        counterOnes++;
	      }
	    }
	    solution.setObjective(0, -1.0 * counterOnes);
	  }
}
