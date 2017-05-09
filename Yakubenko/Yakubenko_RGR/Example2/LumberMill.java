package net.sourceforge.evoj.examples.binpacking;

import net.sourceforge.evoj.Individual;
import net.sourceforge.evoj.BirthStrategy;
import net.sourceforge.evoj.MutationStrategy;
import net.sourceforge.evoj.RatingStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Pavel Tsvetkov
 */
public class LumberMill implements BirthStrategy, MutationStrategy,
        RatingStrategy {

    int[] logs;
    int[] details;
    //This class will be used in multithreaded environment, so we must have
    //separate random number generator for every thread
    ThreadLocal tl2 = new ThreadLocal() {

        @Override
        protected Object initialValue() {
            return new int[logs.length];
        }
    };
    protected ThreadLocal<Random> tl = new ThreadLocal<Random>() {

        @Override
        protected Random initialValue() {
            return new Random();
        }
    };

    public LumberMill(int[] logs, int[] details) {
        this.logs = Arrays.copyOf(logs, logs.length);
        this.details = Arrays.copyOf(details, details.length);
    }

    /**
     * For curent task this method is better than standard, since it avoids
     * obviously wrong solutions
     * This method randomly maps details to logs
     * @return new Individual
     */
    public Individual getNewIndividual() {
        Individual result = new Individual(details.length * 2);
        for (int i = 0; i < details.length; i++) {
            result.setShort(i << 1, (short) tl.get().nextInt(logs.length));
        }
        return result;
    }

    /**
     * Mutates solution by flipping random details from one log to another
     * @param i
     */
    public void mutate(Individual i) {
        Random rnd = tl.get();
        if (rnd.nextDouble() < 0.4) {//mutate 40% of Individuals
            for (int k = 0; k < details.length; k++) {
                if (rnd.nextDouble() < 0.2) {//Mutate 20% of DNA
                    i.setShort(k << 1, (short) rnd.nextInt(logs.length));
                }
            }
        }
    }

    public void calcRating(Individual[] pool, int eliteCount) {
        for (int i = eliteCount; i < pool.length; i++) {
            setRating(pool[i]);
        }
        //we eliminate equal solutions to maintain dispersion
        eliminateTwins(pool, eliteCount);
    }

    private void setRating(Individual ind) {
        int[] tmp = (int[]) tl2.get();
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = 0;
        }

        for (int i = 0; i < details.length; i++) {
            //Determine to which log i-th detail belongs
            short lg = ind.getShort(i << 1);
            if ((lg >= logs.length) || (lg < 0)) {
                //eliminate obviously wrong solution
                ind.setRating(null);
                return;
            }
            //accumulate length of details belonging to lg-th log
            tmp[lg] += details[i];
        }
        double positive = 0;//positive accumulator
        double negative = 0;//negative accumulator
        for (int i = 0; i < tmp.length; i++) {
            int dl = logs[i] - tmp[i];//calculate difference
            if (dl < 0) {
                negative += dl * dl;
            } else {
                positive += dl * dl;
            }
        }
        if (negative > 0) {
            ind.setRating(-negative);
        } else {
            ind.setRating(positive);
        }
    }

    public String asString(Individual ind) {
        int[] tmp = (int[]) tl2.get();
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = 0;
        }
        ArrayList<Integer>[] logDet = new ArrayList[logs.length];
        for (int i = 0; i < logs.length; i++) {
            logDet[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < details.length; i++) {
            //Determine to which log i-th detail belongs
            short lg = ind.getShort(i << 1);
            //accumulate length of details belonging to lg-th log
            tmp[lg] += details[i];
            logDet[lg].add(details[i]);
        }
        StringBuilder sb = new StringBuilder();



        for (int i = 0; i < logs.length; i++) {
            sb.append("Log " + i + " length:" + logs[i]);
            sb.append(" details length:");
            sb.append(tmp[i] + " ");
            sb.append(logDet[i].toString() + "\n");
        }

        return sb.toString();
    }

    @Override
    public int getDNASize() {
        return details.length * 2;
    }

    public void eliminateTwins(Individual[] pool, int eliteCount) {
        for (int i = 0; i < eliteCount - 1; i++) {
            if (pool[i].compareTo(pool[i + 1])==0) {
                if (pool[i].equals(pool[i + 1])) {
                    //eliminate solutions with equal genes
                    pool[i].setRating(null);
                }
            }
        }
    }
}
