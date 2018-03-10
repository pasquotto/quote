package uk.co.pasquotto.zopa.quote.model;

public class LoanPart {
    private final double rate;
    private final int amount;
    private final int term;

    public LoanPart(double rate, int amount, int term) {
        this.rate = rate;
        this.amount = amount;
        this.term = term;
    }

    public double getRate() {
        return rate;
    }

    public int getAmount() {
        return amount;
    }

    public int getTerm() {
        return term;
    }
}
