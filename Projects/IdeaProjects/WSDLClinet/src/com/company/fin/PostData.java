
package com.company.fin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for postData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="postData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sql" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="colMapFilepath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "postData", propOrder = {
    "sql",
    "url",
    "colMapFilepath"
})
public class PostData {

    @XmlElement(required = true, nillable = true)
    protected String sql;
    @XmlElement(required = true, nillable = true)
    protected String url;
    @XmlElement(required = true, nillable = true)
    protected String colMapFilepath;

    /**
     * Gets the value of the sql property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSql() {
        return sql;
    }

    /**
     * Sets the value of the sql property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSql(String value) {
        this.sql = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the colMapFilepath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColMapFilepath() {
        return colMapFilepath;
    }

    /**
     * Sets the value of the colMapFilepath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColMapFilepath(String value) {
        this.colMapFilepath = value;
    }

}
