package net.sourceforge.evoj.examples.binpacking;

import net.sourceforge.evoj.GenePool;
import net.sourceforge.evoj.Individual;
import net.sourceforge.evoj.multithreading.IntermixType;
import net.sourceforge.evoj.multithreading.MultiThreadedGenePool;
import net.sourceforge.evoj.mutation.FlipMutation;

/**
 * This example shows possible approach to bin packing promlem.
 * Let's consider problem of a lumber mill. Lumber mill must produce some
 * details for house building using available logs. The objective is to
 * spread details by logs, to use minimum number of logs, and to maximize
 * the sizes of remainders (in other words it's better to have two logs
 * fully used and one log with three meters remainder than three logs
 * with one meter long remainder each)
 */
public class Main {

    public static void main(String[] args) {

        int[] logs = {10, 9, 12, 12, 14, 12, 12, 9, 9, 11, 10, 12, 12, 9, 9,
            12, 12, 12, 12, 12, 12, 12, 12};//lengths of available logs
        int[] details = {3, 3, 4, 5, 3, 7, 7, 5, 4, 4, 4, 8, 8, 5, 5, 5, 5,
            3, 3, 8, 8, 8, 8, 9, 5, 4, 5, 5, 6};//lengths of required details

        //The DNAs of solutions will be Integers, and will represent index in
        //array of logs to which this detail belong

        LumberMill lm = new LumberMill(logs, details);

        //We will override Birth and Mutation strategies, and provide
        //RatingStrategy. All theese strateries are implemented by
        //LumberMill class.
        //Notice that these pools have different Mutation strategies
        GenePool gp1 = new GenePool(400, 0, lm, null, null, lm, lm);
        GenePool gp2 = new GenePool(300, 0, lm, null, null, lm, lm);
        GenePool gp3 = new GenePool(200, 0, lm, null, null,
                new FlipMutation(0.3, lm), lm);
        GenePool gp4 = new GenePool(100, 0, lm, null, null,
                new FlipMutation(0.8, lm), lm);
        gp3.setEliteCount(15);

        GenePool[] pools = {gp1, gp2, gp3, gp4};

        MultiThreadedGenePool mt = new MultiThreadedGenePool(pools);

        //iterate given pools. printing results every 100 iterations
        for (int i = 1; i <= 50; i++) {
            mt.iterate(200);

            if (i % 10 == 0) {
                GenePool minPool = gp1;
                Individual min = gp1.getChampion();
                for (GenePool p : pools) {
                    if (p.getChampion().compareTo(min)<0) {
                        minPool = p;
                        min = p.getChampion();
                    }
                }
                //intermix elitary Individuals between pools
                mt.intermix(IntermixType.ELITE);
                //reset worst pool, hoping for better results next time
                minPool.reset();
            }

            //print out current results
            System.out.print(lm.asString(mt.getChampion()));
            for (GenePool gp : pools) {
                System.out.print("Rating:" + (Double)gp.getChampion().getRating());
                System.out.println(" Age:" + gp.getChampion().getAge());
            }
        }
        //release thread objects. after this line object is unusable
        mt.dispose();
    }
}
