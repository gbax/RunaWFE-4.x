//
// This file was ru.runa.xpdl.generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.23 at 09:51:37 PM MSD 
//


package ru.runa.xpdl.generated.bpmnxpdl.impl;

public class MessageFlowsTypeImpl implements ru.runa.xpdl.generated.bpmnxpdl.MessageFlowsType, com.sun.xml.bind.JAXBObject, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallableObject, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializable, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.ValidatableObject
{

    protected com.sun.xml.bind.util.ListImpl _MessageFlowAndAny;
    public final static java.lang.Class version = (ru.runa.xpdl.generated.bpmnxpdl.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (ru.runa.xpdl.generated.bpmnxpdl.MessageFlowsType.class);
    }

    protected com.sun.xml.bind.util.ListImpl _getMessageFlowAndAny() {
        if (_MessageFlowAndAny == null) {
            _MessageFlowAndAny = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _MessageFlowAndAny;
    }

    public java.util.List getMessageFlowAndAny() {
        return _getMessageFlowAndAny();
    }

    public ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingEventHandler createUnmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context) {
        return new ru.runa.xpdl.generated.bpmnxpdl.impl.MessageFlowsTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_MessageFlowAndAny == null)? 0 :_MessageFlowAndAny.size());
        while (idx1 != len1) {
            while (idx1 != len1) {
                {
                    java.lang.Object o = _MessageFlowAndAny.get(idx1);
                    if (o instanceof com.sun.xml.bind.JAXBObject) {
                        context.childAsBody(((com.sun.xml.bind.JAXBObject) _MessageFlowAndAny.get(idx1 ++)), "MessageFlowAndAny");
                    } else {
                        if (o instanceof java.lang.Object) {
                            context.childAsBody(((com.sun.xml.bind.JAXBObject) _MessageFlowAndAny.get(idx1 ++)), "MessageFlowAndAny");
                        } else {
                            ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.Util.handleTypeMismatchError(context, this, "MessageFlowAndAny", o);
                        }
                    }
                }
            }
        }
    }

    public void serializeAttributes(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_MessageFlowAndAny == null)? 0 :_MessageFlowAndAny.size());
        while (idx1 != len1) {
            while (idx1 != len1) {
                {
                    java.lang.Object o = _MessageFlowAndAny.get(idx1);
                    if (o instanceof com.sun.xml.bind.JAXBObject) {
                        context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _MessageFlowAndAny.get(idx1 ++)), "MessageFlowAndAny");
                    } else {
                        if (o instanceof java.lang.Object) {
                            idx1 += 1;
                        } else {
                            ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.Util.handleTypeMismatchError(context, this, "MessageFlowAndAny", o);
                        }
                    }
                }
            }
        }
    }

    public void serializeURIs(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_MessageFlowAndAny == null)? 0 :_MessageFlowAndAny.size());
        while (idx1 != len1) {
            while (idx1 != len1) {
                {
                    java.lang.Object o = _MessageFlowAndAny.get(idx1);
                    if (o instanceof com.sun.xml.bind.JAXBObject) {
                        context.childAsURIs(((com.sun.xml.bind.JAXBObject) _MessageFlowAndAny.get(idx1 ++)), "MessageFlowAndAny");
                    } else {
                        if (o instanceof java.lang.Object) {
                            idx1 += 1;
                        } else {
                            ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.Util.handleTypeMismatchError(context, this, "MessageFlowAndAny", o);
                        }
                    }
                }
            }
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (ru.runa.xpdl.generated.bpmnxpdl.MessageFlowsType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun."
+"msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gramm"
+"ar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expression"
+"\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000bex"
+"pandedExpq\u0000~\u0000\u0002xpppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002x"
+"q\u0000~\u0000\u0003sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000\u001fcom.sun"
+".msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001q\u0000~\u0000\npsr\u0000\'com.sun.m"
+"sv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom"
+"/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.ElementEx"
+"p\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelq\u0000~\u0000"
+"\u0002xq\u0000~\u0000\u0003q\u0000~\u0000\np\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0006q\u0000~\u0000\npsr\u0000 com.sun.msv.grammar.At"
+"tributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\u000exq\u0000~\u0000\u0003q\u0000~\u0000\n"
+"psr\u00002com.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\t\u0001q\u0000~\u0000\u0016sr\u0000 com.sun.msv.grammar.AnyNameClass"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u0000"
+"0com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000"
+"xq\u0000~\u0000\u0003q\u0000~\u0000\u0017q\u0000~\u0000\u001csr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq\u0000~\u0000\u001ex"
+"q\u0000~\u0000\u0019t\u0000\u001egenerated.bpmnxpdl.MessageFlowt\u0000+http://java.sun.com"
+"/jaxb/xjc/dummy-elementssq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0006q\u0000~\u0000\npsq\u0000~\u0000\rq\u0000~\u0000\np\u0000sq\u0000"
+"~\u0000\u0013ppq\u0000~\u0000\u0016sr\u0000\'com.sun.msv.grammar.DifferenceNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0002L\u0000\u0003nc1q\u0000~\u0000\u000eL\u0000\u0003nc2q\u0000~\u0000\u000exq\u0000~\u0000\u0019q\u0000~\u0000\u001asr\u0000#com.sun.msv.grammar"
+".ChoiceNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003nc1q\u0000~\u0000\u000eL\u0000\u0003nc2q\u0000~\u0000\u000exq\u0000~\u0000\u0019sr\u0000&c"
+"om.sun.msv.grammar.NamespaceNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\fnamespace"
+"URIq\u0000~\u0000\u001exq\u0000~\u0000\u0019t\u0000\u0000sq\u0000~\u0000*t\u0000 http://www.wfmc.org/2008/XPDL2.1sq"
+"\u0000~\u0000*q\u0000~\u0000!q\u0000~\u0000\u001cq\u0000~\u0000\u001csr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$Cl"
+"osedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash"
+"\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/"
+"grammar/ExpressionPool;xp\u0000\u0000\u0000\u0007\u0001pq\u0000~\u0000\u0012q\u0000~\u0000#q\u0000~\u0000\"q\u0000~\u0000\u0011q\u0000~\u0000\fq\u0000~\u0000"
+"\bq\u0000~\u0000\u0005x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context) {
            super(context, "---");
        }

        protected Unmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return ru.runa.xpdl.generated.bpmnxpdl.impl.MessageFlowsTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  2 :
                        if (("MessageFlow" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMessageFlowAndAny().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MessageFlowImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MessageFlowImpl.class), 1, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (!(("" == ___uri)||("http://www.wfmc.org/2008/XPDL2.1" == ___uri))) {
                            java.lang.Object co = spawnWildcard(2, ___uri, ___local, ___qname, __atts);
                            if (co!= null) {
                                _getMessageFlowAndAny().add(co);
                            }
                            return ;
                        }
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  0 :
                        if (("MessageFlow" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMessageFlowAndAny().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MessageFlowImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MessageFlowImpl.class), 1, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        state = 2;
                        continue outer;
                    case  1 :
                        if (!(("" == ___uri)||("http://www.wfmc.org/2008/XPDL2.1" == ___uri))) {
                            java.lang.Object co = spawnWildcard(2, ___uri, ___local, ___qname, __atts);
                            if (co!= null) {
                                _getMessageFlowAndAny().add(co);
                            }
                            return ;
                        }
                        state = 2;
                        continue outer;
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
                    case  2 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  0 :
                        state = 2;
                        continue outer;
                    case  1 :
                        state = 2;
                        continue outer;
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
                    case  2 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  0 :
                        state = 2;
                        continue outer;
                    case  1 :
                        state = 2;
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
                    case  2 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  0 :
                        state = 2;
                        continue outer;
                    case  1 :
                        state = 2;
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
                        case  2 :
                            revertToParentFromText(value);
                            return ;
                        case  0 :
                            state = 2;
                            continue outer;
                        case  1 :
                            state = 2;
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
