package other;

import org.openjdk.jmh.annotations.Benchmark;

public class Main1 {

    @Benchmark
    public void measureName() {
        Calculate calculate = new Calculate();
        System.out.println(calculate.sin(10));
        System.out.println(calculate.cos(10));
        System.out.println(calculate.tg(10));
    }

    public static void main(String[] args) {
        Main1 main = new Main1();
        main.measureName();
    }
}
