//
// This file was ru.runa.xpdl.generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.23 at 09:51:37 PM MSD 
//


package ru.runa.xpdl.generated.bpmnxpdl.impl;

public class TransitionsTypeImpl implements ru.runa.xpdl.generated.bpmnxpdl.TransitionsType, com.sun.xml.bind.JAXBObject, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallableObject, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializable, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.ValidatableObject
{

    protected com.sun.xml.bind.util.ListImpl _Any;
    protected com.sun.xml.bind.util.ListImpl _Transition;
    public final static java.lang.Class version = (ru.runa.xpdl.generated.bpmnxpdl.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (ru.runa.xpdl.generated.bpmnxpdl.TransitionsType.class);
    }

    protected com.sun.xml.bind.util.ListImpl _getAny() {
        if (_Any == null) {
            _Any = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _Any;
    }

    public java.util.List getAny() {
        return _getAny();
    }

    protected com.sun.xml.bind.util.ListImpl _getTransition() {
        if (_Transition == null) {
            _Transition = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _Transition;
    }

    public java.util.List getTransition() {
        return _getTransition();
    }

    public ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingEventHandler createUnmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context) {
        return new ru.runa.xpdl.generated.bpmnxpdl.impl.TransitionsTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Any == null)? 0 :_Any.size());
        int idx2 = 0;
        final int len2 = ((_Transition == null)? 0 :_Transition.size());
        while (idx2 != len2) {
            if (_Transition.get(idx2) instanceof javax.xml.bind.Element) {
                context.childAsBody(((com.sun.xml.bind.JAXBObject) _Transition.get(idx2 ++)), "Transition");
            } else {
                context.startElement("http://www.wfmc.org/2008/XPDL2.1", "Transition");
                int idx_0 = idx2;
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Transition.get(idx_0 ++)), "Transition");
                context.endNamespaceDecls();
                int idx_1 = idx2;
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Transition.get(idx_1 ++)), "Transition");
                context.endAttributes();
                context.childAsBody(((com.sun.xml.bind.JAXBObject) _Transition.get(idx2 ++)), "Transition");
                context.endElement();
            }
        }
        while (idx1 != len1) {
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _Any.get(idx1 ++)), "Any");
        }
    }

    public void serializeAttributes(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Any == null)? 0 :_Any.size());
        int idx2 = 0;
        final int len2 = ((_Transition == null)? 0 :_Transition.size());
        while (idx2 != len2) {
            if (_Transition.get(idx2) instanceof javax.xml.bind.Element) {
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Transition.get(idx2 ++)), "Transition");
            } else {
                idx2 += 1;
            }
        }
        while (idx1 != len1) {
            idx1 += 1;
        }
    }

    public void serializeURIs(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Any == null)? 0 :_Any.size());
        int idx2 = 0;
        final int len2 = ((_Transition == null)? 0 :_Transition.size());
        while (idx2 != len2) {
            if (_Transition.get(idx2) instanceof javax.xml.bind.Element) {
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Transition.get(idx2 ++)), "Transition");
            } else {
                idx2 += 1;
            }
        }
        while (idx1 != len1) {
            idx1 += 1;
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (ru.runa.xpdl.generated.bpmnxpdl.TransitionsType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000x"
+"r\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003s"
+"r\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\u0006q\u0000~\u0000\fpsr\u0000\'c"
+"om.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClas"
+"st\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.E"
+"lementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentM"
+"odelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003q\u0000~\u0000\fp\u0000sq\u0000~\u0000\u0006ppsq\u0000~\u0000\bq\u0000~\u0000\fpsr\u0000 com.sun.msv.gr"
+"ammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\u000fxq\u0000"
+"~\u0000\u0003q\u0000~\u0000\fpsr\u00002com.sun.msv.grammar.Expression$AnyStringExpress"
+"ion\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\u000b\u0001q\u0000~\u0000\u0017sr\u0000 com.sun.msv.grammar.AnyN"
+"ameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u0018q\u0000~\u0000\u001dsr\u0000#com.sun.msv.grammar.SimpleNameCl"
+"ass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceU"
+"RIq\u0000~\u0000\u001fxq\u0000~\u0000\u001at\u0000\u001dgenerated.bpmnxpdl.Transitiont\u0000+http://java."
+"sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000\u000eq\u0000~\u0000\fp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u000epp"
+"\u0000sq\u0000~\u0000\u0006ppsq\u0000~\u0000\bq\u0000~\u0000\fpsq\u0000~\u0000\u0014q\u0000~\u0000\fpq\u0000~\u0000\u0017q\u0000~\u0000\u001bq\u0000~\u0000\u001dsq\u0000~\u0000\u001et\u0000!gen"
+"erated.bpmnxpdl.TransitionTypeq\u0000~\u0000\"sq\u0000~\u0000\u0006ppsq\u0000~\u0000\u0014q\u0000~\u0000\fpsr\u0000\u001bc"
+"om.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/da"
+"tatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/St"
+"ringPair;xq\u0000~\u0000\u0003ppsr\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'c"
+"om.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespac"
+"eUriq\u0000~\u0000\u001fL\u0000\btypeNameq\u0000~\u0000\u001fL\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/dataty"
+"pe/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSc"
+"hemat\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor"
+"$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceP"
+"rocessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$Null"
+"SetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\fpsr\u0000\u001bcom.sun.msv.util.Str"
+"ingPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001fL\u0000\fnamespaceURIq\u0000~\u0000\u001fxpq\u0000~"
+"\u00008q\u0000~\u00007sq\u0000~\u0000\u001et\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchema-inst"
+"anceq\u0000~\u0000\u001dsq\u0000~\u0000\u001et\u0000\nTransitiont\u0000 http://www.wfmc.org/2008/XPDL"
+"2.1q\u0000~\u0000\u001dsq\u0000~\u0000\u0006ppsq\u0000~\u0000\bq\u0000~\u0000\fpsq\u0000~\u0000\u000eq\u0000~\u0000\fp\u0000sq\u0000~\u0000\u0014ppq\u0000~\u0000\u0017sr\u0000\'co"
+"m.sun.msv.grammar.DifferenceNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003nc1q\u0000~\u0000\u000fL"
+"\u0000\u0003nc2q\u0000~\u0000\u000fxq\u0000~\u0000\u001aq\u0000~\u0000\u001bsr\u0000#com.sun.msv.grammar.ChoiceNameClass"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003nc1q\u0000~\u0000\u000fL\u0000\u0003nc2q\u0000~\u0000\u000fxq\u0000~\u0000\u001asr\u0000&com.sun.msv.gramm"
+"ar.NamespaceNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\fnamespaceURIq\u0000~\u0000\u001fxq\u0000~\u0000\u001at\u0000"
+"\u0000sq\u0000~\u0000Nq\u0000~\u0000Esq\u0000~\u0000Nq\u0000~\u0000\"q\u0000~\u0000\u001dsr\u0000\"com.sun.msv.grammar.Expressi"
+"onPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/Expressi"
+"onPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$C"
+"losedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom"
+"/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\f\u0001pq\u0000~\u0000\u0013q\u0000~\u0000\'q\u0000~\u0000\u0007q\u0000~\u0000\u0012"
+"q\u0000~\u0000&q\u0000~\u0000$q\u0000~\u0000Fq\u0000~\u0000\u0005q\u0000~\u0000+q\u0000~\u0000\rq\u0000~\u0000\nq\u0000~\u0000Gx"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context) {
            super(context, "-----");
        }

        protected Unmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return ru.runa.xpdl.generated.bpmnxpdl.impl.TransitionsTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  3 :
                        if (("Transition" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getTransition().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.TransitionImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.TransitionImpl.class), 3, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("Transition" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 1;
                            return ;
                        }
                        if (!(("" == ___uri)||("http://www.wfmc.org/2008/XPDL2.1" == ___uri))) {
                            java.lang.Object co = spawnWildcard(4, ___uri, ___local, ___qname, __atts);
                            if (co!= null) {
                                _getAny().add(co);
                            }
                            return ;
                        }
                        state = 4;
                        continue outer;
                    case  4 :
                        if (!(("" == ___uri)||("http://www.wfmc.org/2008/XPDL2.1" == ___uri))) {
                            java.lang.Object co = spawnWildcard(4, ___uri, ___local, ___qname, __atts);
                            if (co!= null) {
                                _getAny().add(co);
                            }
                            return ;
                        }
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  0 :
                        if (("Transition" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getTransition().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.TransitionImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.TransitionImpl.class), 3, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("Transition" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 1;
                            return ;
                        }
                        state = 3;
                        continue outer;
                    case  1 :
                        attIdx = context.getAttribute("", "From");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
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
                        state = 4;
                        continue outer;
                    case  4 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  2 :
                        if (("Transition" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
                    case  0 :
                        state = 3;
                        continue outer;
                    case  1 :
                        attIdx = context.getAttribute("", "From");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
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
                        state = 4;
                        continue outer;
                    case  4 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  0 :
                        state = 3;
                        continue outer;
                    case  1 :
                        if (("From" == ___local)&&("" == ___uri)) {
                            _getTransition().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.TransitionTypeImpl) spawnChildFromEnterAttribute((ru.runa.xpdl.generated.bpmnxpdl.impl.TransitionTypeImpl.class), 2, ___uri, ___local, ___qname)));
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
                        state = 4;
                        continue outer;
                    case  4 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  0 :
                        state = 3;
                        continue outer;
                    case  1 :
                        attIdx = context.getAttribute("", "From");
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
                            state = 4;
                            continue outer;
                        case  4 :
                            revertToParentFromText(value);
                            return ;
                        case  0 :
                            state = 3;
                            continue outer;
                        case  1 :
                            attIdx = context.getAttribute("", "From");
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
