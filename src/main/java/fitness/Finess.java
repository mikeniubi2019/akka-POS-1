package fitness;

import java.util.List;

public class Finess {
    public static double finess(List<Double> x){
        return x.stream().filter(g->!g.isInfinite()).reduce(0d,(result,y)->result+=Math.sqrt(y));
    }
}
