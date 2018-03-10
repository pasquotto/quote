package uk.co.pasquotto.zopa.quote.model;

import java.io.Serializable;
import java.util.Objects;

public class LoanPart implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanPart loanPart = (LoanPart) o;
        return Double.compare(loanPart.rate, rate) == 0 &&
                amount == loanPart.amount &&
                term == loanPart.term;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate, amount, term);
    }

    @Override
    public String toString() {
        return "LoanPart{" +
                "rate=" + rate +
                ", amount=" + amount +
                ", term=" + term +
                '}';
    }
}
