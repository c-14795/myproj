package com.company;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Utility {

    public static void resultSet2Xml(ResultSet rs, String filepath) {
        try {
            DocumentBuilderFactory docFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            Document doc = builder.newDocument();
            ResultSetMetaData rsMetaData = rs.getMetaData();
            String prefix = "ns1";
            //Element inbound997 = doc.createElement("Inboud997");
            Element inbound997 = doc.createElementNS("http://www.avaya.com/commondatamodel/Inboud997", prefix + ":Inboud997");
            doc.appendChild(inbound997);
            Element ackData = doc.createElement("997AckData");
            ackData.setPrefix(prefix);
            inbound997.appendChild(ackData);

            while (rs.next()) {
                Element inbound997Message = doc.createElement("Inbound997Message");
                inbound997Message.setPrefix(prefix);
                ackData.appendChild(inbound997Message);
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                    Element cell = doc.createElement(rsMetaData.getColumnLabel(i));
                    cell.setPrefix(prefix);
                    cell.appendChild(doc.createTextNode(rs.getObject(i).toString()));
                    inbound997Message.appendChild(cell);

                }
            }
            System.out.println(getDocumentAsXml(doc));

        } catch (Exception e) {
            System.out.println("check for the result set correctness... ");
            e.printStackTrace();

        }

    }

    public static String getDocumentAsXml(Document doc)
            throws TransformerConfigurationException, TransformerException {
        DOMSource domSource = new DOMSource(doc);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        java.io.StringWriter sw = new java.io.StringWriter();
        StreamResult sr = new StreamResult(sw);
        transformer.transform(domSource, sr);
        return sw.toString();
    }
}

