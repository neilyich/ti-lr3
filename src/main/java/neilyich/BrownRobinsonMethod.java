package neilyich;

import java.util.List;
import java.util.stream.Stream;

public interface BrownRobinsonMethod {
    int xStrategiesCount();
    int yStrategiesCount();
    int xStrategy(int step);
    int yStrategy(int step);
    List<Double> xWin(int step);
    List<Double> yWin(int step);

    List<Double> xMixedStrategy();
    List<Double> yMixedStrategy();


    double minMaxCost(int step);
    double maxMinCost(int step);

    default double dominantXStrategy() {
        return dominantStrategy(xMixedStrategy());
    }

    default double dominantYStrategy() {
        return dominantStrategy(yMixedStrategy());
    }

    public double DOMINANT_STRATEGY_MIN_VALUE = 0.5;
    private double dominantStrategy(List<Double> mixedStrategy) {
        var mostPossibleStrategy = 0;
        var maxP = mixedStrategy.get(mostPossibleStrategy);
        if (maxP >= DOMINANT_STRATEGY_MIN_VALUE) {
            return strategyToDouble(mostPossibleStrategy, mixedStrategy.size());
        }
        for (int i = 1; i < mixedStrategy.size(); i++) {
            var p = mixedStrategy.get(i);
            if (p >= DOMINANT_STRATEGY_MIN_VALUE) {
                return strategyToDouble(i, mixedStrategy.size());
            } else if (p > maxP) {
                maxP = p;
                mostPossibleStrategy = i;
            }
        }
        throw new RuntimeException("no dominant strategy (" + maxP + ")");
    }

    private double strategyToDouble(int strategy, int strategiesCount) {
        return (double) strategy / (strategiesCount - 1);
    }


    default double xWin(int step, int xStrategy) {
        return xWin(step).get(xStrategy);
    }
    default double yWin(int step, int yStrategy) {
        return yWin(step).get(yStrategy);
    }
    default double maxCostEstimate(int step) {
        return xWin(step, xStrategy(step + 1));
    }
    default double minCostEstimate(int step) {
        return yWin(step, yStrategy(step + 1));
    }
    default double e(int step) {
        return minMaxCost(step) - maxMinCost(step);
    }
}
