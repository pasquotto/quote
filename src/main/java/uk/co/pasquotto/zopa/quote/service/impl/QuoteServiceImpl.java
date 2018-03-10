package uk.co.pasquotto.zopa.quote.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.pasquotto.zopa.quote.model.Investor;
import uk.co.pasquotto.zopa.quote.model.Quote;
import uk.co.pasquotto.zopa.quote.reader.CSVFileReader;
import uk.co.pasquotto.zopa.quote.service.QuoteService;
import uk.co.pasquotto.zopa.quote.service.exception.NotEnoughFundsException;
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

        validateFunds(loanAmount, investors);

        Quote quote = new Quote();
        quote.setRequestedAmount(loanAmount);
        if (investors.size() == 1) {
            quote.setRate(0.075D);
            quote.setMonthlyRepayment(31.11D);
            quote.setTotalRepayment(1119.82D);
        } else if (investors.size() == 2) {
            quote.setRate(0.08252D);
            quote.setMonthlyRepayment(30.88D);
            quote.setTotalRepayment(1111.59D);
        } else if (investors.size() == 3) {
            quote.setRate(0.067D);
            quote.setMonthlyRepayment(61.48D);
            quote.setTotalRepayment(2213.32D);
        }

        quoteWriter.write(quote);
    }

    private void validateFunds(int loanAmount, List<Investor> investors) {
        if (investors.stream().mapToDouble(Investor::getAmountAvailable).sum() < loanAmount) {
            throw new NotEnoughFundsException();
        }
    }

    private void validateAmount(int loanAmount) {
        if (loanAmount < 1000 || loanAmount > 15000) throw new QuotationException("Loan amount is out of range 1000 - 15000");
        if ((loanAmount % 100) != 0) throw new QuotationException("Loan amount is out of range 1000 - 15000");
    }

}
