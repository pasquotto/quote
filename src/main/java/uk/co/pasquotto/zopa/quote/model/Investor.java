package uk.co.pasquotto.zopa.quote.model;

public class Investor {
    private String name;
    private double rate;
    private int amountAvailable;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }

    public void setAmountAvailable(int amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public int getAmountAvailable() {
        return amountAvailable;
    }
}
