package uk.co.pasquotto.zopa.quote;

import org.springframework.stereotype.Service;

@Service
public class QuoteService {
    public void quote(String filePath, int loanAmount) {
        this.validateAmount(loanAmount);

    }

    private void validateAmount(int loanAmount) {
        if (loanAmount < 1000 || loanAmount > 15000) throw new QuotationException("Loan amount is out of range 1000 - 15000");
        if ((loanAmount % 100) != 0) throw new QuotationException("Loan amount is out of range 1000 - 15000");
    }
}
