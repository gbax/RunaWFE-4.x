//
// This file was ru.runa.xpdl.generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.23 at 09:51:37 PM MSD 
//


package ru.runa.xpdl.generated.bpmnxpdl;


/**
 * Java content class for anonymous complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/AltLinux/projects/xsd/bpmnxpdl_31.xsd line 3076)
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="TimeDate" type="{http://www.wfmc.org/2008/XPDL2.1}ExpressionType"/>
 *           &lt;element name="TimeCycle" type="{http://www.wfmc.org/2008/XPDL2.1}ExpressionType"/>
 *         &lt;/choice>
 *         &lt;any/>
 *       &lt;/sequence>
 *       &lt;attribute name="TimeCycle2" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="TimeDate2" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface TriggerTimerType {


    /**
     * Gets the value of the timeCycle2 property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getTimeCycle2();

    /**
     * Sets the value of the timeCycle2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setTimeCycle2(java.lang.String value);

    /**
     * Gets the value of the Any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.Object}
     * 
     */
    java.util.List getAny();

    /**
     * Gets the value of the timeCycle property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ExpressionType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.ExpressionType getTimeCycle();

    /**
     * Sets the value of the timeCycle property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ExpressionType}
     */
    void setTimeCycle(ru.runa.xpdl.generated.bpmnxpdl.ExpressionType value);

    /**
     * Gets the value of the timeDate2 property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getTimeDate2();

    /**
     * Sets the value of the timeDate2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setTimeDate2(java.lang.String value);

    /**
     * Gets the value of the timeDate property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ExpressionType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.ExpressionType getTimeDate();

    /**
     * Sets the value of the timeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ExpressionType}
     */
    void setTimeDate(ru.runa.xpdl.generated.bpmnxpdl.ExpressionType value);

}
