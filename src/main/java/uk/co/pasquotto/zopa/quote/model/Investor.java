package uk.co.pasquotto.zopa.quote.model;

import java.io.Serializable;
import java.util.Objects;

public class Investor implements Serializable {
    private String name;
    private Double rate;
    private int amountAvailable;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getRate() {
        return rate;
    }

    public void setAmountAvailable(int amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public int getAmountAvailable() {
        return amountAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Investor investor = (Investor) o;
        return amountAvailable == investor.amountAvailable &&
                Objects.equals(name, investor.name) &&
                Objects.equals(rate, investor.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rate, amountAvailable);
    }

    @Override
    public String toString() {
        return "Investor{" +
                "name='" + name + '\'' +
                ", rate=" + rate +
                ", amountAvailable=" + amountAvailable +
                '}';
    }
}
