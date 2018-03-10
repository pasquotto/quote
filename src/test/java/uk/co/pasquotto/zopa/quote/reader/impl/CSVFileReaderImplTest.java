package uk.co.pasquotto.zopa.quote.reader.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import uk.co.pasquotto.zopa.quote.model.Investor;
import uk.co.pasquotto.zopa.quote.service.exception.MarketFileNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CSVFileReaderImplTest {

    private CSVFileReaderImpl underTest;

    @Before
    public void setUp() {
        underTest = new CSVFileReaderImpl();
    }

    @Test
    public void read() throws IOException {
        File f = new ClassPathResource("MarketDataforExercise.csv").getFile();

        List<Investor> investors = underTest.read(f.getAbsolutePath());

        assertNotNull(investors);
        assertEquals("Bob", investors.get(0).getName());
        assertEquals(0.075D, investors.get(0).getRate(), 0.0001D);
        assertEquals(640, investors.get(0).getAmountAvailable());

        assertEquals("Jane", investors.get(1).getName());
        assertEquals(0.069D, investors.get(1).getRate(), 0.0001D);
        assertEquals(480, investors.get(1).getAmountAvailable());

        assertEquals("Fred", investors.get(2).getName());
        assertEquals(0.071D, investors.get(2).getRate(), 0.0001D);
        assertEquals(520, investors.get(2).getAmountAvailable());
    }

    @Test(expected = MarketFileNotFoundException.class)
    public void readNoFound() throws IOException {
        List<Investor> investors = underTest.read("file.txt");

    }
}