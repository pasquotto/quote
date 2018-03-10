package uk.co.pasquotto.zopa.quote.service;

public class FinancialCalculationUtils {
    private static final double FINANCIAL_PRECISION = 0.00000001; //1.0e-08
    private static final double FINANCIAL_MAX_ITERATIONS = 128;
    /**
     * Code copied from https://github.com/ndongo/Excel-Financial-functions/blob/master/financialfunctions/src/fr/ndongo/financialfunctions/model/Simulation.java
     * @param npr  number of periods in months
     * @param pmt  payment
     * @param pv    present value
     * @return the monthly rate
     */
    public static double rate(int npr, double pmt, double pv){
        double rate = 0.1;
        double y;
        double f = 0.0;
        if (Math.abs(rate) < FINANCIAL_PRECISION) {
            y = pv * (1 + npr * rate) + pmt * npr;
        } else {
            f = Math.exp(npr * Math.log(1 + rate));
            y = pv * f + pmt * (1 / rate) * (f - 1);
        }

        double y0 = pv + pmt * npr;
        double y1 = pv * f + pmt * (1 / rate) * (f - 1);

        // find root by secant method
        int i = 0;
        double x0 = 0.0;
        double x1 = rate;
        while ((Math.abs(y0 - y1) > FINANCIAL_PRECISION) && (i < FINANCIAL_MAX_ITERATIONS)) {
            rate = (y1 * x0 - y0 * x1) / (y1 - y0);
            x0 = x1;
            x1 = rate;

            if (Math.abs(rate) < FINANCIAL_PRECISION) {
                y = pv * (1 + npr * rate) + pmt * npr;
            } else {
                f = Math.exp(npr * Math.log(1 + rate));
                y = pv * f + pmt * (1 / rate) * (f - 1);
            }

            y0 = y1;
            y1 = y;
            i++;
        }
        return rate;
    }
    public static double calculateMonthlyRepayments(int loanAmount, double monthlyRate, int term) {
        double monthlyPayment =
                (loanAmount*monthlyRate) /
                        (1-Math.pow(1+monthlyRate, -term));
        return monthlyPayment;
    }
}
