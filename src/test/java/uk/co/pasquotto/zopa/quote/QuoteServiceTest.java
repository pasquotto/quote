package uk.co.pasquotto.zopa.quote;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.stereotype.Service;

@RunWith(MockitoJUnitRunner.class)
public class QuoteServiceTest {

    @InjectMocks
    private QuoteService quote;

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
}
