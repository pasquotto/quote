package uk.co.pasquotto.zopa.quote.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.pasquotto.zopa.quote.model.Investor;
import uk.co.pasquotto.zopa.quote.model.LoanPart;
import uk.co.pasquotto.zopa.quote.model.Quote;
import uk.co.pasquotto.zopa.quote.reader.CSVFileReader;
import uk.co.pasquotto.zopa.quote.service.QuoteService;
import uk.co.pasquotto.zopa.quote.service.exception.NotEnoughFundsException;
import uk.co.pasquotto.zopa.quote.service.exception.QuotationException;
import uk.co.pasquotto.zopa.quote.writer.QuoteWriter;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static uk.co.pasquotto.zopa.quote.service.FinancialCalculationUtils.calculateMonthlyRepayments;
import static uk.co.pasquotto.zopa.quote.service.FinancialCalculationUtils.rate;

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
        // sort investors by lower rate
        List<Investor> sortedInvestors = investors.stream().sorted(Comparator.comparing(Investor::getRate)).collect(Collectors.toList());

        // define the parts that the investors can lend on their different rates
        List<LoanPart> loanParts = createLoanPartsFromInvestor(loanAmount, sortedInvestors);

        Quote quote = loanParts.stream().map(this::generateQuoteFromLoanPart)
                // sum up all quotes on the different rates
                .reduce(new Quote(0D, 0D, 0D),
                        (q1, q2) -> new Quote(0D, q1.getMonthlyRepayment() + q2.getMonthlyRepayment(),
                                q1.getTotalRepayment() + q2.getTotalRepayment()));

        calculateRate(quote, loanAmount);

        quote.setRequestedAmount(loanAmount);

        quoteWriter.write(quote);
    }

    /**
     * Create a loan part for each investor. if the first investor have enough money then just one LoanPart will be created,
     * otherwise it will iterate to the next investor until there is enough money to fund the loan
     * @param loanAmount the amount to be lent
     * @param investors list of investors
     * @return a list of loan parts with amounts and rates
     */
    private List<LoanPart> createLoanPartsFromInvestor(int loanAmount, List<Investor> investors) {
        List<LoanPart> loanParts = new LinkedList<>();
        int remainderOfLoan = loanAmount;
        for(Investor investor : investors) {
            int loanPartAmount = 0;
            if (investor.getAmountAvailable() > remainderOfLoan) {
                loanPartAmount = remainderOfLoan;
                investor.setAmountAvailable(investor.getAmountAvailable() - remainderOfLoan);
                remainderOfLoan = 0;
            } else if (investor.getAmountAvailable() <= remainderOfLoan) {
                loanPartAmount = investor.getAmountAvailable();
                remainderOfLoan -= investor.getAmountAvailable();
                investor.setAmountAvailable(0);
            }
            loanParts.add(new LoanPart(investor.getRate(), loanPartAmount, 36));
        }
        return loanParts;
    }

    private void calculateRate(Quote quote, int loanValue) {
        quote.setRate(rate(36, quote.getMonthlyRepayment() * -1, loanValue) * 12);
    }

    private Quote generateQuoteFromLoanPart(LoanPart loanPart) {
        Quote quote = new Quote();
        quote.setRequestedAmount(loanPart.getAmount());
        quote.setMonthlyRepayment(calculateMonthlyRepayments(loanPart.getAmount(),
                loanPart.getRate() / 12,
                loanPart.getTerm()));
        quote.setTotalRepayment(quote.getMonthlyRepayment() * loanPart.getTerm());
        return quote;
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
