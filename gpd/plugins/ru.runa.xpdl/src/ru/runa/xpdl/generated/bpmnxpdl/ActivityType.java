//
// This file was ru.runa.xpdl.generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.23 at 09:51:37 PM MSD 
//


package ru.runa.xpdl.generated.bpmnxpdl;


/**
 * Java content class for anonymous complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/AltLinux/projects/xsd/bpmnxpdl_31.xsd line 25)
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}Description" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}Limit" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}Object" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}NodeGraphicsInfos" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}Route"/>
 *           &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}Implementation"/>
 *           &lt;choice minOccurs="0">
 *             &lt;element ref="{http://www.wfmc.org/2002/XPDL1.0}TC1025BlockActivity"/>
 *             &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}BlockActivity"/>
 *           &lt;/choice>
 *           &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}Event"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}Transaction" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}Performers" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}Performer" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2002/XPDL1.0}TC1025StartMode" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2002/XPDL1.0}TC1025FinishMode" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}Priority" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{http://www.wfmc.org/2002/XPDL1.0}TC1025Deadline" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}Deadline" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}SimulationInformation" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}Icon" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}Documentation" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}TransitionRestrictions" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}ExtendedAttributes" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}DataFields" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}InputSets" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}OutputSets" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}IORules" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}Loop" minOccurs="0"/>
 *         &lt;element ref="{http://www.wfmc.org/2008/XPDL2.1}Assignments" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;sequence>
 *             &lt;element name="Extensions" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *             &lt;any/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="CompletionQuantity" type="{http://www.w3.org/2001/XMLSchema}integer" default="1" />
 *       &lt;attribute name="FinishMode">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="Automatic"/>
 *             &lt;enumeration value="Manual"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="Id" use="required" type="{http://www.wfmc.org/2008/XPDL2.1}Id" />
 *       &lt;attribute name="IsATransaction" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="IsForCompensation" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="StartActivity" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="StartMode">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="Automatic"/>
 *             &lt;enumeration value="Manual"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="StartQuantity" type="{http://www.w3.org/2001/XMLSchema}integer" default="1" />
 *       &lt;attribute name="Status" default="None">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="None"/>
 *             &lt;enumeration value="Ready"/>
 *             &lt;enumeration value="Active"/>
 *             &lt;enumeration value="Cancelled"/>
 *             &lt;enumeration value="Aborting"/>
 *             &lt;enumeration value="Aborted"/>
 *             &lt;enumeration value="Completing"/>
 *             &lt;enumeration value="Completed"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ActivityType {


    /**
     * Gets the value of the documentation property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.DocumentationType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Documentation}
     */
    ru.runa.xpdl.generated.bpmnxpdl.DocumentationType getDocumentation();

    /**
     * Sets the value of the documentation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.DocumentationType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Documentation}
     */
    void setDocumentation(ru.runa.xpdl.generated.bpmnxpdl.DocumentationType value);

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
     * Gets the value of the isForCompensation property.
     * 
     */
    boolean isIsForCompensation();

    /**
     * Sets the value of the isForCompensation property.
     * 
     */
    void setIsForCompensation(boolean value);

    /**
     * Gets the value of the startMode property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getStartMode();

    /**
     * Sets the value of the startMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setStartMode(java.lang.String value);

    /**
     * Gets the value of the inputSets property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.InputSets}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.InputSetsType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.InputSetsType getInputSets();

    /**
     * Sets the value of the inputSets property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.InputSets}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.InputSetsType}
     */
    void setInputSets(ru.runa.xpdl.generated.bpmnxpdl.InputSetsType value);

    /**
     * Gets the value of the simulationInformation property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.SimulationInformation}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.SimulationInformationType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.SimulationInformationType getSimulationInformation();

    /**
     * Sets the value of the simulationInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.SimulationInformation}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.SimulationInformationType}
     */
    void setSimulationInformation(ru.runa.xpdl.generated.bpmnxpdl.SimulationInformationType value);

    /**
     * Deprecated from XPDL2.0. Must be a child of  Performers
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.PerformerType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Performer}
     */
    ru.runa.xpdl.generated.bpmnxpdl.PerformerType getPerformer();

    /**
     * Deprecated from XPDL2.0. Must be a child of  Performers
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.PerformerType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Performer}
     */
    void setPerformer(ru.runa.xpdl.generated.bpmnxpdl.PerformerType value);

    /**
     * Gets the value of the Deadline property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Deadline property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeadline().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ru.runa.xpdl.generated.bpmnxpdl.DeadlineType}
     * {@link ru.runa.xpdl.generated.bpmnxpdl.Deadline}
     * 
     */
    java.util.List getDeadline();

    /**
     * Gets the value of the TC1025Deadline property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the TC1025Deadline property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTC1025Deadline().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025DeadlineType}
     * {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025Deadline}
     * 
     */
    java.util.List getTC1025Deadline();

    /**
     * Gets the value of the finishMode property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getFinishMode();

    /**
     * Sets the value of the finishMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setFinishMode(java.lang.String value);

    /**
     * Gets the value of the tc1025FinishMode property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025FinishModeType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025FinishMode}
     */
    ru.runa.xpdl.generated.bpmnxpdl.TC1025FinishModeType getTC1025FinishMode();

    /**
     * Sets the value of the tc1025FinishMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025FinishModeType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025FinishMode}
     */
    void setTC1025FinishMode(ru.runa.xpdl.generated.bpmnxpdl.TC1025FinishModeType value);

    /**
     * Gets the value of the nodeGraphicsInfos property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.NodeGraphicsInfos}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.NodeGraphicsInfosType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.NodeGraphicsInfosType getNodeGraphicsInfos();

    /**
     * Sets the value of the nodeGraphicsInfos property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.NodeGraphicsInfos}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.NodeGraphicsInfosType}
     */
    void setNodeGraphicsInfos(ru.runa.xpdl.generated.bpmnxpdl.NodeGraphicsInfosType value);

    /**
     * Gets the value of the startQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link java.math.BigInteger}
     */
    java.math.BigInteger getStartQuantity();

    /**
     * Sets the value of the startQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.math.BigInteger}
     */
    void setStartQuantity(java.math.BigInteger value);

    /**
     * Gets the value of the transitionRestrictions property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TransitionRestrictionsType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TransitionRestrictions}
     */
    ru.runa.xpdl.generated.bpmnxpdl.TransitionRestrictionsType getTransitionRestrictions();

    /**
     * Sets the value of the transitionRestrictions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TransitionRestrictionsType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TransitionRestrictions}
     */
    void setTransitionRestrictions(ru.runa.xpdl.generated.bpmnxpdl.TransitionRestrictionsType value);

    /**
     * Gets the value of the ioRules property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.IORulesType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.IORules}
     */
    ru.runa.xpdl.generated.bpmnxpdl.IORulesType getIORules();

    /**
     * Sets the value of the ioRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.IORulesType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.IORules}
     */
    void setIORules(ru.runa.xpdl.generated.bpmnxpdl.IORulesType value);

    /**
     * Gets the value of the completionQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link java.math.BigInteger}
     */
    java.math.BigInteger getCompletionQuantity();

    /**
     * Sets the value of the completionQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.math.BigInteger}
     */
    void setCompletionQuantity(java.math.BigInteger value);

    /**
     * Gets the value of the route property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Route}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.RouteType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.RouteType getRoute();

    /**
     * Sets the value of the route property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Route}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.RouteType}
     */
    void setRoute(ru.runa.xpdl.generated.bpmnxpdl.RouteType value);

    /**
     * Gets the value of the object property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Object}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ObjectType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.ObjectType getObject();

    /**
     * Sets the value of the object property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Object}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ObjectType}
     */
    void setObject(ru.runa.xpdl.generated.bpmnxpdl.ObjectType value);

    /**
     * Gets the value of the dataFields property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.DataFieldsType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.DataFields}
     */
    ru.runa.xpdl.generated.bpmnxpdl.DataFieldsType getDataFields();

    /**
     * Sets the value of the dataFields property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.DataFieldsType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.DataFields}
     */
    void setDataFields(ru.runa.xpdl.generated.bpmnxpdl.DataFieldsType value);

    /**
     * Gets the value of the extensions property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.AnyType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.AnyType getExtensions();

    /**
     * Sets the value of the extensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.AnyType}
     */
    void setExtensions(ru.runa.xpdl.generated.bpmnxpdl.AnyType value);

    /**
     * Gets the value of the startActivity property.
     * 
     */
    boolean isStartActivity();

    /**
     * Sets the value of the startActivity property.
     * 
     */
    void setStartActivity(boolean value);

    /**
     * Gets the value of the outputSets property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.OutputSetsType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.OutputSets}
     */
    ru.runa.xpdl.generated.bpmnxpdl.OutputSetsType getOutputSets();

    /**
     * Sets the value of the outputSets property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.OutputSetsType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.OutputSets}
     */
    void setOutputSets(ru.runa.xpdl.generated.bpmnxpdl.OutputSetsType value);

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
     * Gets the value of the tc1025BlockActivity property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025BlockActivityType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025BlockActivity}
     */
    ru.runa.xpdl.generated.bpmnxpdl.TC1025BlockActivityType getTC1025BlockActivity();

    /**
     * Sets the value of the tc1025BlockActivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025BlockActivityType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025BlockActivity}
     */
    void setTC1025BlockActivity(ru.runa.xpdl.generated.bpmnxpdl.TC1025BlockActivityType value);

    /**
     * Gets the value of the transaction property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Transaction}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TransactionType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.TransactionType getTransaction();

    /**
     * Sets the value of the transaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Transaction}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TransactionType}
     */
    void setTransaction(ru.runa.xpdl.generated.bpmnxpdl.TransactionType value);

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
     * BPMN: Identifies XPDL activity as a BPMN event.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Event}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.EventType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.EventType getEvent();

    /**
     * BPMN: Identifies XPDL activity as a BPMN event.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Event}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.EventType}
     */
    void setEvent(ru.runa.xpdl.generated.bpmnxpdl.EventType value);

    /**
     * Gets the value of the isATransaction property.
     * 
     */
    boolean isIsATransaction();

    /**
     * Sets the value of the isATransaction property.
     * 
     */
    void setIsATransaction(boolean value);

    /**
     * BPMN: corresponds to an activity, which could be a task or subprocess.[Suggest change element to BpmnActivity, since there is an attribute Implementation which means something else entirely.]
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Implementation}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ImplementationType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.ImplementationType getImplementation();

    /**
     * BPMN: corresponds to an activity, which could be a task or subprocess.[Suggest change element to BpmnActivity, since there is an attribute Implementation which means something else entirely.]
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Implementation}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.ImplementationType}
     */
    void setImplementation(ru.runa.xpdl.generated.bpmnxpdl.ImplementationType value);

    /**
     * Gets the value of the performers property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.PerformersType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Performers}
     */
    ru.runa.xpdl.generated.bpmnxpdl.PerformersType getPerformers();

    /**
     * Sets the value of the performers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.PerformersType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Performers}
     */
    void setPerformers(ru.runa.xpdl.generated.bpmnxpdl.PerformersType value);

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.PriorityType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Priority}
     */
    ru.runa.xpdl.generated.bpmnxpdl.PriorityType getPriority();

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.PriorityType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Priority}
     */
    void setPriority(ru.runa.xpdl.generated.bpmnxpdl.PriorityType value);

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
     * Gets the value of the blockActivity property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.BlockActivity}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.BlockActivityType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.BlockActivityType getBlockActivity();

    /**
     * Sets the value of the blockActivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.BlockActivity}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.BlockActivityType}
     */
    void setBlockActivity(ru.runa.xpdl.generated.bpmnxpdl.BlockActivityType value);

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getStatus();

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setStatus(java.lang.String value);

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
     * Gets the value of the loop property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Loop}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.LoopType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.LoopType getLoop();

    /**
     * Sets the value of the loop property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Loop}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.LoopType}
     */
    void setLoop(ru.runa.xpdl.generated.bpmnxpdl.LoopType value);

    /**
     * Gets the value of the tc1025StartMode property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025StartMode}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025StartModeType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.TC1025StartModeType getTC1025StartMode();

    /**
     * Sets the value of the tc1025StartMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025StartMode}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.TC1025StartModeType}
     */
    void setTC1025StartMode(ru.runa.xpdl.generated.bpmnxpdl.TC1025StartModeType value);

    /**
     * Gets the value of the assignments property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Assignments}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.AssignmentsType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.AssignmentsType getAssignments();

    /**
     * Sets the value of the assignments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Assignments}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.AssignmentsType}
     */
    void setAssignments(ru.runa.xpdl.generated.bpmnxpdl.AssignmentsType value);

    /**
     * Gets the value of the icon property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Icon}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.IconType}
     */
    ru.runa.xpdl.generated.bpmnxpdl.IconType getIcon();

    /**
     * Sets the value of the icon property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Icon}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.IconType}
     */
    void setIcon(ru.runa.xpdl.generated.bpmnxpdl.IconType value);

    /**
     * Gets the value of the limit property.
     * 
     * @return
     *     possible object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.LimitType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Limit}
     */
    ru.runa.xpdl.generated.bpmnxpdl.LimitType getLimit();

    /**
     * Sets the value of the limit property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.LimitType}
     *     {@link ru.runa.xpdl.generated.bpmnxpdl.Limit}
     */
    void setLimit(ru.runa.xpdl.generated.bpmnxpdl.LimitType value);

}
