package uk.co.pasquotto.zopa.quote.reader.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;
import uk.co.pasquotto.zopa.quote.model.Investor;
import uk.co.pasquotto.zopa.quote.reader.CSVFileReader;
import uk.co.pasquotto.zopa.quote.service.exception.MarketFileNotFoundException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Component
public class CSVFileReaderImpl implements CSVFileReader {
    @Override
    public List<Investor> read(String filePath) {
        File f = new File(filePath);
        if (!f.exists()) throw new MarketFileNotFoundException("File: " + filePath + " no found");

        List<Investor> list;
        try {
            list = getList(f);
        } catch (IOException e) {
            throw new MarketFileNotFoundException("File: " + filePath + " could not be read", e);
        }
        return list;
    }


    private List<Investor> getList(File file) throws IOException {
        try (Reader in = new FileReader(file)) {
            CsvToBean<Investor> csvToBean = new CsvToBeanBuilder<Investor>(in)
                    .withType(Investor.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        }
    }
}
