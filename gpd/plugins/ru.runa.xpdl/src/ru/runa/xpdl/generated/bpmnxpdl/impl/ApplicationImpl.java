//
// This file was ru.runa.xpdl.generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.23 at 09:51:37 PM MSD 
//


package ru.runa.xpdl.generated.bpmnxpdl.impl;

public class ApplicationImpl
    extends ru.runa.xpdl.generated.bpmnxpdl.impl.ApplicationType2Impl
    implements ru.runa.xpdl.generated.bpmnxpdl.Application, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallableObject, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializable, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.ValidatableObject
{

    public final static java.lang.Class version = (ru.runa.xpdl.generated.bpmnxpdl.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (ru.runa.xpdl.generated.bpmnxpdl.Application.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "http://www.wfmc.org/2008/XPDL2.1";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "Application";
    }

    public ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingEventHandler createUnmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context) {
        return new ru.runa.xpdl.generated.bpmnxpdl.impl.ApplicationImpl.Unmarshaller(context);
    }

    public void serializeBody(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("http://www.wfmc.org/2008/XPDL2.1", "Application");
        super.serializeURIs(context);
        context.endNamespaceDecls();
        super.serializeAttributes(context);
        context.endAttributes();
        super.serializeBody(context);
        context.endElement();
    }

    public void serializeAttributes(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public void serializeURIs(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public java.lang.Class getPrimaryInterface() {
        return (ru.runa.xpdl.generated.bpmnxpdl.Application.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000"
+"\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv."
+"grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000"
+"\fcontentModelt\u0000 Lcom/sun/msv/grammar/Expression;xr\u0000\u001ecom.sun."
+"msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Lj"
+"ava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0003xppp\u0000sr\u0000\u001fcom.sun.msv.gra"
+"mmar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1q\u0000~\u0000\u0003L\u0000\u0004exp2q\u0000~\u0000\u0003xq\u0000~\u0000\u0004ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007pps"
+"q\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsr\u0000\u001dcom.sun.msv.grammar.Choic"
+"eExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\bppsq\u0000~\u0000\u0010sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000"
+"\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\u0000q\u0000~\u0000\u0014p\u0000sq\u0000~\u0000\u0010ppsr\u0000 com.sun.msv.grammar.On"
+"eOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004q\u0000~\u0000\u0014psr\u0000 com.sun.msv.grammar.Attribute"
+"Exp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004q\u0000~\u0000\u0014psr\u00002co"
+"m.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000x"
+"q\u0000~\u0000\u0004sq\u0000~\u0000\u0013\u0001q\u0000~\u0000\u001dsr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.su"
+"n.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q"
+"\u0000~\u0000\u001eq\u0000~\u0000#sr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L"
+"\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq\u0000~\u0000%xq\u0000~\u0000 t\u0000"
+"\u001egenerated.bpmnxpdl.Descriptiont\u0000+http://java.sun.com/jaxb/x"
+"jc/dummy-elementssq\u0000~\u0000\u0000q\u0000~\u0000\u0014p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0010ppsq\u0000~\u0000"
+"\u0017q\u0000~\u0000\u0014psq\u0000~\u0000\u001aq\u0000~\u0000\u0014pq\u0000~\u0000\u001dq\u0000~\u0000!q\u0000~\u0000#sq\u0000~\u0000$t\u0000\"ru.runa.xpdl.generated.bpmnxpd"
+"l.DescriptionTypeq\u0000~\u0000(sq\u0000~\u0000\u0010ppsq\u0000~\u0000\u001aq\u0000~\u0000\u0014psr\u0000\u001bcom.sun.msv.gr"
+"ammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Dataty"
+"pe;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~"
+"\u0000\u0004ppsr\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com"
+".sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.su"
+"n.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.da"
+"tatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000%L\u0000\bt"
+"ypeNameq\u0000~\u0000%L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteS"
+"paceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0005QNames"
+"r\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$NullSetExpression"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000\u0014psr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f"
+"\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000%L\u0000\fnamespaceURIq\u0000~\u0000%xpq\u0000~\u0000>q\u0000~\u0000=sq\u0000~\u0000$"
+"t\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchema-instanceq\u0000~\u0000#sq\u0000~"
+"\u0000$t\u0000\u000bDescriptiont\u0000 http://www.wfmc.org/2008/XPDL2.1q\u0000~\u0000#sq\u0000~"
+"\u0000\u0010ppsq\u0000~\u0000\u0000q\u0000~\u0000\u0014p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0010ppsq\u0000~\u0000\u0017q\u0000~\u0000\u0014psq\u0000~\u0000\u001a"
+"q\u0000~\u0000\u0014pq\u0000~\u0000\u001dq\u0000~\u0000!q\u0000~\u0000#sq\u0000~\u0000$t\u0000\"ru.runa.xpdl.generated.bpmnxpdl.Application"
+"Typeq\u0000~\u0000(sq\u0000~\u0000\u0010ppsq\u0000~\u0000\u001aq\u0000~\u0000\u0014pq\u0000~\u00006q\u0000~\u0000Fq\u0000~\u0000#sq\u0000~\u0000$t\u0000\u0004Typeq\u0000~"
+"\u0000Kq\u0000~\u0000#sq\u0000~\u0000\u0010ppsq\u0000~\u0000\u0010q\u0000~\u0000\u0014psq\u0000~\u0000\u0010q\u0000~\u0000\u0014psq\u0000~\u0000\u0010q\u0000~\u0000\u0014psq\u0000~\u0000\u0000q\u0000~"
+"\u0000\u0014p\u0000sq\u0000~\u0000\u0010ppsq\u0000~\u0000\u0017q\u0000~\u0000\u0014psq\u0000~\u0000\u001aq\u0000~\u0000\u0014pq\u0000~\u0000\u001dq\u0000~\u0000!q\u0000~\u0000#sq\u0000~\u0000$t\u0000#"
+"ru.runa.xpdl.generated.bpmnxpdl.FormalParametersq\u0000~\u0000(sq\u0000~\u0000\u0000q\u0000~\u0000\u0014p\u0000sq\u0000~\u0000\u0007p"
+"psq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0010ppsq\u0000~\u0000\u0017q\u0000~\u0000\u0014psq\u0000~\u0000\u001aq\u0000~\u0000\u0014pq\u0000~\u0000\u001dq\u0000~\u0000!q\u0000~\u0000#sq\u0000"
+"~\u0000$t\u0000\'ru.runa.xpdl.generated.bpmnxpdl.FormalParametersTypeq\u0000~\u0000(sq\u0000~\u0000\u0010ppsq"
+"\u0000~\u0000\u001aq\u0000~\u0000\u0014pq\u0000~\u00006q\u0000~\u0000Fq\u0000~\u0000#sq\u0000~\u0000$t\u0000\u0010FormalParametersq\u0000~\u0000Ksq\u0000~\u0000"
+"\u0000q\u0000~\u0000\u0014p\u0000sq\u0000~\u0000\u0010ppsq\u0000~\u0000\u0017q\u0000~\u0000\u0014psq\u0000~\u0000\u001aq\u0000~\u0000\u0014pq\u0000~\u0000\u001dq\u0000~\u0000!q\u0000~\u0000#sq\u0000~\u0000"
+"$t\u0000$ru.runa.xpdl.generated.bpmnxpdl.ExternalReferenceq\u0000~\u0000(sq\u0000~\u0000\u0000q\u0000~\u0000\u0014p\u0000sq"
+"\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0010ppsq\u0000~\u0000\u0017q\u0000~\u0000\u0014psq\u0000~\u0000\u001aq\u0000~\u0000\u0014pq\u0000~\u0000\u001dq\u0000~\u0000!q\u0000~"
+"\u0000#sq\u0000~\u0000$t\u0000(ru.runa.xpdl.generated.bpmnxpdl.ExternalReferenceTypeq\u0000~\u0000(sq\u0000~"
+"\u0000\u0010ppsq\u0000~\u0000\u001aq\u0000~\u0000\u0014pq\u0000~\u00006q\u0000~\u0000Fq\u0000~\u0000#sq\u0000~\u0000$t\u0000\u0011ExternalReferenceq\u0000~"
+"\u0000Kq\u0000~\u0000#sq\u0000~\u0000\u0010ppsq\u0000~\u0000\u0010q\u0000~\u0000\u0014psq\u0000~\u0000\u0000q\u0000~\u0000\u0014p\u0000sq\u0000~\u0000\u0010ppsq\u0000~\u0000\u0017q\u0000~\u0000\u0014p"
+"sq\u0000~\u0000\u001aq\u0000~\u0000\u0014pq\u0000~\u0000\u001dq\u0000~\u0000!q\u0000~\u0000#sq\u0000~\u0000$t\u0000%ru.runa.xpdl.generated.bpmnxpdl.Exten"
+"dedAttributesq\u0000~\u0000(sq\u0000~\u0000\u0000q\u0000~\u0000\u0014p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0010ppsq\u0000~"
+"\u0000\u0017q\u0000~\u0000\u0014psq\u0000~\u0000\u001aq\u0000~\u0000\u0014pq\u0000~\u0000\u001dq\u0000~\u0000!q\u0000~\u0000#sq\u0000~\u0000$t\u0000)ru.runa.xpdl.generated.bpmnxp"
+"dl.ExtendedAttributesTypeq\u0000~\u0000(sq\u0000~\u0000\u0010ppsq\u0000~\u0000\u001aq\u0000~\u0000\u0014pq\u0000~\u00006q\u0000~\u0000F"
+"q\u0000~\u0000#sq\u0000~\u0000$t\u0000\u0012ExtendedAttributesq\u0000~\u0000Kq\u0000~\u0000#sq\u0000~\u0000\u0010ppsq\u0000~\u0000\u0017q\u0000~\u0000"
+"\u0014psq\u0000~\u0000\u0000q\u0000~\u0000\u0014p\u0000sq\u0000~\u0000\u001appq\u0000~\u0000\u001dsr\u0000\'com.sun.msv.grammar.Differen"
+"ceNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003nc1q\u0000~\u0000\u0001L\u0000\u0003nc2q\u0000~\u0000\u0001xq\u0000~\u0000 q\u0000~\u0000!sr\u0000#c"
+"om.sun.msv.grammar.ChoiceNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003nc1q\u0000~\u0000\u0001L\u0000\u0003n"
+"c2q\u0000~\u0000\u0001xq\u0000~\u0000 sr\u0000&com.sun.msv.grammar.NamespaceNameClass\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\fnamespaceURIq\u0000~\u0000%xq\u0000~\u0000 t\u0000\u0000sq\u0000~\u0000\u009dq\u0000~\u0000Ksq\u0000~\u0000\u009dq\u0000~\u0000(q\u0000~"
+"\u0000#sq\u0000~\u0000\u001appsq\u0000~\u00003ppsr\u0000$com.sun.msv.datatype.xsd.NmtokenType\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\"com.sun.msv.datatype.xsd.TokenType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr"
+"\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysV"
+"alidxq\u0000~\u00008q\u0000~\u0000=t\u0000\u0007NMTOKENq\u0000~\u0000A\u0000q\u0000~\u0000Csq\u0000~\u0000Dq\u0000~\u0000\u00a8q\u0000~\u0000=sq\u0000~\u0000$t\u0000"
+"\u0002Idq\u0000~\u0000\u009fsq\u0000~\u0000\u0010ppsq\u0000~\u0000\u001aq\u0000~\u0000\u0014psq\u0000~\u00003q\u0000~\u0000\u0014psq\u0000~\u0000\u00a6q\u0000~\u0000=t\u0000\u0006string"
+"sr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000@\u0001q\u0000~\u0000Csq\u0000~\u0000Dq\u0000~\u0000\u00b0q\u0000~\u0000=sq\u0000~\u0000$t\u0000\u0004Nameq\u0000~\u0000\u009fq\u0000~\u0000#s"
+"q\u0000~\u0000\u0010ppsq\u0000~\u0000\u001aq\u0000~\u0000\u0014pq\u0000~\u00006q\u0000~\u0000Fq\u0000~\u0000#sq\u0000~\u0000$t\u0000\u000bApplicationq\u0000~\u0000Ks"
+"r\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet"
+"\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.s"
+"un.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB"
+"\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/ExpressionPo"
+"ol;xp\u0000\u0000\u00000\u0001pq\u0000~\u0000\u0019q\u0000~\u0000-q\u0000~\u0000Qq\u0000~\u0000_q\u0000~\u0000gq\u0000~\u0000qq\u0000~\u0000yq\u0000~\u0000Yq\u0000~\u0000\u0085q\u0000~\u0000"
+"\u008dq\u0000~\u0000\u000fq\u0000~\u0000\u0016q\u0000~\u0000,q\u0000~\u0000Pq\u0000~\u0000^q\u0000~\u0000fq\u0000~\u0000Lq\u0000~\u0000pq\u0000~\u0000xq\u0000~\u0000\u0084q\u0000~\u0000\u008cq\u0000~\u0000"
+"*q\u0000~\u0000Nq\u0000~\u0000dq\u0000~\u0000vq\u0000~\u0000\u008aq\u0000~\u0000\u000bq\u0000~\u0000\u00acq\u0000~\u0000[q\u0000~\u0000\u000eq\u0000~\u0000Zq\u0000~\u0000\fq\u0000~\u0000\nq\u0000~\u0000"
+"1q\u0000~\u0000Uq\u0000~\u0000kq\u0000~\u0000}q\u0000~\u0000\u0091q\u0000~\u0000\u00b6q\u0000~\u0000\u0011q\u0000~\u0000\u0012q\u0000~\u0000\\q\u0000~\u0000\u0082q\u0000~\u0000\u0081q\u0000~\u0000\rq\u0000~\u0000"
+"\u0095q\u0000~\u0000\u0096q\u0000~\u0000\tx"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context) {
            super(context, "----");
        }

        protected Unmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return ru.runa.xpdl.generated.bpmnxpdl.impl.ApplicationImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  1 :
                        attIdx = context.getAttribute("", "Id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  3 :
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  0 :
                        if (("Application" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 1;
                            return ;
                        }
                        break;
                }
                super.enterElement(___uri, ___local, ___qname, __atts);
                break;
            }
        }

        public void leaveElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  1 :
                        attIdx = context.getAttribute("", "Id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  3 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  2 :
                        if (("Application" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
                }
                super.leaveElement(___uri, ___local, ___qname);
                break;
            }
        }

        public void enterAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  1 :
                        if (("Id" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((ru.runa.xpdl.generated.bpmnxpdl.impl.ApplicationType2Impl) ru.runa.xpdl.generated.bpmnxpdl.impl.ApplicationImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  3 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                }
                super.enterAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void leaveAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  1 :
                        attIdx = context.getAttribute("", "Id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  3 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                }
                super.leaveAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void handleText(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                try {
                    switch (state) {
                        case  1 :
                            attIdx = context.getAttribute("", "Id");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            break;
                        case  3 :
                            revertToParentFromText(value);
                            return ;
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

    }

}
