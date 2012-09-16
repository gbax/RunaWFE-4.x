//
// This file was ru.runa.xpdl.generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.04 at 12:24:30 PM MSD 
//


package ru.runa.xpdl.generated.jpdl32;


/**
 * Java content class for anonymous complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/D:/MyProjects/AltLinux/xsd/jpdl-3.2.xsd line 192)
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{urn:jbpm.org:jpdl-3.2}task"/>
 *         &lt;group ref="{urn:jbpm.org:jpdl-3.2}node-content-elements"/>
 *       &lt;/choice>
 *       &lt;attribute name="async" type="{http://www.w3.org/2001/XMLSchema}string" default="false" />
 *       &lt;attribute name="create-tasks" type="{urn:jbpm.org:jpdl-3.2}booleanType" default="true" />
 *       &lt;attribute name="end-tasks" type="{urn:jbpm.org:jpdl-3.2}booleanType" default="false" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="signal" default="last">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="unsynchronized"/>
 *             &lt;enumeration value="never"/>
 *             &lt;enumeration value="first"/>
 *             &lt;enumeration value="first-wait"/>
 *             &lt;enumeration value="last"/>
 *             &lt;enumeration value="last-wait"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface TaskNodeType extends GeneralNodeType{



    /**
     * Gets the value of the async property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getAsync();

    /**
     * Sets the value of the async property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setAsync(java.lang.String value);

    /**
     * Gets the value of the signal property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getSignal();

    /**
     * Sets the value of the signal property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setSignal(java.lang.String value);

    /**
     * Gets the value of the createTasks property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getCreateTasks();

    /**
     * Sets the value of the createTasks property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setCreateTasks(java.lang.String value);

    /**
     * Gets the value of the endTasks property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getEndTasks();

    /**
     * Sets the value of the endTasks property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setEndTasks(java.lang.String value);

    /**
     * Gets the value of the TaskOrDescriptionOrEvent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the TaskOrDescriptionOrEvent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaskOrDescriptionOrEvent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ru.runa.xpdl.generated.jpdl32.Description}
     * {@link ru.runa.xpdl.generated.jpdl32.Task}
     * {@link ru.runa.xpdl.generated.jpdl32.Transition}
     * {@link ru.runa.xpdl.generated.jpdl32.ExceptionHandler}
     * {@link ru.runa.xpdl.generated.jpdl32.Timer}
     * {@link ru.runa.xpdl.generated.jpdl32.Event}
     * 
     */
    java.util.List getTaskOrDescriptionOrEvent();

}
