package com.wipro.utils;

public class XlsxBean {
    private String id;
    private String toName;
    private String toMailID;
    private String fromName;
    private String fromEmailID;
    private String body;
    private String subject;
    private String dateTimeReceived;

    public XlsxBean(String id, String toName, String toMailID, String fromName, String fromEmailID, String body, String subject, String dateTimeReceived) {
        this.id = id;
        this.toName = toName;
        this.toMailID = toMailID;
        this.fromName = fromName;
        this.fromEmailID = fromEmailID;
        this.body = body;
        this.subject = subject;
        this.dateTimeReceived = dateTimeReceived;
    }
}
