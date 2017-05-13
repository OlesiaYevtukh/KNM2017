package solution;

import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.impl.AbstractGenericSolution;
import org.uma.jmetal.util.binarySet.BinarySet;

import java.util.HashMap;

public class MyBinarySolution extends AbstractGenericSolution<BinarySet, BinaryProblem> implements BinarySolution {

	private static final long serialVersionUID = 1L;

	/** Constructor */
	  public MyBinarySolution(BinaryProblem problem) {
	    super(problem) ;

	    initializeBinaryVariables();
	    initializeObjectiveValues();
	  }

	/** Copy constructor */
	  public MyBinarySolution(MyBinarySolution solution) {
	    super(solution.problem);

	    for (int i = 0; i < problem.getNumberOfVariables(); i++) {
	      setVariableValue(i, (BinarySet) solution.getVariableValue(i).clone());
	    }

	    for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
	      setObjective(i, solution.getObjective(i)) ;
	    }

	    attributes = new HashMap<Object, Object>(solution.attributes) ;
	  }

	private BinarySet createNewBitSet(int numberOfBits) {
		BinarySet bitSet = new BinarySet(numberOfBits);

		for (int i = 0; i < numberOfBits; i++) {
			double rnd = randomGenerator.nextDouble();
			if (rnd < 0.5) {
				bitSet.set(i);
			} else {
				bitSet.clear(i);
			}
		}
		return bitSet;
	}

	public int getNumberOfBits(int index) {
		return getVariableValue(index).getBinarySetLength();
	}

	public MyBinarySolution copy() {
		return new MyBinarySolution(this);
	}

	public int getTotalNumberOfBits() {
		int sum = 0;
		for (int i = 0; i < getNumberOfVariables(); i++) {
			sum += getVariableValue(i).getBinarySetLength();
		}
		return sum;
	}

	public String getVariableValueString(int index) {
		String result = "";
		for (int i = 0; i < getVariableValue(index).getBinarySetLength(); i++) {
			if (getVariableValue(index).get(i)) {
				result += "1";
			} else {
				result += "0";
			}
		}
		return result;
	}

	private void initializeBinaryVariables() {
		for (int i = 0; i < problem.getNumberOfVariables(); i++) {
			setVariableValue(i, createNewBitSet(problem.getNumberOfBits(i)));
		}
	}

	/* Значення функції */
	public double getObjective(int i) {
		int n = 0;
		String str = "";
		for (int j = 0; j < getVariableValue(i).getBinarySetLength(); j++) {
			if (getVariableValue(i).get(j)) {
				n++;
				str+="1";
			} else {
				str+="0";
			}
		}
		int val = Integer.parseInt(str, 2);
		if (n == 0) n=1;
		return -val/n;
	}
	
}
