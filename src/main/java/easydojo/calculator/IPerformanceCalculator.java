package easydojo.calculator;


import easydojo.model.Performance;

public interface IPerformanceCalculator {
    double getVolumeCredits(Performance performance);

    int getAmount(Performance performance);
}
