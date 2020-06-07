package position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PosValue {
    final  double value;
    final List<Double> x;

    public PosValue(double value, List<Double> x) {
        this.value = value;
        List<Double> b = new ArrayList<Double>(5);
        b.addAll(x);
        this.x = Collections.unmodifiableList(b);
    }

    public double getValue() {
        return value;
    }

    public List<Double> getX() {
        return x;
    }

    @Override
    public String toString() {
        return "PosValue{" +
                "value=" + value +
                ", x=" + x +
                '}';
    }
}
