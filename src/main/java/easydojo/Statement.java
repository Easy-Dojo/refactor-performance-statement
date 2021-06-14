package easydojo;


import easydojo.calculator.ComedyCalculator;
import easydojo.calculator.IPerformanceCalculator;
import easydojo.calculator.TragedyCalculator;
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
            stringBuilder.append(String.format(" %s: %s (%d seats)\n", play.getName(), formatUSD(getPerformanceCalculator(play).getAmount(performance)), performance.getAudience()));
        }
        return stringBuilder;
    }

    private int getTotalVolumeCredits() {
        int volumeCredits = 0;
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            volumeCredits += getPerformanceCalculator(play).getVolumeCredits(performance);
        }
        return volumeCredits;
    }


    private int getTotalAmount() {
        int result = 0;
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            result += getPerformanceCalculator(play).getAmount(performance);
        }
        return result;
    }

    private IPerformanceCalculator getPerformanceCalculator(Play play) {
        if ("tragedy".equals(play.getType())) {
            return new TragedyCalculator();
        }
        if ("comedy".equals(play.getType())) {
            return new ComedyCalculator();
        }
        return null;
    }

    private String formatUSD(int amount) {
        return NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(amount / 100);
    }
}
