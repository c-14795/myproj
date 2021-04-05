package wipro.utils;

import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Constants {

    private static final String dateSep = "-";
    private static final String dateFormat = "dd" + dateSep + "MM" + dateSep + "yyyy";
    public static final DateFormat formatter = new SimpleDateFormat(dateFormat);
    public static final String[] headers = new String[]{"ID", "ToName", "ToMailID", "FromName", "FromEmailID", "Body", "Subject", "DateTimeReceived"};
    public static final String path = Props.PropertiesFile().getProperty("OUTPUT_FILE", "output.csv");
    private static CsvWriter writer = null;
    public static final XSSFWorkbook workbook = new XSSFWorkbook();
    public static final String fileType = Props.PropertiesFile().getProperty("FILE_TYPE");

    public static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static CsvWriter getCsvWriter() {
        CsvFormat format = new CsvFormat();
        format.setDelimiter('|');
        CsvWriterSettings settings = new CsvWriterSettings();
        settings.setFormat(format);
        writer = new CsvWriter(new File(path+File.separator+"output"+ LocalDate.now().toString()+".csv"), settings);
        return writer;
    }



}


