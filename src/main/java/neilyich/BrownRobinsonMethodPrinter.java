package neilyich;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class BrownRobinsonMethodPrinter {
    private BrownRobinsonMethod brm;
    private int rows;
    private int cellWidth;
    public static String DELIMITER = " |";

    public void print(BrownRobinsonMethod brm, int rows) {
        this.brm = brm;
        this.rows = rows;
        calcCellWidth();
        printHeader();
        for (int row = 0; row < rows; row++) {
            printRow(row);
        }
        nextLine();
        System.out.println("X = " + brm.dominantXStrategy());
        System.out.println("Y = " + brm.dominantYStrategy());
    }

    private void calcCellWidth() {
        var maxNumber = Stream.iterate(0, i -> i < rows, i -> i + 1)
                .flatMapToDouble(row ->
                        Stream.<Function<Integer, Double>>of(
                                r -> (double) brm.xStrategy(r),
                                r -> (double) brm.yStrategy(r),
                                brm::maxCostEstimate,
                                r -> brm.xWin(r).stream().mapToDouble(it -> it).max().orElse(0),
                                r -> brm.yWin(r).stream().mapToDouble(it -> it).max().orElse(0)
                        ).mapToDouble(f -> Math.abs(f.apply(row)))
                ).max().orElse(0);
        cellWidth = (int) Math.log10(maxNumber) + 7;
    }

    private void printHeader() {
        printRow(List.of(
                () -> print("i"),
                () -> print("xi"),
                () -> print("yi"),
                () -> printGroup("X", brm.xStrategiesCount()),
                () -> printGroup("Y", brm.yStrategiesCount()),
                () -> print("V"),
                () -> print("v"),
                () -> print("e", cellWidth + 3)
        ));
    }

    private void printRow(Iterable<Runnable> contentCreators) {
        for (var cellCreator : contentCreators) {
            cellCreator.run();
            sep();
        }
        nextLine();
    }

    private void printRow(int row) {
        printRow(List.of(
                () -> print(row + 1),
                () -> print(brm.xStrategy(row) + 1),
                () -> print(brm.yStrategy(row) + 1),
                () -> printGroup(brm.xWin(row)),
                () -> printGroup(brm.yWin(row)),
                () -> print(brm.maxCostEstimate(row)),
                () -> print(brm.minCostEstimate(row)),
                () -> print(brm.e(row))
        ));
    }

    private void print(int n) {
        print(String.valueOf(n));
    }

    private void print(double n) {
        print(BigDecimal.valueOf(n).setScale(2, RoundingMode.HALF_EVEN).toString(), cellWidth);
    }

    private void print(String cell) {
        print(cell, cellWidth);
    }

    private void printGroup(String content, int colsCount) {
        print(content, colsCount * cellWidth);
    }

    private void printGroup(List<Double> content) {
        for (var c : content) {
            print(c);
        }
    }

    private void print(String content, int width) {
        System.out.print(" ".repeat(width - content.length()));
        System.out.print(content);
    }

    private void sep() {
        System.out.print(DELIMITER);
    }

    private void nextLine() {
        System.out.println();
    }
}
