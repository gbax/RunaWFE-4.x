//
// This file was ru.runa.xpdl.generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.23 at 09:51:37 PM MSD 
//


package ru.runa.xpdl.generated.bpmnxpdl;


/**
 * Java content class for anonymous complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/AltLinux/projects/xsd/TC-1025_schema_10_xpdl.xsd line 414)
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.wfmc.org/2002/XPDL1.0}TC1025Created" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2002/XPDL1.0}TC1025Description" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2002/XPDL1.0}TC1025Priority" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2002/XPDL1.0}TC1025Limit" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2002/XPDL1.0}TC1025ValidFrom" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2002/XPDL1.0}TC1025ValidTo" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2002/XPDL1.0}TC1025TimeEstimation" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="DurationUnit">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="Y"/>
 *             &lt;enumeration value="M"/>
 *             &lt;enumeration value="D"/>
 *             &lt;enumeration value="h"/>
 *             &lt;enumeration value="m"/>
 *             &lt;enumeration value="s"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface TC1025ProcessHeaderType {


    /**
     * Gets the value of the tc1025Priority property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getTC1025Priority();

    /**
     * Sets the value of the tc1025Priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setTC1025Priority(java.lang.String value);

    /**
     * Gets the value of the tc1025TimeEstimation property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025TimeEstimation}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025TimeEstimationType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.TC1025TimeEstimationType getTC1025TimeEstimation();

    /**
     * Sets the value of the tc1025TimeEstimation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025TimeEstimation}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025TimeEstimationType}
     */
    void setTC1025TimeEstimation(ru.runa.xpdl.generated.bpmnxpdl.TC1025TimeEstimationType value);

    /**
     * Gets the value of the tc1025ValidFrom property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getTC1025ValidFrom();

    /**
     * Sets the value of the tc1025ValidFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setTC1025ValidFrom(java.lang.String value);

    /**
     * Gets the value of the tc1025ValidTo property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getTC1025ValidTo();

    /**
     * Sets the value of the tc1025ValidTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setTC1025ValidTo(java.lang.String value);

    /**
     * Gets the value of the durationUnit property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getDurationUnit();

    /**
     * Sets the value of the durationUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setDurationUnit(java.lang.String value);

    /**
     * Gets the value of the tc1025Description property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getTC1025Description();

    /**
     * Sets the value of the tc1025Description property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setTC1025Description(java.lang.String value);

    /**
     * Gets the value of the tc1025Created property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getTC1025Created();

    /**
     * Sets the value of the tc1025Created property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setTC1025Created(java.lang.String value);

    /**
     * Gets the value of the tc1025Limit property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getTC1025Limit();

    /**
     * Sets the value of the tc1025Limit property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setTC1025Limit(java.lang.String value);

}
