//
// This file was ru.runa.xpdl.generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.04 at 12:24:30 PM MSD 
//


package ru.runa.xpdl.generated.jpdl32;


/**
 * Java content class for event element declaration.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/D:/MyProjects/AltLinux/xsd/jpdl-3.2.xsd line 329)
 * <p>
 * <pre>
 * &lt;element name="event">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;group ref="{urn:jbpm.org:jpdl-3.2}action-elements"/>
 *         &lt;/choice>
 *         &lt;attribute name="type" use="required">
 *           &lt;simpleType>
 *             &lt;union>
 *               &lt;simpleType>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                 &lt;/restriction>
 *               &lt;/simpleType>
 *               &lt;simpleType>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                   &lt;enumeration value="node-enter"/>
 *                   &lt;enumeration value="node-leave"/>
 *                   &lt;enumeration value="process-start"/>
 *                   &lt;enumeration value="process-end"/>
 *                   &lt;enumeration value="task-create"/>
 *                   &lt;enumeration value="task-assign"/>
 *                   &lt;enumeration value="task-start"/>
 *                   &lt;enumeration value="task-end"/>
 *                   &lt;enumeration value="before-signal"/>
 *                   &lt;enumeration value="after-signal"/>
 *                   &lt;enumeration value="superstate-enter"/>
 *                   &lt;enumeration value="superstate-leave"/>
 *                   &lt;enumeration value="timer-create"/>
 *                   &lt;enumeration value="subprocess-created"/>
 *                   &lt;enumeration value="subprocess-end"/>
 *                 &lt;/restriction>
 *               &lt;/simpleType>
 *             &lt;/union>
 *           &lt;/simpleType>
 *         &lt;/attribute>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 */
public interface Event
    extends javax.xml.bind.Element, ru.runa.xpdl.generated.jpdl32.EventType
{


}
