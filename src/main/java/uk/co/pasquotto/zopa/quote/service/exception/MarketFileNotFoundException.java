package uk.co.pasquotto.zopa.quote.service.exception;

public class MarketFileNotFoundException extends RuntimeException {
    public MarketFileNotFoundException(String message) {
        super(message);
    }
    public MarketFileNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
