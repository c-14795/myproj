package wipro.fetchmails;

import com.univocity.parsers.csv.CsvWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static wipro.utils.Constants.*;
import static wipro.utils.Constants.getCsvWriter;

public class Main {
    static ArrayList<String[]> mailData = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        if (fileType.equalsIgnoreCase("csv")) {
            System.out.println("WRITING CSV");
            CsvWriter writer = getCsvWriter();

            try {

                writer.writeHeaders(headers);
                new Mail().fetchData();
                System.out.println("#$%" + workbook.getSheet("Data").getRow(2).getCell(7));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                for (String[] row : mailData) {
                    writer.writeRow(row);
                }
                System.out.println("Closing session");
                writer.close();
            }
        } else {
            System.out.println("WRITING EXCEL");
            try (FileOutputStream outputStream = new FileOutputStream(path+ File.separator+"output"+LocalDate.now()+".xlsx")) {
                new Mail().fetchData();
                System.out.println("#$%" + workbook.getSheet("Data").getRow(2).getCell(7));
                workbook.write(outputStream);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("closing session");
                workbook.close();
            }
        }

    }
}
