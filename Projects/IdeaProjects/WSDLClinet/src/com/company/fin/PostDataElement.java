
package com.company.fin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="String_1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="String_2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="arrayOfString_3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "string1",
    "string2",
    "arrayOfString3"
})
@XmlRootElement(name = "postDataElement")
public class PostDataElement {

    @XmlElement(name = "String_1", required = true)
    protected String string1;
    @XmlElement(name = "String_2", required = true)
    protected String string2;
    @XmlElement(name = "arrayOfString_3", required = true)
    protected String arrayOfString3;

    /**
     * Gets the value of the string1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getString1() {
        return string1;
    }

    /**
     * Sets the value of the string1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setString1(String value) {
        this.string1 = value;
    }

    /**
     * Gets the value of the string2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getString2() {
        return string2;
    }

    /**
     * Sets the value of the string2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setString2(String value) {
        this.string2 = value;
    }

    /**
     * Gets the value of the arrayOfString3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrayOfString3() {
        return arrayOfString3;
    }

    /**
     * Sets the value of the arrayOfString3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrayOfString3(String value) {
        this.arrayOfString3 = value;
    }

}
