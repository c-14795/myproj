package com.wipro.utils;

import com.univocity.parsers.csv.Csv;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Constants {

    private static final String dateSep = "-";
    private static final String dateFormat = "dd" + dateSep + "MM" + dateSep + "yyyy";
    public static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter _dateTimeFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateFormat formatter = new SimpleDateFormat(dateFormat);
    public static final String[] headers = new String[]{"ID", "ToName", "ToMailID", "FromName", "FromEmailID", "Body", "Subject", "DateTimeReceived"};
    public static final String path = Props.PropertiesFile().getProperty("OUTPUT_FILE", "output.csv").replace(".csv",java.time.LocalDateTime.now().format(_dateTimeFormat).toString()+".csv");
    private static CsvWriter writer = null;
    public static final XSSFWorkbook workbook = new XSSFWorkbook();


    public static CsvWriter getCsvWriter() {
        CsvFormat format = new CsvFormat();
        format.setDelimiter('|');
        CsvWriterSettings settings = new CsvWriterSettings();
        settings.setFormat(format);
        writer = new CsvWriter(new File(path), settings);
        return writer;
    }



}


