
package ru.runa.wfe.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for exportBotTask complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exportBotTask">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="user" type="{http://impl.service.wfe.runa.ru/}user" minOccurs="0"/>
 *         &lt;element name="bot" type="{http://impl.service.wfe.runa.ru/}bot" minOccurs="0"/>
 *         &lt;element name="botTaskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exportBotTask", propOrder = {
    "user",
    "bot",
    "botTaskName"
})
public class ExportBotTask {

    protected User user;
    protected Bot bot;
    protected String botTaskName;

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setUser(User value) {
        this.user = value;
    }

    /**
     * Gets the value of the bot property.
     * 
     * @return
     *     possible object is
     *     {@link Bot }
     *     
     */
    public Bot getBot() {
        return bot;
    }

    /**
     * Sets the value of the bot property.
     * 
     * @param value
     *     allowed object is
     *     {@link Bot }
     *     
     */
    public void setBot(Bot value) {
        this.bot = value;
    }

    /**
     * Gets the value of the botTaskName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBotTaskName() {
        return botTaskName;
    }

    /**
     * Sets the value of the botTaskName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBotTaskName(String value) {
        this.botTaskName = value;
    }

}
