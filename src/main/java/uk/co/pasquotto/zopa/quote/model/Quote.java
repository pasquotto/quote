package uk.co.pasquotto.zopa.quote.model;

import java.util.Objects;

public class Quote {
    private int requestedAmount;
    private Double rate;
    private Double monthlyRepayment;
    private Double totalRepayment;

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
}
