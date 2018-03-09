package uk.co.pasquotto.zopa.quote.reader;

import uk.co.pasquotto.zopa.quote.model.Investor;

import java.util.List;

public interface CSVFileReader {
    List<Investor> read(String filePath);
}
