package com.timex.bt.integration;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FTPCaller {

    public static boolean uploadFile(String host, int port, String user, String pass, String localPath,int bufferSize, String destinationPath, String connType) throws IOException {

        boolean status = false;
        String ftpUrl = connType + "://" + user + ":" + pass + "@" + host + ":" + port + "/" + destinationPath;
        System.out.println(ftpUrl);

        FTPSClient ftpClient = new FTPSClient();
        //ftpClient.
        try {
            System.setProperty("https.protocols", "SSLv3,TLSv1,TLSv1.1,TLSv1.2");
            ftpClient.connect(host, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            System.out.println(ftpClient.getEnabledProtocols());
            ftpClient.execPROT("P");
            ftpClient.getSystemName();


            FileInputStream inputStream = new FileInputStream(localPath);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File localFile = new File(localPath);


            InputStream inputStream1 = new FileInputStream(localFile);

            System.out.println("Start uploading first file");
            status = ftpClient.storeFile(destinationPath, inputStream1);
            inputStream1.close();


            OutputStream outputStream = ftpClient.storeFileStream(destinationPath);
            byte[] buffer = new byte[bufferSize];

            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            status = ftpClient.completePendingCommand();
            if (status) {
                System.out.println("File uploaded");
            }


        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }


        }
        return status;
    }

    public static void main(String[] args) throws Exception{
        System.out.println(uploadFile("avayasftp.avaya.com",1022,"test05","test123",
                "/home/c14795/IdeaProjects/FinalArt/s.txt",
                4096,"home/MIDDLEWARE/EDI/ss.txt","sftp"));
    }
}
