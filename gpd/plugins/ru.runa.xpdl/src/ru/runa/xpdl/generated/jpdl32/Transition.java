//
// This file was ru.runa.xpdl.generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.04 at 12:24:30 PM MSD 
//


package ru.runa.xpdl.generated.jpdl32;


/**
 * Java content class for transition element declaration.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/D:/MyProjects/AltLinux/xsd/jpdl-3.2.xsd line 236)
 * <p>
 * <pre>
 * &lt;element name="transition">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element ref="{urn:jbpm.org:jpdl-3.2}description"/>
 *           &lt;element name="condition">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                     &lt;any/>
 *                   &lt;/sequence>
 *                   &lt;attribute name="expression" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;group ref="{urn:jbpm.org:jpdl-3.2}action-elements"/>
 *           &lt;element ref="{urn:jbpm.org:jpdl-3.2}exception-handler"/>
 *         &lt;/choice>
 *         &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="to" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 */
public interface Transition
    extends javax.xml.bind.Element, ru.runa.xpdl.generated.jpdl32.TransitionType
{


}
