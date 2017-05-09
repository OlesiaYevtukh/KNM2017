import net.sourceforge.evoj.Individual;
import net.sourceforge.evoj.strategies.sorting.AbstractSimpleRating;

/**
 * Created by Віталік on 06.05.2017.
 */
public class Rating extends AbstractSimpleRating<Solution> {
    public static double calcFunction(Solution solution) {
        double x = solution.getX();
        double y = solution.getY();
        return 12 * x * x + 8 * x + 9 * y * y;
    }

    @Override
    public Double doCalcRating(Solution solution) {
        double fn = calcFunction(solution);
        if (Double.isNaN(fn)) {
            return null;
        } else {
            return -fn;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }
}
