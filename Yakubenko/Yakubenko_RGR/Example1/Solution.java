import net.sourceforge.evoj.core.annotation.MutationRange;
import net.sourceforge.evoj.core.annotation.Range;

/**
 * Created by Віталік on 06.05.2017.
 */
public interface Solution {
    @Range(min = "-10", max = "10")
    @MutationRange("0.5")
    double getX();

    @Range(min = "-10", max = "10")
    @MutationRange("0.5")
    double getY();
}
