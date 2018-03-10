package uk.co.pasquotto.zopa.quote;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.pasquotto.zopa.quote.service.QuoteService;
import uk.co.pasquotto.zopa.quote.writer.Output;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QuotationRunnerTest {

    @Mock
    private QuoteService quoteService;

    @Mock
    private Output output;

    @InjectMocks
    private QuotationRunner underTest;

    @Test
    public void testRun() throws Exception {
        underTest.run("fileName", "7500");

        ArgumentCaptor<String> fileCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> loanAmountCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(quoteService).quote(fileCaptor.capture(), loanAmountCaptor.capture());

        assertEquals("fileName", fileCaptor.getValue());
        assertEquals(7500, (int)loanAmountCaptor.getValue());
    }

    @Test
    public void testRunNoParameters() throws Exception{
        underTest.run();
        verify(output).print("It is not possible to provide a quote at this time.\nFile name and loan amount are needed\nUse:\njava -jar quote-0.0.1-SNAPSHOT.jar {fileName} {loanAmount}\n");
    }

    @Test
    public void testRunLoanAmountInvalid() throws Exception{
        underTest.run("fileName", "derf");
        verify(output).print("It is not possible to provide a quote at this time.\nLoan amount is invalid\nUse:\njava -jar quote-0.0.1-SNAPSHOT.jar {fileName} {loanAmount}\n");
    }

    @Test
    public void testRunLoanAmountNegativeIsInvalid() throws Exception{
        underTest.run("fileName", "-2000");
        verify(output).print("It is not possible to provide a quote at this time.\nLoan amount is invalid\nUse:\njava -jar quote-0.0.1-SNAPSHOT.jar {fileName} {loanAmount}\n");
    }

}
