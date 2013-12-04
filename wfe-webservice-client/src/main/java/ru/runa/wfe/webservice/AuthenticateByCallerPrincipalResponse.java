
package ru.runa.wfe.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for authenticateByCallerPrincipalResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="authenticateByCallerPrincipalResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="result" type="{http://impl.service.wfe.runa.ru/}user" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authenticateByCallerPrincipalResponse", propOrder = {
    "result"
})
public class AuthenticateByCallerPrincipalResponse {

    protected User result;

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setResult(User value) {
        this.result = value;
    }

}
