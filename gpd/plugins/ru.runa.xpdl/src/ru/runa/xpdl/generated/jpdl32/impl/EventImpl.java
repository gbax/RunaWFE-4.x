//
// This file was ru.runa.xpdl.generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.04 at 12:24:30 PM MSD 
//


package ru.runa.xpdl.generated.jpdl32.impl;

public class EventImpl
    extends ru.runa.xpdl.generated.jpdl32.impl.EventTypeImpl
    implements ru.runa.xpdl.generated.jpdl32.Event, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, ru.runa.xpdl.generated.jpdl32.impl.runtime.UnmarshallableObject, ru.runa.xpdl.generated.jpdl32.impl.runtime.XMLSerializable, ru.runa.xpdl.generated.jpdl32.impl.runtime.ValidatableObject
{

    public final static java.lang.Class version = (ru.runa.xpdl.generated.jpdl32.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (ru.runa.xpdl.generated.jpdl32.Event.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "urn:jbpm.org:jpdl-3.2";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "event";
    }

    public ru.runa.xpdl.generated.jpdl32.impl.runtime.UnmarshallingEventHandler createUnmarshaller(ru.runa.xpdl.generated.jpdl32.impl.runtime.UnmarshallingContext context) {
        return new ru.runa.xpdl.generated.jpdl32.impl.EventImpl.Unmarshaller(context);
    }

    public void serializeBody(ru.runa.xpdl.generated.jpdl32.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("urn:jbpm.org:jpdl-3.2", "event");
        super.serializeURIs(context);
        context.endNamespaceDecls();
        super.serializeAttributes(context);
        context.endAttributes();
        super.serializeBody(context);
        context.endElement();
    }

    public void serializeAttributes(ru.runa.xpdl.generated.jpdl32.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public void serializeURIs(ru.runa.xpdl.generated.jpdl32.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public java.lang.Class getPrimaryInterface() {
        return (ru.runa.xpdl.generated.jpdl32.Event.class);
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
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1q\u0000~\u0000\u0003L\u0000\u0004exp2q\u0000~\u0000\u0003xq\u0000~\u0000\u0004ppsq\u0000~\u0000\u0007ppsr\u0000\u001dcom.s"
+"un.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\bppsr\u0000 com.sun.msv.g"
+"rammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryE"
+"xp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002"
+"\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\u000bq\u0000~\u0000\u0011psq\u0000~\u0000\u000bq\u0000~\u0000\u0011psq\u0000~\u0000\u000bq\u0000~\u0000\u0011psq\u0000~\u0000\u000bq\u0000~\u0000"
+"\u0011psq\u0000~\u0000\u000bq\u0000~\u0000\u0011psq\u0000~\u0000\u000bq\u0000~\u0000\u0011psq\u0000~\u0000\u000bq\u0000~\u0000\u0011psq\u0000~\u0000\u000bq\u0000~\u0000\u0011psq\u0000~\u0000\u000bq\u0000~\u0000"
+"\u0011psq\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u000bppsq\u0000~\u0000\rq\u0000~\u0000\u0011psr\u0000 com.sun.msv.grammar.A"
+"ttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004q\u0000~\u0000"
+"\u0011psr\u00002com.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000\u0010\u0001psr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000co"
+"m.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000"
+"~\u0000\u0004q\u0000~\u0000\"psr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L"
+"\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq\u0000~\u0000)xq\u0000~\u0000$t\u0000"
+"\u0017generated.jpdl32.Actiont\u0000+http://java.sun.com/jaxb/xjc/dumm"
+"y-elementssq\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u000bppsq\u0000~\u0000\rq\u0000~\u0000\u0011p"
+"sq\u0000~\u0000\u001eq\u0000~\u0000\u0011pq\u0000~\u0000!q\u0000~\u0000%q\u0000~\u0000\'sq\u0000~\u0000(t\u0000\u001bgenerated.jpdl32.ActionT"
+"ypeq\u0000~\u0000,sq\u0000~\u0000\u000bppsq\u0000~\u0000\u001eq\u0000~\u0000\u0011psr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000"
+"~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0004ppsr\u0000\"com.su"
+"n.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datat"
+"ype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype"
+".xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSD"
+"atatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000)L\u0000\btypeNameq\u0000~\u0000)L\u0000"
+"\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;"
+"xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0005QNamesr\u00005com.sun.msv"
+".datatype.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com"
+".sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000co"
+"m.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000"
+"~\u0000\u0004q\u0000~\u0000\u0011psr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalN"
+"ameq\u0000~\u0000)L\u0000\fnamespaceURIq\u0000~\u0000)xpq\u0000~\u0000Bq\u0000~\u0000Asq\u0000~\u0000(t\u0000\u0004typet\u0000)http"
+"://www.w3.org/2001/XMLSchema-instanceq\u0000~\u0000\'sq\u0000~\u0000(t\u0000\u0006actiont\u0000\u0015"
+"urn:jbpm.org:jpdl-3.2sq\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u000bppsq\u0000~\u0000\rq\u0000~\u0000\u0011psq\u0000~\u0000\u001e"
+"q\u0000~\u0000\u0011pq\u0000~\u0000!q\u0000~\u0000%q\u0000~\u0000\'sq\u0000~\u0000(t\u0000\u0017generated.jpdl32.Scriptq\u0000~\u0000,sq"
+"\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u000bppsq\u0000~\u0000\rq\u0000~\u0000\u0011psq\u0000~\u0000\u001eq\u0000~\u0000\u0011p"
+"q\u0000~\u0000!q\u0000~\u0000%q\u0000~\u0000\'sq\u0000~\u0000(t\u0000\u001bgenerated.jpdl32.ScriptTypeq\u0000~\u0000,sq\u0000~"
+"\u0000\u000bppsq\u0000~\u0000\u001eq\u0000~\u0000\u0011pq\u0000~\u0000:q\u0000~\u0000Jq\u0000~\u0000\'sq\u0000~\u0000(t\u0000\u0006scriptq\u0000~\u0000Osq\u0000~\u0000\u0000q\u0000~"
+"\u0000\u0011p\u0000sq\u0000~\u0000\u000bppsq\u0000~\u0000\rq\u0000~\u0000\u0011psq\u0000~\u0000\u001eq\u0000~\u0000\u0011pq\u0000~\u0000!q\u0000~\u0000%q\u0000~\u0000\'sq\u0000~\u0000(t\u0000\u001c"
+"ru.runa.xpdl.generated.jpdl32.CreateTimerq\u0000~\u0000,sq\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000"
+"pp\u0000sq\u0000~\u0000\u000bppsq\u0000~\u0000\rq\u0000~\u0000\u0011psq\u0000~\u0000\u001eq\u0000~\u0000\u0011pq\u0000~\u0000!q\u0000~\u0000%q\u0000~\u0000\'sq\u0000~\u0000(t\u0000 g"
+"enerated.jpdl32.CreateTimerTypeq\u0000~\u0000,sq\u0000~\u0000\u000bppsq\u0000~\u0000\u001eq\u0000~\u0000\u0011pq\u0000~\u0000"
+":q\u0000~\u0000Jq\u0000~\u0000\'sq\u0000~\u0000(t\u0000\fcreate-timerq\u0000~\u0000Osq\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u000bppsq"
+"\u0000~\u0000\rq\u0000~\u0000\u0011psq\u0000~\u0000\u001eq\u0000~\u0000\u0011pq\u0000~\u0000!q\u0000~\u0000%q\u0000~\u0000\'sq\u0000~\u0000(t\u0000\u001cgenerated.jpdl"
+"32.CancelTimerq\u0000~\u0000,sq\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u000bppsq\u0000"
+"~\u0000\rq\u0000~\u0000\u0011psq\u0000~\u0000\u001eq\u0000~\u0000\u0011pq\u0000~\u0000!q\u0000~\u0000%q\u0000~\u0000\'sq\u0000~\u0000(t\u0000 ru.runa.xpdl.generated.jpdl3"
+"2.CancelTimerTypeq\u0000~\u0000,sq\u0000~\u0000\u000bppsq\u0000~\u0000\u001eq\u0000~\u0000\u0011pq\u0000~\u0000:q\u0000~\u0000Jq\u0000~\u0000\'sq\u0000"
+"~\u0000(t\u0000\fcancel-timerq\u0000~\u0000Osq\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u000bppsq\u0000~\u0000\rq\u0000~\u0000\u0011psq\u0000~"
+"\u0000\u001eq\u0000~\u0000\u0011pq\u0000~\u0000!q\u0000~\u0000%q\u0000~\u0000\'sq\u0000~\u0000(t\u0000\u0015generated.jpdl32.Mailq\u0000~\u0000,sq"
+"\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u000bppsq\u0000~\u0000\rq\u0000~\u0000\u0011psq\u0000~\u0000\u001eq\u0000~\u0000\u0011p"
+"q\u0000~\u0000!q\u0000~\u0000%q\u0000~\u0000\'sq\u0000~\u0000(t\u0000\u0019generated.jpdl32.MailTypeq\u0000~\u0000,sq\u0000~\u0000\u000b"
+"ppsq\u0000~\u0000\u001eq\u0000~\u0000\u0011pq\u0000~\u0000:q\u0000~\u0000Jq\u0000~\u0000\'sq\u0000~\u0000(t\u0000\u0004mailq\u0000~\u0000Oq\u0000~\u0000\'sq\u0000~\u0000\u001epp"
+"sq\u0000~\u00007ppsr\u0000\"com.sun.msv.datatype.xsd.UnionType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001[\u0000\u000b"
+"memberTypest\u0000*[Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;xq\u0000~"
+"\u0000=q\u0000~\u0000Opq\u0000~\u0000Eur\u0000*[Lcom.sun.msv.datatype.xsd.XSDatatypeImpl;H"
+"\u001c\u00ad{pzHw\u0002\u0000\u0000xp\u0000\u0000\u0000\u0002sr\u0000\'com.sun.msv.datatype.xsd.FinalComponent\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\nfinalValuexr\u0000\u001ecom.sun.msv.datatype.xsd.Proxy\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bbaseTypet\u0000)Lcom/sun/msv/datatype/xsd/XSDatatypeIm"
+"pl;xq\u0000~\u0000>q\u0000~\u0000Opsr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProces"
+"sor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000Dsr\u0000#com.sun.msv.datatype.xsd.St"
+"ringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxq\u0000~\u0000<q\u0000~\u0000At\u0000\u0006stringq\u0000~\u0000\u00a4"
+"\u0001\u0000\u0000\u0000\u0000sr\u0000)com.sun.msv.datatype.xsd.EnumerationFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0001L\u0000\u0006valuest\u0000\u000fLjava/util/Set;xr\u00009com.sun.msv.datatype.xsd.Dat"
+"aTypeWithValueConstraintFacet\"\u00a7Ro\u00ca\u00c7\u008aT\u0002\u0000\u0000xr\u0000*com.sun.msv.data"
+"type.xsd.DataTypeWithFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0005Z\u0000\fisFacetFixedZ\u0000\u0012needV"
+"alueCheckFlagL\u0000\bbaseTypeq\u0000~\u0000\u00a1L\u0000\fconcreteTypet\u0000\'Lcom/sun/msv/"
+"datatype/xsd/ConcreteType;L\u0000\tfacetNameq\u0000~\u0000)xq\u0000~\u0000>q\u0000~\u0000Opq\u0000~\u0000\u00a4"
+"\u0000\u0000q\u0000~\u0000\u00a6q\u0000~\u0000\u00a6t\u0000\u000benumerationsr\u0000\u0011java.util.HashSet\u00baD\u0085\u0095\u0096\u00b8\u00b74\u0003\u0000\u0000xp"
+"w\f\u0000\u0000\u0000 ?@\u0000\u0000\u0000\u0000\u0000\u000ft\u0000\nnode-entert\u0000\u0010superstate-leavet\u0000\u000esubprocess-"
+"endt\u0000\nnode-leavet\u0000\u0010superstate-entert\u0000\rbefore-signalt\u0000\rproces"
+"s-startt\u0000\ftimer-createt\u0000\u000bprocess-endt\u0000\btask-endt\u0000\ntask-start"
+"t\u0000\u0012subprocess-createdt\u0000\fafter-signalt\u0000\u000btask-assignt\u0000\u000btask-cr"
+"eatexq\u0000~\u0000Gpsq\u0000~\u0000(t\u0000\u0004typet\u0000\u0000sq\u0000~\u0000\u000bppsq\u0000~\u0000\u001eq\u0000~\u0000\u0011pq\u0000~\u0000:q\u0000~\u0000Jq\u0000~"
+"\u0000\'sq\u0000~\u0000(t\u0000\u0005eventq\u0000~\u0000Osr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$"
+"ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHa"
+"sh\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/ms"
+"v/grammar/ExpressionPool;xp\u0000\u0000\u0000,\u0001pq\u0000~\u0000\tq\u0000~\u0000\u0015q\u0000~\u0000\u0013q\u0000~\u0000\u0019q\u0000~\u0000.q\u0000"
+"~\u0000Wq\u0000~\u0000iq\u0000~\u0000{q\u0000~\u0000\u0017q\u0000~\u0000\u008dq\u0000~\u0000\u001aq\u0000~\u00005q\u0000~\u0000^q\u0000~\u0000\u0018q\u0000~\u0000pq\u0000~\u0000\u0082q\u0000~\u0000\u0014q\u0000"
+"~\u0000\u0094q\u0000~\u0000\nq\u0000~\u0000\u00c3q\u0000~\u0000\u001dq\u0000~\u00001q\u0000~\u0000Rq\u0000~\u0000Zq\u0000~\u0000dq\u0000~\u0000lq\u0000~\u0000vq\u0000~\u0000~q\u0000~\u0000\u0088q\u0000"
+"~\u0000\u0090q\u0000~\u0000\u000fq\u0000~\u0000\u001cq\u0000~\u00000q\u0000~\u0000Qq\u0000~\u0000Yq\u0000~\u0000cq\u0000~\u0000kq\u0000~\u0000uq\u0000~\u0000}q\u0000~\u0000\u0087q\u0000~\u0000\u008fq\u0000"
+"~\u0000\u0012q\u0000~\u0000\fq\u0000~\u0000\u0016x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends ru.runa.xpdl.generated.jpdl32.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(ru.runa.xpdl.generated.jpdl32.impl.runtime.UnmarshallingContext context) {
            super(context, "----");
        }

        protected Unmarshaller(ru.runa.xpdl.generated.jpdl32.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return ru.runa.xpdl.generated.jpdl32.impl.EventImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  1 :
                        attIdx = context.getAttribute("", "type");
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
                        if (("event" == ___local)&&("urn:jbpm.org:jpdl-3.2" == ___uri)) {
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
                        attIdx = context.getAttribute("", "type");
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
                        if (("event" == ___local)&&("urn:jbpm.org:jpdl-3.2" == ___uri)) {
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
                        if (("type" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((ru.runa.xpdl.generated.jpdl32.impl.EventTypeImpl) ru.runa.xpdl.generated.jpdl32.impl.EventImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
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
                        attIdx = context.getAttribute("", "type");
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
                            attIdx = context.getAttribute("", "type");
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
