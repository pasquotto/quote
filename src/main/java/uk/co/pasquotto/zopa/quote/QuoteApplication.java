package uk.co.pasquotto.zopa.quote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@SpringBootApplication
public class QuoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuoteApplication.class, args);
	}

	/*private List<Property> getListFromProperty(File file) throws IOException {
		try (
				Reader in = new FileReader(file)
		) {
			CsvToBean<Property> csvToBean = new CsvToBeanBuilder<Property>(in)
					.withType(Property.class)
					.withIgnoreLeadingWhiteSpace(true)
					.build();
			return csvToBean.parse();
		}
	}*/
}
