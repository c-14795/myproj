package FTPFilePush;

import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.AbstractRowProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.File;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {


        CsvParser parser = new CsvParser(parserSettings());

        parser.parse(new File("/home/c14795/Documents/file conversion/GS_Source.dat"), "UTF-8");

    }


    public static CsvParserSettings parserSettings()

    {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);

        settings.getFormat().setDelimiter("|");
        settings.setDelimiterDetectionEnabled(true);

        settings.setProcessor(new AbstractRowProcessor() {
            @Override
            public void rowProcessed(String[] row, ParsingContext context) {
                System.out.println(Arrays.toString(row));

                if (row[1].equals("100363")) System.exit(1);
            }
        });

        return settings;
    }
}
