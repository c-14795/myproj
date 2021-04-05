package csvp;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.*;

import static csvp.GSProcessor.appendToFile;
import static csvp.GSProcessor.sequence;
import static csvp.GSProcessor.totalCount;

public class App {
    static PrintWriter out = null;

    public static void main(String[] args) throws IOException {


        BufferedWriter bw = null;
        FileWriter fw = null;

        fw = new FileWriter(args[1], true);
        bw = new BufferedWriter(fw);
        out = new PrintWriter(bw);


        CsvParser parser = new CsvParser(parserSettings());

        parser.parse(new File(args[0]), "UTF-8");
        parser.parse(new File(args[0]));
        appendToFile("CTT~"+sequence+"\n" +
                "SE~"+totalCount+"~0001\n" +
                "GE~1~73\n" +
                "IEA~1~000000073");


        out.close();
        bw.close();
        fw.close();
    }


    public static CsvParserSettings parserSettings()

    {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaders();
        settings.getFormat().setDelimiter("|");
        settings.getFormat().setQuote('\0');
        settings.setDelimiterDetectionEnabled(true);
        settings.setProcessor(new GSProcessor());

        return settings;
    }



}
