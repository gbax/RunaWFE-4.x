//
// This file was ru.runa.xpdl.generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.23 at 09:51:37 PM MSD 
//


package ru.runa.xpdl.generated.bpmnxpdl.impl;

public class TC1025DeadlineTypeImpl implements ru.runa.xpdl.generated.bpmnxpdl.TC1025DeadlineType, com.sun.xml.bind.JAXBObject, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallableObject, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializable, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.ValidatableObject
{

    protected java.lang.String _Execution;
    protected ru.runa.xpdl.generated.bpmnxpdl.AnyType _DeadlineCondition;
    protected ru.runa.xpdl.generated.bpmnxpdl.AnyType _ExceptionName;
    public final static java.lang.Class version = (ru.runa.xpdl.generated.bpmnxpdl.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (ru.runa.xpdl.generated.bpmnxpdl.TC1025DeadlineType.class);
    }

    public java.lang.String getExecution() {
        return _Execution;
    }

    public void setExecution(java.lang.String value) {
        _Execution = value;
    }

    public ru.runa.xpdl.generated.bpmnxpdl.AnyType getDeadlineCondition() {
        return _DeadlineCondition;
    }

    public void setDeadlineCondition(ru.runa.xpdl.generated.bpmnxpdl.AnyType value) {
        _DeadlineCondition = value;
    }

    public ru.runa.xpdl.generated.bpmnxpdl.AnyType getExceptionName() {
        return _ExceptionName;
    }

    public void setExceptionName(ru.runa.xpdl.generated.bpmnxpdl.AnyType value) {
        _ExceptionName = value;
    }

