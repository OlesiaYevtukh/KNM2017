import net.sourceforge.evoj.GenePool;
import net.sourceforge.evoj.core.DefaultPoolFactory;
import net.sourceforge.evoj.handlers.DefaultHandler;
import net.sourceforge.evoj.handlers.MultithreadedHandler;
import net.sourceforge.evoj.strategies.mutation.DefaultMutationStrategy;
import net.sourceforge.evoj.strategies.mutation.SimpleBitMutation;

import javax.swing.*;


public class main extends JFrame {
    public static void main(String[] args) {

        DefaultPoolFactory pf = new DefaultPoolFactory();
        GenePool<Solution> pool = pf.createPool(320, Solution.class, null);
        DefaultMutationStrategy m = new DefaultMutationStrategy(0.3, 0.4, 0.5);
        DefaultHandler handler = new DefaultHandler(new Rating(), m, null, null);

        handler.iterate(pool, 200);
        Solution solution = pool.getBestSolution();
        pool.getBestIndividual().getRating();
        System.out.println("X =" + solution.getX());
        System.out.println("Y =" + solution.getY());
        double val = Rating.calcFunction(pool.getBestSolution());
        System.out.println("Detected minimum value: " + val);

    }
}
