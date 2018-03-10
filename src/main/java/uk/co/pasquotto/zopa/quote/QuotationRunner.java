package uk.co.pasquotto.zopa.quote;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import uk.co.pasquotto.zopa.quote.service.QuoteService;
import uk.co.pasquotto.zopa.quote.writer.Output;

@Controller
public class QuotationRunner implements CommandLineRunner {

    private Output output;

    private QuoteService quoteService;

    public QuotationRunner(QuoteService quoteService, Output output) {
        this.quoteService = quoteService;
        this.output = output;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            if (args.length != 2) throw new IllegalArgumentException("File name and loan amount are needed");
            String fileName = args[0];
            int loanAmount = Integer.parseUnsignedInt(args[1]);

            quoteService.quote(fileName, loanAmount);
        } catch (NumberFormatException e) {
            output.print("Loan amount is invalid\n" + usage());
        } catch (Exception e) {
            output.print(e.getMessage() + "\n" + usage());
        }
    }

    private String usage() {
        return "Use:\njava -jar quote-0.0.1-SNAPSHOT.jar {fileName} {loanAmount}";
    }
}
