package easydojo.calculator;


import easydojo.model.Performance;

public abstract class AbstractPerformanceCalculator {
    public static AbstractPerformanceCalculator of(String type) {
        if ("tragedy".equals(type)) {
            return new TragedyCalculator();
        }

        if ("comedy".equals(type)) {
            return new ComedyCalculator();
        }
        return null;
    }

    public abstract double getVolumeCredits(Performance performance);

    public abstract int getAmount(Performance performance);
}