//
// This file was ru.runa.xpdl.generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.23 at 09:51:37 PM MSD 
//


package ru.runa.xpdl.generated.bpmnxpdl.impl;

public class TC1025ImplementationTypeImpl implements ru.runa.xpdl.generated.bpmnxpdl.TC1025ImplementationType, com.sun.xml.bind.JAXBObject, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallableObject, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializable, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.ValidatableObject
{

    protected ru.runa.xpdl.generated.bpmnxpdl.TC1025SubFlowType _TC1025SubFlow;
    protected com.sun.xml.bind.util.ListImpl _Tool;
    protected ru.runa.xpdl.generated.bpmnxpdl.TC1025NoType _TC1025No;
    public final static java.lang.Class version = (ru.runa.xpdl.generated.bpmnxpdl.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (ru.runa.xpdl.generated.bpmnxpdl.TC1025ImplementationType.class);
    }

    public ru.runa.xpdl.generated.bpmnxpdl.TC1025SubFlowType getTC1025SubFlow() {
        return _TC1025SubFlow;
    }

    public void setTC1025SubFlow(ru.runa.xpdl.generated.bpmnxpdl.TC1025SubFlowType value) {
        _TC1025SubFlow = value;
    }

    protected com.sun.xml.bind.util.ListImpl _getTool() {
        if (_Tool == null) {
            _Tool = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _Tool;
    }

    public java.util.List getTool() {
        return _getTool();
    }

    public ru.runa.xpdl.generated.bpmnxpdl.TC1025NoType getTC1025No() {
        return _TC1025No;
    }

    public void setTC1025No(ru.runa.xpdl.generated.bpmnxpdl.TC1025NoType value) {
        _TC1025No = value;
    }

    public ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingEventHandler createUnmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context) {
        return new ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025ImplementationTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_Tool == null)? 0 :_Tool.size());
        if (((((_Tool == null)? 0 :_Tool.size()) == 0)&&(_TC1025SubFlow == null))&&(_TC1025No!= null)) {
            if (_TC1025No instanceof javax.xml.bind.Element) {
                context.childAsBody(((com.sun.xml.bind.JAXBObject) _TC1025No), "TC1025No");
            } else {
                context.startElement("http://www.wfmc.org/2002/XPDL1.0", "TC1025No");
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) _TC1025No), "TC1025No");
                context.endNamespaceDecls();
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _TC1025No), "TC1025No");
                context.endAttributes();
                context.childAsBody(((com.sun.xml.bind.JAXBObject) _TC1025No), "TC1025No");
                context.endElement();
            }
        } else {
            if (((((_Tool == null)? 0 :_Tool.size())>= 1)&&(_TC1025SubFlow == null))&&(_TC1025No == null)) {
                while (idx2 != len2) {
                    if (_Tool.get(idx2) instanceof javax.xml.bind.Element) {
                        context.childAsBody(((com.sun.xml.bind.JAXBObject) _Tool.get(idx2 ++)), "Tool");
                    } else {
                        context.startElement("http://www.wfmc.org/2002/XPDL1.0", "Tool");
                        int idx_2 = idx2;
                        context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Tool.get(idx_2 ++)), "Tool");
                        context.endNamespaceDecls();
                        int idx_3 = idx2;
                        context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Tool.get(idx_3 ++)), "Tool");
                        context.endAttributes();
                        context.childAsBody(((com.sun.xml.bind.JAXBObject) _Tool.get(idx2 ++)), "Tool");
                        context.endElement();
                    }
                }
            } else {
                if (((((_Tool == null)? 0 :_Tool.size()) == 0)&&(_TC1025SubFlow!= null))&&(_TC1025No == null)) {
                    if (_TC1025SubFlow instanceof javax.xml.bind.Element) {
                        context.childAsBody(((com.sun.xml.bind.JAXBObject) _TC1025SubFlow), "TC1025SubFlow");
                    } else {
                        context.startElement("http://www.wfmc.org/2002/XPDL1.0", "TC1025SubFlow");
                        context.childAsURIs(((com.sun.xml.bind.JAXBObject) _TC1025SubFlow), "TC1025SubFlow");
                        context.endNamespaceDecls();
                        context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _TC1025SubFlow), "TC1025SubFlow");
                        context.endAttributes();
                        context.childAsBody(((com.sun.xml.bind.JAXBObject) _TC1025SubFlow), "TC1025SubFlow");
                        context.endElement();
                    }
                }
            }
        }
    }

    public void serializeAttributes(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_Tool == null)? 0 :_Tool.size());
        if (((((_Tool == null)? 0 :_Tool.size()) == 0)&&(_TC1025SubFlow == null))&&(_TC1025No!= null)) {
            if (_TC1025No instanceof javax.xml.bind.Element) {
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _TC1025No), "TC1025No");
            }
        } else {
            if (((((_Tool == null)? 0 :_Tool.size())>= 1)&&(_TC1025SubFlow == null))&&(_TC1025No == null)) {
                while (idx2 != len2) {
                    if (_Tool.get(idx2) instanceof javax.xml.bind.Element) {
                        context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Tool.get(idx2 ++)), "Tool");
                    } else {
                        idx2 += 1;
                    }
                }
            } else {
                if (((((_Tool == null)? 0 :_Tool.size()) == 0)&&(_TC1025SubFlow!= null))&&(_TC1025No == null)) {
                    if (_TC1025SubFlow instanceof javax.xml.bind.Element) {
                        context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _TC1025SubFlow), "TC1025SubFlow");
                    }
                }
            }
        }
    }

    public void serializeURIs(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_Tool == null)? 0 :_Tool.size());
        if (((((_Tool == null)? 0 :_Tool.size()) == 0)&&(_TC1025SubFlow == null))&&(_TC1025No!= null)) {
            if (_TC1025No instanceof javax.xml.bind.Element) {
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) _TC1025No), "TC1025No");
            }
        } else {
            if (((((_Tool == null)? 0 :_Tool.size())>= 1)&&(_TC1025SubFlow == null))&&(_TC1025No == null)) {
                while (idx2 != len2) {
                    if (_Tool.get(idx2) instanceof javax.xml.bind.Element) {
                        context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Tool.get(idx2 ++)), "Tool");
                    } else {
                        idx2 += 1;
                    }
                }
            } else {
                if (((((_Tool == null)? 0 :_Tool.size()) == 0)&&(_TC1025SubFlow!= null))&&(_TC1025No == null)) {
                    if (_TC1025SubFlow instanceof javax.xml.bind.Element) {
                        context.childAsURIs(((com.sun.xml.bind.JAXBObject) _TC1025SubFlow), "TC1025SubFlow");
                    }
                }
            }
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (ru.runa.xpdl.generated.bpmnxpdl.TC1025ImplementationType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun."
+"msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gramm"
+"ar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expression"
+"\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000bex"
+"pandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsr\u0000\'com.sun.msv.gr"
+"ammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/"
+"msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~"
+"\u0000\u0003pp\u0000sq\u0000~\u0000\u0000ppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000"
+"xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003"
+"sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000 com.sun.msv."
+"grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\nx"
+"q\u0000~\u0000\u0003q\u0000~\u0000\u0012psr\u00002com.sun.msv.grammar.Expression$AnyStringExpre"
+"ssion\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\u0011\u0001q\u0000~\u0000\u0016sr\u0000 com.sun.msv.grammar.An"
+"yNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u0017q\u0000~\u0000\u001csr\u0000#com.sun.msv.grammar.SimpleName"
+"Class\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespac"
+"eURIq\u0000~\u0000\u001exq\u0000~\u0000\u0019t\u0000\u001bgenerated.bpmnxpdl.TC1025Not\u0000+http://java."
+"sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000\tpp\u0000sr\u0000\u001fcom.sun.msv.gram"
+"mar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsq\u0000~\u0000\tpp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u000eq\u0000~"
+"\u0000\u0012psq\u0000~\u0000\u0013q\u0000~\u0000\u0012pq\u0000~\u0000\u0016q\u0000~\u0000\u001aq\u0000~\u0000\u001csq\u0000~\u0000\u001dt\u0000\u001fgenerated.bpmnxpdl.TC"
+"1025NoTypeq\u0000~\u0000!sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0013q\u0000~\u0000\u0012psr\u0000\u001bcom.sun.msv.grammar.D"
+"ataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006e"
+"xceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0003ppsr\u0000"
+"\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.ms"
+"v.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.d"
+"atatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype."
+"xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000\u001eL\u0000\btypeName"
+"q\u0000~\u0000\u001eL\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpacePro"
+"cessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0005QNamesr\u00005com."
+"sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000"
+"xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000x"
+"psr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u0012psr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000"
+"\tlocalNameq\u0000~\u0000\u001eL\u0000\fnamespaceURIq\u0000~\u0000\u001expq\u0000~\u00008q\u0000~\u00007sq\u0000~\u0000\u001dt\u0000\u0004type"
+"t\u0000)http://www.w3.org/2001/XMLSchema-instanceq\u0000~\u0000\u001csq\u0000~\u0000\u001dt\u0000\bTC"
+"1025Not\u0000 http://www.wfmc.org/2002/XPDL1.0sq\u0000~\u0000\u000eppsq\u0000~\u0000\u0000ppsq\u0000"
+"~\u0000\tpp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u000eq\u0000~\u0000\u0012psq\u0000~\u0000\u0013q\u0000~\u0000\u0012pq\u0000~\u0000\u0016q\u0000~\u0000\u001aq\u0000~\u0000\u001csq\u0000~\u0000\u001dt"
+"\u0000\u0017generated.bpmnxpdl.Toolq\u0000~\u0000!sq\u0000~\u0000\tpp\u0000sq\u0000~\u0000#ppsq\u0000~\u0000\tpp\u0000sq\u0000~"
+"\u0000\u0000ppsq\u0000~\u0000\u000eq\u0000~\u0000\u0012psq\u0000~\u0000\u0013q\u0000~\u0000\u0012pq\u0000~\u0000\u0016q\u0000~\u0000\u001aq\u0000~\u0000\u001csq\u0000~\u0000\u001dt\u0000\u001bgenerate"
+"d.bpmnxpdl.ToolTypeq\u0000~\u0000!sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0013q\u0000~\u0000\u0012pq\u0000~\u00000q\u0000~\u0000@q\u0000~\u0000\u001cs"
+"q\u0000~\u0000\u001dt\u0000\u0004Toolq\u0000~\u0000Esq\u0000~\u0000\tpp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u000eq\u0000~\u0000\u0012psq\u0000~\u0000\u0013q\u0000~\u0000\u0012pq\u0000"
+"~\u0000\u0016q\u0000~\u0000\u001aq\u0000~\u0000\u001csq\u0000~\u0000\u001dt\u0000 ru.runa.xpdl.generated.bpmnxpdl.TC1025SubFlowq\u0000~\u0000!s"
+"q\u0000~\u0000\tpp\u0000sq\u0000~\u0000#ppsq\u0000~\u0000\tpp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u000eq\u0000~\u0000\u0012psq\u0000~\u0000\u0013q\u0000~\u0000\u0012pq\u0000~"
+"\u0000\u0016q\u0000~\u0000\u001aq\u0000~\u0000\u001csq\u0000~\u0000\u001dt\u0000$ru.runa.xpdl.generated.bpmnxpdl.TC1025SubFlowTypeq\u0000~"
+"\u0000!sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0013q\u0000~\u0000\u0012pq\u0000~\u00000q\u0000~\u0000@q\u0000~\u0000\u001csq\u0000~\u0000\u001dt\u0000\rTC1025SubFlowq"
+"\u0000~\u0000Esr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpT"
+"ablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-"
+"com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005c"
+"ountB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/Express"
+"ionPool;xp\u0000\u0000\u0000\u0018\u0001pq\u0000~\u0000\u0010q\u0000~\u0000\'q\u0000~\u0000Jq\u0000~\u0000Rq\u0000~\u0000\\q\u0000~\u0000dq\u0000~\u0000\u0005q\u0000~\u0000\rq\u0000~\u0000"
+"&q\u0000~\u0000Iq\u0000~\u0000Qq\u0000~\u0000[q\u0000~\u0000cq\u0000~\u0000$q\u0000~\u0000Oq\u0000~\u0000aq\u0000~\u0000\u0006q\u0000~\u0000+q\u0000~\u0000Vq\u0000~\u0000hq\u0000~\u0000"
+"\bq\u0000~\u0000Gq\u0000~\u0000\u0007q\u0000~\u0000Fx"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context) {
            super(context, "--------");
        }

        protected Unmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025ImplementationTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  6 :
                        _TC1025No = ((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025NoTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025NoTypeImpl.class), 7, ___uri, ___local, ___qname, __atts));
                        return ;
                    case  2 :
                        attIdx = context.getAttribute("", "Id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  4 :
                        attIdx = context.getAttribute("", "Execution");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = context.getAttribute("", "Id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  1 :
                        if (("Tool" == ___local)&&("http://www.wfmc.org/2002/XPDL1.0" == ___uri)) {
                            _getTool().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.ToolImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.ToolImpl.class), 1, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("Tool" == ___local)&&("http://www.wfmc.org/2002/XPDL1.0" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 2;
                            return ;
                        }
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  0 :
                        if (("TC1025No" == ___local)&&("http://www.wfmc.org/2002/XPDL1.0" == ___uri)) {
                            _TC1025No = ((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025NoImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025NoImpl.class), 1, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("TC1025No" == ___local)&&("http://www.wfmc.org/2002/XPDL1.0" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 6;
                            return ;
                        }
                        if (("Tool" == ___local)&&("http://www.wfmc.org/2002/XPDL1.0" == ___uri)) {
                            _getTool().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.ToolImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.ToolImpl.class), 1, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("Tool" == ___local)&&("http://www.wfmc.org/2002/XPDL1.0" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 2;
                            return ;
                        }
                        if (("TC1025SubFlow" == ___local)&&("http://www.wfmc.org/2002/XPDL1.0" == ___uri)) {
                            _TC1025SubFlow = ((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025SubFlowImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025SubFlowImpl.class), 1, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("TC1025SubFlow" == ___local)&&("http://www.wfmc.org/2002/XPDL1.0" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 4;
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
                    case  6 :
                        _TC1025No = ((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025NoTypeImpl) spawnChildFromLeaveElement((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025NoTypeImpl.class), 7, ___uri, ___local, ___qname));
                        return ;
                    case  7 :
                        if (("TC1025No" == ___local)&&("http://www.wfmc.org/2002/XPDL1.0" == ___uri)) {
                            context.popAttributes();
                            state = 1;
                            return ;
                        }
                        break;
                    case  2 :
                        attIdx = context.getAttribute("", "Id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  3 :
                        if (("Tool" == ___local)&&("http://www.wfmc.org/2002/XPDL1.0" == ___uri)) {
                            context.popAttributes();
                            state = 1;
                            return ;
                        }
                        break;
                    case  4 :
                        attIdx = context.getAttribute("", "Execution");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "Id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  1 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  5 :
                        if (("TC1025SubFlow" == ___local)&&("http://www.wfmc.org/2002/XPDL1.0" == ___uri)) {
                            context.popAttributes();
                            state = 1;
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
                    case  6 :
                        _TC1025No = ((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025NoTypeImpl) spawnChildFromEnterAttribute((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025NoTypeImpl.class), 7, ___uri, ___local, ___qname));
                        return ;
                    case  2 :
                        if (("Id" == ___local)&&("" == ___uri)) {
                            _getTool().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.ToolTypeImpl) spawnChildFromEnterAttribute((ru.runa.xpdl.generated.bpmnxpdl.impl.ToolTypeImpl.class), 3, ___uri, ___local, ___qname)));
                            return ;
                        }
                        break;
                    case  4 :
                        if (("Execution" == ___local)&&("" == ___uri)) {
                            _TC1025SubFlow = ((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025SubFlowTypeImpl) spawnChildFromEnterAttribute((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025SubFlowTypeImpl.class), 5, ___uri, ___local, ___qname));
                            return ;
                        }
                        if (("Id" == ___local)&&("" == ___uri)) {
                            _TC1025SubFlow = ((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025SubFlowTypeImpl) spawnChildFromEnterAttribute((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025SubFlowTypeImpl.class), 5, ___uri, ___local, ___qname));
                            return ;
                        }
                        break;
                    case  1 :
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
                    case  6 :
                        _TC1025No = ((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025NoTypeImpl) spawnChildFromLeaveAttribute((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025NoTypeImpl.class), 7, ___uri, ___local, ___qname));
                        return ;
                    case  2 :
                        attIdx = context.getAttribute("", "Id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  4 :
                        attIdx = context.getAttribute("", "Execution");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "Id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  1 :
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
                        case  6 :
                            _TC1025No = ((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025NoTypeImpl) spawnChildFromText((ru.runa.xpdl.generated.bpmnxpdl.impl.TC1025NoTypeImpl.class), 7, value));
                            return ;
                        case  2 :
                            attIdx = context.getAttribute("", "Id");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            break;
                        case  4 :
                            attIdx = context.getAttribute("", "Execution");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = context.getAttribute("", "Id");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            break;
                        case  1 :
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
