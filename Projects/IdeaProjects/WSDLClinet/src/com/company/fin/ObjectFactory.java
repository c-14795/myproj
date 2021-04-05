
package com.company.fin;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the integration.bt.timex.com package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetXmlDataResponse_QNAME = new QName("http://com.timex.bt.integration/", "getXmlDataResponse");
    private final static QName _GetXmlData_QNAME = new QName("http://com.timex.bt.integration/", "getXmlData");
    private final static QName _IOExceptionElement_QNAME = new QName("http://com.timex.bt.integration/", "IOExceptionElement");
    private final static QName _PostDataResponse_QNAME = new QName("http://com.timex.bt.integration/", "postDataResponse");
    private final static QName _PostData_QNAME = new QName("http://com.timex.bt.integration/", "postData");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: integration.bt.timex.com
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetXmlData }
     * 
     */
    public GetXmlData createGetXmlData() {
        return new GetXmlData();
    }

    /**
     * Create an instance of {@link GetXmlDataResponseElement }
     * 
     */
    public GetXmlDataResponseElement createGetXmlDataResponseElement() {
        return new GetXmlDataResponseElement();
    }

    /**
     * Create an instance of {@link GetXmlDataResponse }
     * 
     */
    public GetXmlDataResponse createGetXmlDataResponse() {
        return new GetXmlDataResponse();
    }

    /**
     * Create an instance of {@link PostDataResponseElement }
     * 
     */
    public PostDataResponseElement createPostDataResponseElement() {
        return new PostDataResponseElement();
    }

    /**
     * Create an instance of {@link PostDataElement }
     * 
     */
    public PostDataElement createPostDataElement() {
        return new PostDataElement();
    }

    /**
     * Create an instance of {@link PostDataResponse }
     * 
     */
    public PostDataResponse createPostDataResponse() {
        return new PostDataResponse();
    }

    /**
     * Create an instance of {@link IOException }
     * 
     */
    public IOException createIOException() {
        return new IOException();
    }

    /**
     * Create an instance of {@link GetXmlDataElement }
     * 
     */
    public GetXmlDataElement createGetXmlDataElement() {
        return new GetXmlDataElement();
    }

    /**
     * Create an instance of {@link PostData }
     * 
     */
    public PostData createPostData() {
        return new PostData();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetXmlDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.timex.bt.integration/", name = "getXmlDataResponse")
    public JAXBElement<GetXmlDataResponse> createGetXmlDataResponse(GetXmlDataResponse value) {
        return new JAXBElement<GetXmlDataResponse>(_GetXmlDataResponse_QNAME, GetXmlDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetXmlData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.timex.bt.integration/", name = "getXmlData")
    public JAXBElement<GetXmlData> createGetXmlData(GetXmlData value) {
        return new JAXBElement<GetXmlData>(_GetXmlData_QNAME, GetXmlData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.timex.bt.integration/", name = "IOExceptionElement")
    public JAXBElement<IOException> createIOExceptionElement(IOException value) {
        return new JAXBElement<IOException>(_IOExceptionElement_QNAME, IOException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PostDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.timex.bt.integration/", name = "postDataResponse")
    public JAXBElement<PostDataResponse> createPostDataResponse(PostDataResponse value) {
        return new JAXBElement<PostDataResponse>(_PostDataResponse_QNAME, PostDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PostData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.timex.bt.integration/", name = "postData")
    public JAXBElement<PostData> createPostData(PostData value) {
        return new JAXBElement<PostData>(_PostData_QNAME, PostData.class, null, value);
    }

}
