//
// This file was ru.runa.xpdl.generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.23 at 09:51:37 PM MSD 
//


package ru.runa.xpdl.generated.bpmnxpdl;


/**
 * Java content class for ResourceCosts element declaration.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/AltLinux/projects/xsd/bpmnxpdl_31.xsd line 2262)
 * <p>
 * <pre>
 * &lt;element name="ResourceCosts">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="ResourceCostName">
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="100"/>
 *               &lt;minLength value="0"/>
 *               &lt;whiteSpace value="preserve"/>
 *             &lt;/restriction>
 *           &lt;/element>
 *           &lt;element name="ResourceCost">
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/element>
 *           &lt;element name="CostUnitOfTime">
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *               &lt;enumeration value="second"/>
 *               &lt;enumeration value="minute"/>
 *               &lt;enumeration value="hour"/>
 *             &lt;/restriction>
 *           &lt;/element>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 */
public interface ResourceCosts
    extends javax.xml.bind.Element, ru.runa.xpdl.generated.bpmnxpdl.ResourceCostsType
{


}
