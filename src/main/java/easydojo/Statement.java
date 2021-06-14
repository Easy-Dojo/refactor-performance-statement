package easydojo;


import easydojo.model.Invoice;
import easydojo.model.Performance;
import easydojo.model.Play;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class Statement {

    private Invoice invoice;
    private Map<String, Play> plays;

    public Statement(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    public String show() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Statement for %s", invoice.getCustomer()));
        stringBuilder.append(formatPerformances());
        stringBuilder.append(String.format("Amount owed is %s\n", formatUSD(getTotalAmount())));
        stringBuilder.append(String.format("You earned %s credits\n", getTotalVolumeCredits()));
        return stringBuilder.toString();
    }

    private StringBuilder formatPerformances() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            stringBuilder.append(String.format(" %s: %s (%d seats)\n", play.getName(), formatUSD(getThisAmount(performance, play)), performance.getAudience()));
        }
        return stringBuilder;
    }

    private int getTotalVolumeCredits() {
        int volumeCredits = 0;
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            volumeCredits += getThisVolumeCredits(performance, play);
        }
        return volumeCredits;
    }

    private double getThisVolumeCredits(Performance performance, Play play) {
        double VolumeCredits = 0;
        switch (play.getType()) {
            case "tragedy":
                VolumeCredits = getTragedyVolumeCredits(performance);
                break;
            case "comedy":
                VolumeCredits = getComedyVolumeCredits(performance);
                break;
            default:
                return VolumeCredits;
        }
        return VolumeCredits;
    }

    private int getTotalAmount() {
        int result = 0;
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            result += getThisAmount(performance, play);
        }
        return result;
    }

    private int getThisAmount(Performance performance, Play play) {
        int thisAmount;
        switch (play.getType()) {
            case "tragedy":
                thisAmount = getTragedyAmount(performance);
                break;
            case "comedy":
                thisAmount = getComedyAmount(performance);
                break;
            default:
                throw new RuntimeException("unknown type:" + play.getType());
        }
        return thisAmount;
    }


    private double getComedyVolumeCredits(Performance performance) {
        return Math.max(performance.getAudience() - 30, 0) + Math.floor(performance.getAudience() / 5);
    }

    private int getTragedyVolumeCredits(Performance performance) {
        return Math.max(performance.getAudience() - 30, 0);
    }

    private String formatUSD(int amount) {
        return NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(amount / 100);
    }

    private int getComedyAmount(Performance performance) {
        int thisAmount;
        thisAmount = 30000;
        if (performance.getAudience() > 20) {
            thisAmount += 10000 + 500 *(performance.getAudience() - 20);
        }
        thisAmount += 300 * performance.getAudience();
        return thisAmount;
    }

    private int getTragedyAmount(Performance performance) {
        int thisAmount;
        thisAmount = 40000;
        if (performance.getAudience() > 30) {
            thisAmount += 1000 * (performance.getAudience() - 30);
        }
        return thisAmount;
    }
}
