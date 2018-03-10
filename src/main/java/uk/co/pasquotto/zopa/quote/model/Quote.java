package uk.co.pasquotto.zopa.quote.model;

import java.io.Serializable;
import java.util.Objects;

public class Quote implements Serializable {
    private int requestedAmount;
    private Double rate;
    private Double monthlyRepayment;
    private Double totalRepayment;
    private int term;

    public Quote(double monthlyRepayment, double totalRepayment, int term) {
        this.rate = 0D;
        this.monthlyRepayment = monthlyRepayment;
        this.totalRepayment = totalRepayment;
        this.term = term;
    }

    public Quote() {
        this.rate = 0D;
        this.monthlyRepayment = 0D;
        this.totalRepayment = 0D;
    }

    public int getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(int requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public void setMonthlyRepayment(Double monthlyRepayment) {
        this.monthlyRepayment = monthlyRepayment;
    }

    public Double getTotalRepayment() {
        return totalRepayment;
    }

    public void setTotalRepayment(Double totalRepayment) {
        this.totalRepayment = totalRepayment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return requestedAmount == quote.requestedAmount &&
                Objects.equals(rate, quote.rate) &&
                Objects.equals(monthlyRepayment, quote.monthlyRepayment) &&
                Objects.equals(totalRepayment, quote.totalRepayment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestedAmount, rate, monthlyRepayment, totalRepayment);
    }

    @Override
    public String toString() {
        return "Quote{" +
                "requestedAmount=" + requestedAmount +
                ", rate=" + rate +
                ", monthlyRepayment=" + monthlyRepayment +
                ", totalRepayment=" + totalRepayment +
                '}';
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getTerm() {
        return term;
    }
}
