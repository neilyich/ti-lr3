package neilyich;

import org.ejml.simple.SimpleMatrix;

public class NumericalSolution {

    public static void solve(H h, int n, int steps) {
        var c = buildMatrix(h, n);
        c.print();
        var brm = new SimpleBrownRobinsonMethod(c);
        brm.iterate(steps);
        var printer = new BrownRobinsonMethodPrinter();
        printer.print(brm, steps);
        System.out.println("H = " + h.at(brm.dominantXStrategy(), brm.dominantYStrategy()));
    }

    private static SimpleMatrix buildMatrix(H h, int n) {
        var c = new SimpleMatrix(n + 1, n + 1);
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                c.set(i, j, h.at((double) i / n, (double) j / n));
            }
        }
        return c;
    }
}
