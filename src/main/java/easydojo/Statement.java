package easydojo;


import easydojo.calculator.AbstractPerformanceCalculator;
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
        stringBuilder.append(String.format("Amount owed is %s\n", formatUSD(invoice.getTotalAmount(plays))));
        stringBuilder.append(String.format("You earned %s credits\n", invoice.getTotalVolumeCredits(plays)));
        return stringBuilder.toString();
    }

    private StringBuilder formatPerformances() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            stringBuilder.append(String.format(" %s: %s (%d seats)\n", play.getName(), formatUSD(AbstractPerformanceCalculator.of(play.getType()).getAmount(performance)), performance.getAudience()));
        }
        return stringBuilder;
    }


    private String formatUSD(int amount) {
        return NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(amount / 100);
    }
}