    public ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingEventHandler createUnmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context) {
        return new ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025DeadlineTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("http://www.wfmc.org/2002/XPDL1.0", "DeadlineCondition");
        context.childAsURIs(((com.sun.xml.bind.JAXBObject) _DeadlineCondition), "DeadlineCondition");
        context.endNamespaceDecls();
        context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _DeadlineCondition), "DeadlineCondition");
        context.endAttributes();
        context.childAsBody(((com.sun.xml.bind.JAXBObject) _DeadlineCondition), "DeadlineCondition");
        context.endElement();
        context.startElement("http://www.wfmc.org/2002/XPDL1.0", "ExceptionName");
        context.childAsURIs(((com.sun.xml.bind.JAXBObject) _ExceptionName), "ExceptionName");
        context.endNamespaceDecls();
        context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _ExceptionName), "ExceptionName");
        context.endAttributes();
        context.childAsBody(((com.sun.xml.bind.JAXBObject) _ExceptionName), "ExceptionName");
        context.endElement();
    }

    public void serializeAttributes(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        if (_Execution!= null) {
            context.startAttribute("", "Execution");
            try {
                context.text(((java.lang.String) _Execution), "Execution");
            } catch (java.lang.Exception e) {
                ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttribute();
        }
    }

    public void serializeURIs(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public java.lang.Class getPrimaryInterface() {
        return (ru.runa.xpdl.generated.bpmnxpdl.TC1025DeadlineType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsr\u0000\'com.sun.msv.grammar.trex.Ele"
+"mentPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/Na"
+"meClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aigno"
+"reUndeclaredAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003pp\u0000sq\u0000~\u0000\u0000pps"
+"q\u0000~\u0000\u0007pp\u0000sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001pp"
+"sr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.m"
+"sv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003sr\u0000\u0011java.lang"
+".Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000 com.sun.msv.grammar.Attri"
+"buteExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\bxq\u0000~\u0000\u0003q\u0000~\u0000\u0013psr"
+"\u00002com.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\u0012\u0001q\u0000~\u0000\u0017sr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000co"
+"m.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000"
+"~\u0000\u0003q\u0000~\u0000\u0018q\u0000~\u0000\u001dsr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq\u0000~\u0000\u001fxq\u0000~"
+"\u0000\u001at\u0000\u001agenerated.bpmnxpdl.AnyTypet\u0000+http://java.sun.com/jaxb/x"
+"jc/dummy-elementssq\u0000~\u0000\rppsq\u0000~\u0000\u0014q\u0000~\u0000\u0013psr\u0000\u001bcom.sun.msv.grammar"
+".DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000"
+"\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0003pps"
+"r\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun."
+"msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv"
+".datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatyp"
+"e.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000\u001fL\u0000\btypeNa"
+"meq\u0000~\u0000\u001fL\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceP"
+"rocessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0005QNamesr\u00005co"
+"m.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002"
+"\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0000xpsr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u0013psr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002"
+"L\u0000\tlocalNameq\u0000~\u0000\u001fL\u0000\fnamespaceURIq\u0000~\u0000\u001fxpq\u0000~\u00000q\u0000~\u0000/sq\u0000~\u0000\u001et\u0000\u0004ty"
+"pet\u0000)http://www.w3.org/2001/XMLSchema-instanceq\u0000~\u0000\u001dsq\u0000~\u0000\u001et\u0000\u0011"
+"DeadlineConditiont\u0000 http://www.wfmc.org/2002/XPDL1.0sq\u0000~\u0000\u0007pp"
+"\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0007pp\u0000sq\u0000~\u0000\rppsq\u0000~\u0000\u000fq\u0000~\u0000\u0013psq\u0000~\u0000\u0014q\u0000~\u0000\u0013pq\u0000~\u0000\u0017q\u0000~\u0000\u001b"
+"q\u0000~\u0000\u001dsq\u0000~\u0000\u001eq\u0000~\u0000!q\u0000~\u0000\"sq\u0000~\u0000\rppsq\u0000~\u0000\u0014q\u0000~\u0000\u0013pq\u0000~\u0000(q\u0000~\u00008q\u0000~\u0000\u001dsq\u0000~"
+"\u0000\u001et\u0000\rExceptionNameq\u0000~\u0000=sq\u0000~\u0000\rppsq\u0000~\u0000\u0014q\u0000~\u0000\u0013psq\u0000~\u0000%ppsr\u0000)com.s"
+"un.msv.datatype.xsd.EnumerationFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0006valuest\u0000\u000fL"
+"java/util/Set;xr\u00009com.sun.msv.datatype.xsd.DataTypeWithValue"
+"ConstraintFacet\"\u00a7Ro\u00ca\u00c7\u008aT\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.DataT"
+"ypeWithFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0005Z\u0000\fisFacetFixedZ\u0000\u0012needValueCheckFlagL"
+"\u0000\bbaseTypet\u0000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;L\u0000\fcon"
+"creteTypet\u0000\'Lcom/sun/msv/datatype/xsd/ConcreteType;L\u0000\tfacetN"
+"ameq\u0000~\u0000\u001fxq\u0000~\u0000,q\u0000~\u0000=pq\u0000~\u00003\u0000\u0000sr\u0000$com.sun.msv.datatype.xsd.Nmto"
+"kenType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\"com.sun.msv.datatype.xsd.TokenType\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\r"
+"isAlwaysValidxq\u0000~\u0000*q\u0000~\u0000/t\u0000\u0007NMTOKENq\u0000~\u00003\u0000q\u0000~\u0000Vt\u0000\u000benumerations"
+"r\u0000\u0011java.util.HashSet\u00baD\u0085\u0095\u0096\u00b8\u00b74\u0003\u0000\u0000xpw\f\u0000\u0000\u0000\u0010?@\u0000\u0000\u0000\u0000\u0000\u0002t\u0000\u0007ASYNCHRt\u0000\u0006"
+"SYNCHRxq\u0000~\u00005sq\u0000~\u00006t\u0000\u000fNMTOKEN-derivedq\u0000~\u0000=sq\u0000~\u0000\u001et\u0000\tExecutiont"
+"\u0000\u0000q\u0000~\u0000\u001dsr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\be"
+"xpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xps"
+"r\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I"
+"\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/Expr"
+"essionPool;xp\u0000\u0000\u0000\u000b\u0001pq\u0000~\u0000\u0011q\u0000~\u0000Bq\u0000~\u0000\u0006q\u0000~\u0000\u000eq\u0000~\u0000Aq\u0000~\u0000\u000bq\u0000~\u0000?q\u0000~\u0000Iq"
+"\u0000~\u0000#q\u0000~\u0000Eq\u0000~\u0000\u0005x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context) {
            super(context, "----------");
        }

        protected Unmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025DeadlineTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  6 :
                        if (("ExceptionName" == ___local)&&("http://www.wfmc.org/2002/XPDL1.0" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 7;
                            return ;
                        }
                        break;
                    case  4 :
                        if (true) {
                            _DeadlineCondition = ((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        _DeadlineCondition = ((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                        return ;
                    case  7 :
                        if (true) {
                            _ExceptionName = ((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl.class), 8, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        _ExceptionName = ((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl.class), 8, ___uri, ___local, ___qname, __atts));
                        return ;
                    case  3 :
                        if (("DeadlineCondition" == ___local)&&("http://www.wfmc.org/2002/XPDL1.0" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 4;
                            return ;
                        }
                        break;
                    case  9 :
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  0 :
                        attIdx = context.getAttribute("", "Execution");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText1(v);
                            continue outer;
                        }
                        state = 3;
                        continue outer;
                }
                super.enterElement(___uri, ___local, ___qname, __atts);
                break;
            }
        }

        private void eatText1(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Execution = com.sun.xml.bind.WhiteSpaceProcessor.collapse(value);
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        public void leaveElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  4 :
                        _DeadlineCondition = ((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl) spawnChildFromLeaveElement((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl.class), 5, ___uri, ___local, ___qname));
                        return ;
                    case  7 :
                        _ExceptionName = ((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl) spawnChildFromLeaveElement((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl.class), 8, ___uri, ___local, ___qname));
                        return ;
                    case  9 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  0 :
                        attIdx = context.getAttribute("", "Execution");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText1(v);
                            continue outer;
                        }
                        state = 3;
                        continue outer;
                    case  5 :
                        if (("DeadlineCondition" == ___local)&&("http://www.wfmc.org/2002/XPDL1.0" == ___uri)) {
                            context.popAttributes();
                            state = 6;
                            return ;
                        }
                        break;
                    case  8 :
                        if (("ExceptionName" == ___local)&&("http://www.wfmc.org/2002/XPDL1.0" == ___uri)) {
                            context.popAttributes();
                            state = 9;
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
                    case  4 :
                        _DeadlineCondition = ((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl) spawnChildFromEnterAttribute((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl.class), 5, ___uri, ___local, ___qname));
                        return ;
                    case  7 :
                        _ExceptionName = ((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl) spawnChildFromEnterAttribute((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl.class), 8, ___uri, ___local, ___qname));
                        return ;
                    case  9 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  0 :
                        if (("Execution" == ___local)&&("" == ___uri)) {
                            state = 1;
                            return ;
                        }
                        state = 3;
                        continue outer;
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
                    case  4 :
                        _DeadlineCondition = ((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl) spawnChildFromLeaveAttribute((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl.class), 5, ___uri, ___local, ___qname));
                        return ;
                    case  7 :
                        _ExceptionName = ((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl) spawnChildFromLeaveAttribute((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl.class), 8, ___uri, ___local, ___qname));
                        return ;
                    case  9 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  2 :
                        if (("Execution" == ___local)&&("" == ___uri)) {
                            state = 3;
                            return ;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "Execution");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText1(v);
                            continue outer;
                        }
                        state = 3;
                        continue outer;
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
                        case  4 :
                            _DeadlineCondition = ((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl) spawnChildFromText((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl.class), 5, value));
                            return ;
                        case  7 :
                            _ExceptionName = ((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl) spawnChildFromText((ru.runa.xpdl.generated.bpmnxpdl.impl.AnyTypeImpl.class), 8, value));
                            return ;
                        case  9 :
                            revertToParentFromText(value);
                            return ;
                        case  1 :
                            state = 2;
                            eatText1(value);
                            return ;
                        case  0 :
                            attIdx = context.getAttribute("", "Execution");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 3;
                                eatText1(v);
                                continue outer;
                            }
                            state = 3;
                            continue outer;
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

    }

}
