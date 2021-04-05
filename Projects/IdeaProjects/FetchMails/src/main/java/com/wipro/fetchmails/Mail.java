package com.wipro.fetchmails;

import com.wipro.utils.Constants;

import static com.wipro.utils.Constants.dateTimeFormat;
import static com.wipro.utils.Constants.workbook;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.BasePropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.SortDirection;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.FolderView;
import microsoft.exchange.webservices.data.search.ItemView;


import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Pattern;

import com.wipro.utils.Props;


import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFSheet;


class Mail {

    private ExchangeService service = null;
    private FolderId folderId = null;
    private final Properties properties = Props.PropertiesFile();
    private final String senders = properties.getProperty("SENDER_LIST", "None");
    private final List<String> senders_list = Objects.equals(senders, "None") ? new ArrayList<>() : Arrays.asList(senders.split(Pattern.quote(";")));

    Mail() throws Exception {
        setService();
        setFolderId();
    }

    private void setService() throws URISyntaxException {

        service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        ExchangeCredentials credentials = new WebCredentials(properties.getProperty("USER_NAME"), properties.getProperty("PSWD"));
        service.setCredentials(credentials);
        URI url = new URI(properties.getProperty("URL"));
        service.setUrl(url);

    }

    private void setFolderId() throws Exception {
        ArrayList<Folder> folders = service.findFolders(WellKnownFolderName.MsgFolderRoot, new FolderView(100)).getFolders();
        for (Folder f : folders) {
            ///System.out.println(f.getDisplayName());
            if (properties.getProperty("FOLDER_NAME").equals(f.getDisplayName())) {
                //System.out.println("I am here");
                folderId = f.getId();
                // System.out.println("Success");
                //System.out.println(folder_id.toString());
                // System.out.println("Success");
                break;
            }
        }

    }

    void fetchData() throws Exception {

        Folder inbox = Folder.bind(service, folderId == null ? new FolderId(WellKnownFolderName.Inbox) : folderId);
        ItemView view = new ItemView(100);
        view.getOrderBy().add(ItemSchema.DateTimeReceived, SortDirection.Descending);
        FindItemsResults<Item> findResults;

        Date start_date = Constants.formatter.parse(properties.getProperty("START_DATE"));
        Date end_date = Constants.formatter.parse(properties.getProperty("END_DATE"));
        PropertySet prpSet = new PropertySet(BasePropertySet.FirstClassProperties);
        prpSet.setRequestedBodyType(BodyType.Text);
        XSSFSheet sheet = workbook.createSheet("Data");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        int rowNum = 1;
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("ToName");
        headerRow.createCell(2).setCellValue("ToMailID");
        headerRow.createCell(3).setCellValue("FromName");
        headerRow.createCell(4).setCellValue("FromEmailID");
        headerRow.createCell(5).setCellValue("Body");
        headerRow.createCell(6).setCellValue("Subject");
        headerRow.createCell(7).setCellValue("DateTimeReceived");
        System.out.println("S");
        my_loop:
        do {

            findResults = service.findItems(inbox.getId(), view);
            for (Item item : findResults.getItems()) {
                if (item.getDateTimeReceived().before(end_date) && item.getDateTimeReceived().after(start_date)) {
                    String sender_address = ((EmailMessage) item).getFrom().getAddress();
                    String unique_id = item.getDateTimeReceived().getTime() + java.net.InetAddress.getLocalHost().getHostName().toString();

                    String date_time = Instant.ofEpochMilli(item.getDateTimeReceived().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime().format(dateTimeFormat);

                    if (senders_list.contains(sender_address)) {
                        item.load(prpSet);
                        String body = item.getBody().toString().split(Pattern.quote("From: "))[0].replaceAll("[\r\n]+", "\n");
                        String fromName = ((EmailMessage) item).getSender().getName().split(Pattern.quote("("))[0];
                        System.out.println(body.replaceAll("[\r\n]+", "\n"));
                        Main.mailData.add(new String[]{unique_id, properties.getProperty("YOUR_NAME"), properties.getProperty("YOUR_MAIL_ID"), fromName, sender_address, body, item.getSubject(), date_time});
                        Row row = sheet.createRow(rowNum++);
                        row.createCell(0).setCellValue(unique_id);
                        row.createCell(1).setCellValue(properties.getProperty("YOUR_NAME"));
                        row.createCell(2).setCellValue(properties.getProperty("YOUR_MAIL_ID"));
                        row.createCell(3).setCellValue(fromName);
                        row.createCell(4).setCellValue(sender_address);
                        row.createCell(5).setCellValue(body);
                        row.createCell(6).setCellValue(item.getSubject());
                        row.createCell(7).setCellValue(date_time);
                    }
                } else if (item.getDateTimeReceived().before(start_date)) {
                    break my_loop;
                }
            }
            view.setOffset(view.getOffset() + 100);
        } while (findResults.isMoreAvailable());

    }


}

 