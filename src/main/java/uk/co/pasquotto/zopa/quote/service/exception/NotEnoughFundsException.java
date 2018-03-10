package uk.co.pasquotto.zopa.quote.service.exception;

public class NotEnoughFundsException extends RuntimeException {
    public NotEnoughFundsException(String message) {
        super(message);
    }
}
