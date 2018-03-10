package uk.co.pasquotto.zopa.quote.writer.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.pasquotto.zopa.quote.model.Quote;
import uk.co.pasquotto.zopa.quote.service.exception.QuotationException;
import uk.co.pasquotto.zopa.quote.writer.Output;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QuoteWriterImplTest {

    @Mock
    private Output output;
    @InjectMocks
    private QuoteWriterImpl underTest;

    @Test
    public void write() {
        Quote quote = new Quote();
        quote.setRequestedAmount(2000);
        quote.setRate(0.0670096906573481D);
        quote.setMonthlyRepayment(61.4811179855167D);
        quote.setTotalRepayment(2213.3202474786D);

        underTest.write(quote);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(output, times(4)).print(captor.capture());

        List<String> allValues = captor.getAllValues();
        assertEquals("Requested amount: £2000\n", allValues.get(0));
        assertEquals("Rate: 6.7%\n", allValues.get(1));
        assertEquals("Monthly repayment: £61.48\n", allValues.get(2));
        assertEquals("Total repayment: £2213.32\n", allValues.get(3));

    }

    @Test(expected = QuotationException.class)
    public void writeQuoteNull() {
        underTest.write(null);
    }

    @Test(expected = QuotationException.class)
    public void writeRateNull() {
        Quote quote = new Quote();
        quote.setRequestedAmount(2000);
        quote.setRate(null);
        quote.setMonthlyRepayment(61.4811179855167D);
        quote.setTotalRepayment(2213.3202474786D);
        underTest.write(quote);
    }
    @Test(expected = QuotationException.class)
    public void writeMonthlyPaymentNull() {
        Quote quote = new Quote();
        quote.setRequestedAmount(2000);
        quote.setRate(0.0670096906573481D);
        quote.setMonthlyRepayment(null);
        quote.setTotalRepayment(2213.3202474786D);
        underTest.write(quote);
    }

    @Test(expected = QuotationException.class)
    public void writeTotalPaymentNull() {
        Quote quote = new Quote();
        quote.setRequestedAmount(2000);
        quote.setRate(0.0670096906573481D);
        quote.setMonthlyRepayment(61.4811179855167D);
        quote.setTotalRepayment(null);
        underTest.write(quote);
    }
}