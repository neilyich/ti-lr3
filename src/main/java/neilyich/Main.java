package neilyich;

import org.ejml.simple.SimpleMatrix;

public class Main {
    public static void main(String[] args) {
        var c = new SimpleMatrix(new double[][]{
                {1.0, 11.0, 11.0},
                {7.0, 5.0, 8.0},
                {16.0, 6.0, 2.0}
        });


        var exampleC = new SimpleMatrix(new double[][]{
                {2.0, 1.0, 3.0},
                {3.0, 0.0, 1.0},
                {1.0, 2.0, 1.0}
        });

        var h = new H(-4.0, 10.0/3, 16.0/3, -16.0/30, -112.0/30);

        var exampleH = new H(-3.0, 1.5, 18.0/5, -18.0/50, -72.0/25);
        //h = exampleH;

        int n = 10;
        int steps = 100;
        NumericalSolution.solve(h, n, steps);

        System.out.println("\n".repeat(5));

        AnalyticSolution.solve(h);
    }
}