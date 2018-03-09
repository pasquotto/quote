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
    private QuoteService quote;

    @Test
    public void testQuoteOneInvestor() {
        int loanAmount = 1000;
        String filePath = "filePath";

        List<Investor> investors = new ArrayList<>();
        investors.add(createInvestor());
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

    private Investor createInvestor() {
        Investor investor = new Investor();
        investor.setName("investorName");
        investor.setRate(0.075);
        investor.setAmountAvailable(1300);
        return investor;
    }
}
