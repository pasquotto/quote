package uk.co.pasquotto.zopa.quote;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.pasquotto.zopa.quote.model.Investor;
import uk.co.pasquotto.zopa.quote.model.Quote;
import uk.co.pasquotto.zopa.quote.reader.CSVFileReader;
import uk.co.pasquotto.zopa.quote.service.QuoteService;
import uk.co.pasquotto.zopa.quote.service.exception.MarketFileNotFoundException;
import uk.co.pasquotto.zopa.quote.service.exception.QuotationException;
import uk.co.pasquotto.zopa.quote.service.impl.QuoteServiceImpl;
import uk.co.pasquotto.zopa.quote.writer.QuoteWriter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuoteServiceTest {

    @Mock
    private CSVFileReader fileReader;

    @Mock
    private QuoteWriter quoteWriter;
    @InjectMocks
    private QuoteServiceImpl quote;

    @Test
    public void testQuoteOneInvestor() {
        int loanAmount = 1000;
        String filePath = "filePath";

        List<Investor> investors = new ArrayList<>();
        investors.add(createInvestor("investorName", 0.075, 1300));
        when(fileReader.read(filePath)).thenReturn(investors);

        quote.quote(filePath, loanAmount);

        ArgumentCaptor<Quote> arg = ArgumentCaptor.forClass(Quote.class);
        verify(quoteWriter).write(arg.capture());
        Quote generatedQuote = arg.getValue();
        assertEquals(loanAmount, generatedQuote.getRequestedAmount());
        assertEquals(0.075D, generatedQuote.getRate(), 0.001D);
        assertEquals(31.11D, generatedQuote.getMonthlyRepayment(), 0.001D);
        assertEquals(1119.82D, generatedQuote.getTotalRepayment(), 0.001D);

    }

    /**
     * 500 in 7.5 and 500 in 0.65
     */
    @Test
    public void testQuoteTwoInvestors() {
        int loanAmount = 1000;
        String filePath = "filePath";

        List<Investor> investors = new ArrayList<>();
        investors.add(createInvestor("investorName", 0.075, 1300));
        investors.add(createInvestor("investorName2", 0.065, 500));
        when(fileReader.read(filePath)).thenReturn(investors);

        quote.quote(filePath, loanAmount);

        ArgumentCaptor<Quote> arg = ArgumentCaptor.forClass(Quote.class);
        verify(quoteWriter).write(arg.capture());
        Quote generatedQuote = arg.getValue();
        assertEquals(loanAmount, generatedQuote.getRequestedAmount());
        assertEquals(0.08252D, generatedQuote.getRate(), 0.001D);
        assertEquals(30.88D, generatedQuote.getMonthlyRepayment(), 0.001D);
        assertEquals(1111.59D, generatedQuote.getTotalRepayment(), 0.001D);

    }

    /**
     * 500 in 7.5
     * 500 in 0.65
     * 1000 in 0.064
     */
    @Test
    public void testQuoteThreeInvestors() {
        int loanAmount = 2000;
        String filePath = "filePath";

        List<Investor> investors = new ArrayList<>();
        investors.add(createInvestor("investorName", 0.075, 1300));
        investors.add(createInvestor("investorName2", 0.065, 500));
        investors.add(createInvestor("investorName3", 0.064, 1000));
        when(fileReader.read(filePath)).thenReturn(investors);

        quote.quote(filePath, loanAmount);

        ArgumentCaptor<Quote> arg = ArgumentCaptor.forClass(Quote.class);
        verify(quoteWriter).write(arg.capture());
        Quote generatedQuote = arg.getValue();
        assertEquals(loanAmount, generatedQuote.getRequestedAmount());
        assertEquals(0.067D, generatedQuote.getRate(), 0.001D);
        assertEquals(61.48D, generatedQuote.getMonthlyRepayment(), 0.001D);
        assertEquals(2213.32D, generatedQuote.getTotalRepayment(), 0.001D);

    }

    @Test(expected = MarketFileNotFoundException.class)
    public void testQuoteFileDoesNotExists() {
        int loanAmount = 1000;
        String filePath = "filePath";
        doThrow(MarketFileNotFoundException.class).when(fileReader).read(filePath);
        quote.quote(filePath, loanAmount);
    }

    @Test(expected = QuotationException.class)
    public void testQuoteUnderThousandThrowException() {
        int loanAmount = 100;
        quote.quote("filePath", loanAmount);
    }

    @Test(expected = QuotationException.class)
    public void testQuoteOverFifteenThousandThrowException() {
        int loanAmount = 15001;
        quote.quote("filePath", loanAmount);
    }

    @Test(expected = QuotationException.class)
    public void testQuoteNotMultipleOfHundredThrowException() {
        int loanAmount = 1753;
        quote.quote("filePath", loanAmount);
    }

    private Investor createInvestor(String investorName, double rate, int amountAvailable) {
        Investor investor = new Investor();
        investor.setName(investorName);
        investor.setRate(rate);
        investor.setAmountAvailable(amountAvailable);
        return investor;
    }
}
