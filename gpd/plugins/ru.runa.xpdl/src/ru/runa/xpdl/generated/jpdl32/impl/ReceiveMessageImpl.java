//
// This file was ru.runa.xpdl.generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.04 at 12:24:30 PM MSD 
//


package ru.runa.xpdl.generated.jpdl32.impl;

public class ReceiveMessageImpl
    extends ru.runa.xpdl.generated.jpdl32.impl.ReceiveMessageTypeImpl
    implements ru.runa.xpdl.generated.jpdl32.ReceiveMessage, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, ru.runa.xpdl.generated.jpdl32.impl.runtime.UnmarshallableObject, ru.runa.xpdl.generated.jpdl32.impl.runtime.XMLSerializable, ru.runa.xpdl.generated.jpdl32.impl.runtime.ValidatableObject
{

    public final static java.lang.Class version = (ru.runa.xpdl.generated.jpdl32.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (ru.runa.xpdl.generated.jpdl32.ReceiveMessage.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "urn:jbpm.org:jpdl-3.2";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "receive-message";
    }

    public ru.runa.xpdl.generated.jpdl32.impl.runtime.UnmarshallingEventHandler createUnmarshaller(ru.runa.xpdl.generated.jpdl32.impl.runtime.UnmarshallingContext context) {
        return new ru.runa.xpdl.generated.jpdl32.impl.ReceiveMessageImpl.Unmarshaller(context);
    }

    public void serializeBody(ru.runa.xpdl.generated.jpdl32.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("urn:jbpm.org:jpdl-3.2", "receive-message");
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
        return (ru.runa.xpdl.generated.jpdl32.ReceiveMessage.class);
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
+"\u0011psq\u0000~\u0000\u000bq\u0000~\u0000\u0011psq\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u000bppsq\u0000~\u0000\rq\u0000~\u0000\u0011psr\u0000 com.sun.m"
+"sv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq\u0000~"
+"\u0000\u0001xq\u0000~\u0000\u0004q\u0000~\u0000\u0011psr\u00002com.sun.msv.grammar.Expression$AnyStringEx"
+"pression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000\u0010\u0001psr\u0000 com.sun.msv.grammar.Any"
+"NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000\u001epsr\u0000#com.sun.msv.grammar.SimpleNameClass"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq"
+"\u0000~\u0000%xq\u0000~\u0000 t\u0000\u0019generated.jpdl32.Variablet\u0000+http://java.sun.com"
+"/jaxb/xjc/dummy-elementssq\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u000bppsq\u0000~\u0000\rq\u0000~\u0000\u0011psq\u0000"
+"~\u0000\u001aq\u0000~\u0000\u0011pq\u0000~\u0000\u001dq\u0000~\u0000!q\u0000~\u0000#sq\u0000~\u0000$t\u0000\u001cgenerated.jpdl32.Descriptio"
+"nq\u0000~\u0000(sq\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u000bppsq\u0000~\u0000\rq\u0000~\u0000\u0011psq\u0000~\u0000\u001aq\u0000~\u0000\u0011pq\u0000~\u0000\u001dq\u0000~\u0000"
+"!q\u0000~\u0000#sq\u0000~\u0000$t\u0000\u0016generated.jpdl32.Eventq\u0000~\u0000(sq\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000"
+"\u000bppsq\u0000~\u0000\rq\u0000~\u0000\u0011psq\u0000~\u0000\u001aq\u0000~\u0000\u0011pq\u0000~\u0000\u001dq\u0000~\u0000!q\u0000~\u0000#sq\u0000~\u0000$t\u0000!ru.runa.xpdl.generated"
+".jpdl32.ExceptionHandlerq\u0000~\u0000(sq\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u000bppsq\u0000~\u0000\rq\u0000~\u0000"
+"\u0011psq\u0000~\u0000\u001aq\u0000~\u0000\u0011pq\u0000~\u0000\u001dq\u0000~\u0000!q\u0000~\u0000#sq\u0000~\u0000$t\u0000\u0016generated.jpdl32.Timer"
+"q\u0000~\u0000(sq\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u000bppsq\u0000~\u0000\rq\u0000~\u0000\u0011psq\u0000~\u0000\u001aq\u0000~\u0000\u0011pq\u0000~\u0000\u001dq\u0000~\u0000!"
+"q\u0000~\u0000#sq\u0000~\u0000$t\u0000\u001bgenerated.jpdl32.Transitionq\u0000~\u0000(q\u0000~\u0000#sq\u0000~\u0000\u001apps"
+"r\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxn"
+"g/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/msv/uti"
+"l/StringPair;xq\u0000~\u0000\u0004q\u0000~\u0000\u0011psr\u0000#com.sun.msv.datatype.xsd.String"
+"Type\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun.msv.datatype.xsd."
+"BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.Con"
+"creteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeI"
+"mpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000%L\u0000\btypeNameq\u0000~\u0000%L\u0000\nwhiteSp"
+"acet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 htt"
+"p://www.w3.org/2001/XMLSchemat\u0000\u0006stringsr\u00005com.sun.msv.dataty"
+"pe.xsd.WhiteSpaceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.ms"
+"v.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com.sun."
+"msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~"
+"\u0000\u0011psr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~"
+"\u0000%L\u0000\fnamespaceURIq\u0000~\u0000%xpq\u0000~\u0000Sq\u0000~\u0000Rsq\u0000~\u0000$t\u0000\u0004namet\u0000\u0000sq\u0000~\u0000\u000bppsq"
+"\u0000~\u0000\u001aq\u0000~\u0000\u0011psq\u0000~\u0000Hppsr\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000Mq\u0000~\u0000Rt\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd.White"
+"SpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000Uq\u0000~\u0000Xsq\u0000~\u0000Yq\u0000~\u0000cq\u0000~\u0000"
+"Rsq\u0000~\u0000$t\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchema-instanceq\u0000"
+"~\u0000#sq\u0000~\u0000$t\u0000\u000freceive-messaget\u0000\u0015urn:jbpm.org:jpdl-3.2sr\u0000\"com.s"
+"un.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/s"
+"un/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.g"
+"rammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstream"
+"VersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000"
+"\u0016\u0001pq\u0000~\u0000\nq\u0000~\u0000\u0016q\u0000~\u0000^q\u0000~\u0000\fq\u0000~\u0000\tq\u0000~\u0000\u0012q\u0000~\u0000\u0013q\u0000~\u0000\u0019q\u0000~\u0000+q\u0000~\u00001q\u0000~\u00007q\u0000"
+"~\u0000=q\u0000~\u0000Cq\u0000~\u0000\u000fq\u0000~\u0000\u0018q\u0000~\u0000*q\u0000~\u00000q\u0000~\u00006q\u0000~\u0000<q\u0000~\u0000Bq\u0000~\u0000\u0014q\u0000~\u0000\u0015x"));
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
            return ru.runa.xpdl.generated.jpdl32.impl.ReceiveMessageImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  3 :
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  1 :
                        attIdx = context.getAttribute("", "name");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  0 :
                        if (("receive-message" == ___local)&&("urn:jbpm.org:jpdl-3.2" == ___uri)) {
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
                    case  3 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  1 :
                        attIdx = context.getAttribute("", "name");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  2 :
                        if (("receive-message" == ___local)&&("urn:jbpm.org:jpdl-3.2" == ___uri)) {
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
                    case  3 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  1 :
                        if (("name" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((ru.runa.xpdl.generated.jpdl32.impl.ReceiveMessageTypeImpl) ru.runa.xpdl.generated.jpdl32.impl.ReceiveMessageImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        break;
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
                    case  3 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  1 :
                        attIdx = context.getAttribute("", "name");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
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
                        case  3 :
                            revertToParentFromText(value);
                            return ;
                        case  1 :
                            attIdx = context.getAttribute("", "name");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            break;
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

    }

}
