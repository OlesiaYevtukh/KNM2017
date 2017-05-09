package net.sourceforge.evoj.examples.binpacking;

import net.sourceforge.evoj.GenePool;
import net.sourceforge.evoj.mutation.FlipMutation;
import net.sourceforge.evoj.mutation.SimpleBitMutation;

/**
 *
 * @author Pavel
 */
public class MainProfiling {

    public static void main(String[] args){
        int[] logs = {10, 9, 12, 12, 14, 12, 12, 9, 9, 11, 10, 12, 12, 9, 9, 12, 12, 12, 12, 12, 12, 12, 12};//lengths of available logs
        int[] details = {3, 3, 4, 5, 3, 7, 7, 5, 4, 4, 4, 8, 8, 5, 5, 5, 5, 3, 3, 8, 8, 8, 8, 9, 5, 4, 5, 5, 6};// lengths of required details

        //The DNAs of solutions will be Integers, and will represent index in
        //array of logs to which this detail belong

        LumberMill lm = new LumberMill(logs, details);

        //We will override Birth and Mutation strategies, and provide Rating Strategy
        //All theese strateries are implemented by LumberMill class

        GenePool gp1 = new GenePool(500, 0, lm, null, null, lm, lm);
        GenePool gp2 = new GenePool(500, 0, lm, null, null, new SimpleBitMutation(0.3, 0.5), lm);//this pool uses other strategy for mutation
        GenePool gp3 = new GenePool(500, 0, lm, null, null, new FlipMutation(0.1, lm), lm);
        gp3.setEliteCount(250);

        gp1.iterate(1000);
    }

}
