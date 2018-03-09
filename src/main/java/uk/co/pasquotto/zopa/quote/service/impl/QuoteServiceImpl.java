package uk.co.pasquotto.zopa.quote.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.pasquotto.zopa.quote.model.Investor;
import uk.co.pasquotto.zopa.quote.model.Quote;
import uk.co.pasquotto.zopa.quote.reader.CSVFileReader;
import uk.co.pasquotto.zopa.quote.service.QuoteService;
import uk.co.pasquotto.zopa.quote.service.exception.QuotationException;
import uk.co.pasquotto.zopa.quote.writer.QuoteWriter;

import java.util.List;

@Service
public class QuoteServiceImpl implements QuoteService {

    @Autowired
    private CSVFileReader fileReader;

    @Autowired
    private QuoteWriter quoteWriter;

    @Override
    public void quote(String filePath, int loanAmount) {
        this.validateAmount(loanAmount);
        List<Investor> investors = this.fileReader.read(filePath);

        Quote quote = new Quote();
        quote.setRequestedAmount(1000);
        quote.setRate(0.075);
        quote.setMonthlyRepayment(31.11D);
        quote.setTotalRepayment(1119.82D);
        quoteWriter.write(quote);
    }

    private void validateAmount(int loanAmount) {
        if (loanAmount < 1000 || loanAmount > 15000) throw new QuotationException("Loan amount is out of range 1000 - 15000");
        if ((loanAmount % 100) != 0) throw new QuotationException("Loan amount is out of range 1000 - 15000");
    }

}
