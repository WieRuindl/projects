package other;

import org.openjdk.jmh.annotations.Benchmark;

public class Calculate {
//    @Benchmark
    public double sin(double arg) {
        return Math.sin(arg);
    }

//    @Benchmark
    public double cos(double arg) {
        return Math.cos(arg);
    }

//    @Benchmark
    public double tg(double arg) {
        return Math.sin(arg)/Math.cos(arg);
    }
}
