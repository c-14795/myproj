package com.wipro.fetchmails;

import com.univocity.parsers.csv.CsvWriter;
import com.wipro.utils.Props;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import static com.wipro.utils.Constants.*;

public class Main {
    static ArrayList<String[]> mailData = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        CsvWriter writer = null;

        try {


            new Mail().fetchData();
            System.out.println("#$%" + workbook.getSheet("Data").getRow(2).getCell(7));
            if (!Props.PropertiesFile().getProperty("FILE_TYPE").equalsIgnoreCase("csv")) {
                FileOutputStream outputStream = new FileOutputStream(path.replace(".csv", ".xlsx"));

                workbook.write(outputStream);
            }
            else
            {
                writer=getCsvWriter();
                writer.writeHeaders(headers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Props.PropertiesFile().getProperty("FILE_TYPE").equalsIgnoreCase("csv")) {
                for (String[] row : mailData) {
                    writer.writeRow(row);
                }
                writer.close();
            }
            workbook.close();
        }
    }
}
