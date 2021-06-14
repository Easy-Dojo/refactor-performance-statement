package easydojo.model;

import easydojo.calculator.AbstractPerformanceCalculator;

import java.util.List;
import java.util.Map;

public class Invoice {

    private String customer;

    private List<Performance> performances;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public int getTotalVolumeCredits(Map<String, Play> plays) {
        int volumeCredits = 0;
        for (Performance performance : getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            volumeCredits += AbstractPerformanceCalculator.of(play.getType()).getVolumeCredits(performance);
        }
        return volumeCredits;
    }

    public int getTotalAmount(Map<String, Play> plays) {
        int result = 0;
        for (Performance performance : getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            result += AbstractPerformanceCalculator.of(play.getType()).getAmount(performance);
        }
        return result;
    }
}
