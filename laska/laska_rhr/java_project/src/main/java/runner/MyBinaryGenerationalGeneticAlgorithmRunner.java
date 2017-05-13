package runner;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.problem.multiobjective.OneZeroMax;
import org.uma.jmetal.problem.singleobjective.OneMax;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import problems.MyOneMax;

import java.util.ArrayList;
import java.util.List;

public class MyBinaryGenerationalGeneticAlgorithmRunner {

	public static void main(String[] args) throws Exception {
		Algorithm<BinarySolution> algorithm;
			
		/* Задача оптимізації */
		BinaryProblem problem = new MyOneMax(15);
		
		/* Параметри генетичного алгоритму */
		CrossoverOperator<BinarySolution> crossoverOperator = new SinglePointCrossover(0.9);
		MutationOperator<BinarySolution> mutationOperator = new BitFlipMutation(1.0 / problem.getNumberOfBits(0));
		SelectionOperator<List<BinarySolution>, BinarySolution> selectionOperator = new BinaryTournamentSelection<BinarySolution>();

		algorithm = new GeneticAlgorithmBuilder<BinarySolution>(problem, crossoverOperator, mutationOperator)
				.setPopulationSize(6).setMaxEvaluations(50).setSelectionOperator(selectionOperator).build();
		
		/* Запускаємо алгоритм */
		AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

		BinarySolution solution = algorithm.getResult();
		List<BinarySolution> population = new ArrayList<BinarySolution>(1);
		population.add(solution);

		long computingTime = algorithmRunner.getComputingTime();

		/* Запис результату у зовнішній файл */
		new SolutionListOutput(population).setSeparator("\n")
				.setVarFileOutputContext(new DefaultFileOutputContext("/home/laska/KNM2017/laska/laska_rhr/VAR.tsv"))
				.setFunFileOutputContext(new DefaultFileOutputContext("/home/laska/KNM2017/laska/laska_rhr/FUN.tsv")).print();
System.out.println(solution.getObjective(0));
		/* Вивід загальної інформації про виконання алгоритму */
		JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
		JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
		JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");		
	}

}
