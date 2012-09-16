//
// This file was ru.runa.xpdl.generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.23 at 09:51:37 PM MSD 
//


package ru.runa.xpdl.generated.bpmnxpdl;


/**
 * Java content class for ParticipantType2 complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/AltLinux/projects/xsd/bpmnxpdl_31.xsd line 1858)
 * <p>
 * <pre>
 * &lt;complexType name="ParticipantType2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}ParticipantType"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}Description" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}ExternalReference" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}ExtendedAttributes" minOccurs="0"/>
 *         &lt;any/>
 *       &lt;/sequence>
 *       &lt;attribute name="Id" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *       &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ParticipantType2 {


    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getName();

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setName(java.lang.String value);

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
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.DescriptionType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Description}
     */
    ru.runa.xpdl.generated.bpmnxpdl.DescriptionType getDescription();

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.DescriptionType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Description}
     */
    void setDescription(ru.runa.xpdl.generated.bpmnxpdl.DescriptionType value);

    /**
     * Gets the value of the extendedAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ExtendedAttributesType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ExtendedAttributes}
     */
    ru.runa.xpdl.generated.bpmnxpdl.ExtendedAttributesType getExtendedAttributes();

    /**
     * Sets the value of the extendedAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ExtendedAttributesType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ExtendedAttributes}
     */
    void setExtendedAttributes(ru.runa.xpdl.generated.bpmnxpdl.ExtendedAttributesType value);

    /**
     * Gets the value of the externalReference property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ExternalReferenceType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ExternalReference}
     */
    ru.runa.xpdl.generated.bpmnxpdl.ExternalReferenceType getExternalReference();

    /**
     * Sets the value of the externalReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ExternalReferenceType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ExternalReference}
     */
    void setExternalReference(ru.runa.xpdl.generated.bpmnxpdl.ExternalReferenceType value);

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getId();

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setId(java.lang.String value);

    /**
     * Gets the value of the participantType property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ParticipantTypeType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ParticipantType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.ParticipantTypeType getParticipantType();

    /**
     * Sets the value of the participantType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ParticipantTypeType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ParticipantType}
     */
    void setParticipantType(ru.runa.xpdl.generated.bpmnxpdl.ParticipantTypeType value);

}
