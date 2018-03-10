package uk.co.pasquotto.zopa.quote.writer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.pasquotto.zopa.quote.model.Quote;
import uk.co.pasquotto.zopa.quote.service.exception.QuotationException;
import uk.co.pasquotto.zopa.quote.writer.Output;
import uk.co.pasquotto.zopa.quote.writer.QuoteWriter;

import java.text.NumberFormat;
import java.util.Locale;

@Component
public class QuoteWriterImpl implements QuoteWriter {
    @Autowired
    private Output output;
    @Override
    public void write(Quote quote) {
        validate(quote);

        NumberFormat loanAmountCurrencyFormatter = createNumberFormatForLoanAmount();
        NumberFormat currencyFormatter = createNumberFormatForCurrency();
        NumberFormat percentFormatter = createNumberFormateForRate();

        output.print("Requested amount: " +  loanAmountCurrencyFormatter.format(quote.getRequestedAmount()) +  "\n");
        output.print("Rate: " + percentFormatter.format(quote.getRate()) +"\n");
        output.print("Monthly repayment: " + currencyFormatter.format(quote.getMonthlyRepayment()) + "\n");
        output.print("Total repayment: " + currencyFormatter.format(quote.getTotalRepayment()) + "\n");
    }

    private NumberFormat createNumberFormateForRate() {
        NumberFormat percentFormatter = NumberFormat.getPercentInstance(Locale.UK);
        percentFormatter.setMinimumFractionDigits(1);
        percentFormatter.setMaximumFractionDigits(1);
        return percentFormatter;
    }

    private NumberFormat createNumberFormatForCurrency() {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.UK);
        currencyFormatter.setGroupingUsed(false);
        return currencyFormatter;
    }

    private NumberFormat createNumberFormatForLoanAmount() {
        NumberFormat loanAmountCurrencyFormatter = createNumberFormatForCurrency();
        loanAmountCurrencyFormatter.setMaximumFractionDigits(0);
        return loanAmountCurrencyFormatter;
    }

    private void validate(Quote quote) {
        if (quote == null) throw new QuotationException("Quote cannot be null");
        if (quote.getRate() == null) throw new QuotationException("Quote cannot be null");
        if (quote.getMonthlyRepayment() == null) throw new QuotationException("Monthly repayment cannot be null");
        if (quote.getTotalRepayment() == null) throw new QuotationException("Total repayment cannot be null");
    }
}
