//
// This file was ru.runa.xpdl.generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.23 at 09:51:37 PM MSD 
//


package ru.runa.xpdl.generated.bpmnxpdl.impl;

public class UnionTypeTypeImpl implements ru.runa.xpdl.generated.bpmnxpdl.UnionTypeType, com.sun.xml.bind.JAXBObject, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallableObject, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializable, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.ValidatableObject
{

    protected com.sun.xml.bind.util.ListImpl _Any;
    protected com.sun.xml.bind.util.ListImpl _Member;
    public final static java.lang.Class version = (ru.runa.xpdl.generated.bpmnxpdl.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (ru.runa.xpdl.generated.bpmnxpdl.UnionTypeType.class);
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

    protected com.sun.xml.bind.util.ListImpl _getMember() {
        if (_Member == null) {
            _Member = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _Member;
    }

    public java.util.List getMember() {
        return _getMember();
    }

    public ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingEventHandler createUnmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context) {
        return new ru.runa.xpdl.generated.bpmnxpdl.impl.UnionTypeTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Any == null)? 0 :_Any.size());
        int idx2 = 0;
        final int len2 = ((_Member == null)? 0 :_Member.size());
        while (idx2 != len2) {
            if (_Member.get(idx2) instanceof javax.xml.bind.Element) {
                context.childAsBody(((com.sun.xml.bind.JAXBObject) _Member.get(idx2 ++)), "Member");
            } else {
                context.startElement("http://www.wfmc.org/2008/XPDL2.1", "Member");
                int idx_0 = idx2;
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Member.get(idx_0 ++)), "Member");
                context.endNamespaceDecls();
                int idx_1 = idx2;
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Member.get(idx_1 ++)), "Member");
                context.endAttributes();
                context.childAsBody(((com.sun.xml.bind.JAXBObject) _Member.get(idx2 ++)), "Member");
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
        final int len2 = ((_Member == null)? 0 :_Member.size());
        while (idx2 != len2) {
            if (_Member.get(idx2) instanceof javax.xml.bind.Element) {
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Member.get(idx2 ++)), "Member");
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
        final int len2 = ((_Member == null)? 0 :_Member.size());
        while (idx2 != len2) {
            if (_Member.get(idx2) instanceof javax.xml.bind.Element) {
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Member.get(idx2 ++)), "Member");
            } else {
                idx2 += 1;
            }
        }
        while (idx1 != len1) {
            idx1 += 1;
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (ru.runa.xpdl.generated.bpmnxpdl.UnionTypeType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000"
+"\u0002xq\u0000~\u0000\u0003ppsr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001p"
+"psr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tna"
+"meClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.gra"
+"mmar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fco"
+"ntentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003pp\u0000sq\u0000~\u0000\tppsq\u0000~\u0000\u0006sr\u0000\u0011java.lang.Boolean\u00cd"
+" r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\fxq\u0000~\u0000\u0003q\u0000~\u0000\u0012psr\u00002com.sun"
+".msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003"
+"sq\u0000~\u0000\u0011\u0001q\u0000~\u0000\u0016sr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000x"
+"r\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv"
+".grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u0017q"
+"\u0000~\u0000\u001csr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tloc"
+"alNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq\u0000~\u0000\u001exq\u0000~\u0000\u0019t\u0000\u0019gene"
+"rated.bpmnxpdl.Membert\u0000+http://java.sun.com/jaxb/xjc/dummy-e"
+"lementssq\u0000~\u0000\u000bpp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u000bpp\u0000sq\u0000~\u0000\tppsq\u0000~\u0000\u0006q\u0000~\u0000\u0012psq\u0000~\u0000\u0013q"
+"\u0000~\u0000\u0012pq\u0000~\u0000\u0016q\u0000~\u0000\u001aq\u0000~\u0000\u001csq\u0000~\u0000\u001dt\u0000\u001dgenerated.bpmnxpdl.MemberTypeq\u0000"
+"~\u0000!sq\u0000~\u0000\tppsq\u0000~\u0000\u0013q\u0000~\u0000\u0012psr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000"
+"\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0003ppsr\u0000\"com.sun.msv"
+".datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.x"
+"sd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd."
+"ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDataty"
+"peImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000\u001eL\u0000\btypeNameq\u0000~\u0000\u001eL\u0000\nwhit"
+"eSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 "
+"http://www.w3.org/2001/XMLSchemat\u0000\u0005QNamesr\u00005com.sun.msv.data"
+"type.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun."
+"msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun"
+".msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000"
+"~\u0000\u0012psr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000"
+"~\u0000\u001eL\u0000\fnamespaceURIq\u0000~\u0000\u001expq\u0000~\u00007q\u0000~\u00006sq\u0000~\u0000\u001dt\u0000\u0004typet\u0000)http://ww"
+"w.w3.org/2001/XMLSchema-instanceq\u0000~\u0000\u001csq\u0000~\u0000\u001dt\u0000\u0006Membert\u0000 http:"
+"//www.wfmc.org/2008/XPDL2.1sq\u0000~\u0000\tppsq\u0000~\u0000\u0006q\u0000~\u0000\u0012psq\u0000~\u0000\u000bq\u0000~\u0000\u0012p\u0000"
+"sq\u0000~\u0000\u0013ppq\u0000~\u0000\u0016sr\u0000\'com.sun.msv.grammar.DifferenceNameClass\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003nc1q\u0000~\u0000\fL\u0000\u0003nc2q\u0000~\u0000\fxq\u0000~\u0000\u0019q\u0000~\u0000\u001asr\u0000#com.sun.msv.gram"
+"mar.ChoiceNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003nc1q\u0000~\u0000\fL\u0000\u0003nc2q\u0000~\u0000\fxq\u0000~\u0000\u0019sr"
+"\u0000&com.sun.msv.grammar.NamespaceNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\fnamesp"
+"aceURIq\u0000~\u0000\u001exq\u0000~\u0000\u0019t\u0000\u0000sq\u0000~\u0000Mq\u0000~\u0000Dsq\u0000~\u0000Mq\u0000~\u0000!q\u0000~\u0000\u001csr\u0000\"com.sun.m"
+"sv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/m"
+"sv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.gramm"
+"ar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVers"
+"ionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\u000b\u0001pq"
+"\u0000~\u0000\u0010q\u0000~\u0000&q\u0000~\u0000\u000fq\u0000~\u0000%q\u0000~\u0000#q\u0000~\u0000Eq\u0000~\u0000*q\u0000~\u0000Fq\u0000~\u0000\nq\u0000~\u0000\bq\u0000~\u0000\u0005x"));
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
            return ru.runa.xpdl.generated.bpmnxpdl.impl.UnionTypeTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
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
                    case  1 :
                        if (("BasicType" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("BasicType" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("DeclaredType" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("DeclaredType" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("SchemaType" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("SchemaType" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ExternalReference" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ExternalReference" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("RecordType" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("RecordType" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("UnionType" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("UnionType" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("EnumerationType" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("EnumerationType" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ArrayType" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ArrayType" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ListType" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ListType" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        break;
                    case  0 :
                        if (("Member" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberImpl.class), 3, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("Member" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 1;
                            return ;
                        }
                        break;
                    case  3 :
                        if (("Member" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getMember().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.MemberImpl.class), 3, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("Member" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
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
                    case  4 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  2 :
                        if (("Member" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
                    case  3 :
                        state = 4;
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
                    case  4 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  3 :
                        state = 4;
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
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  3 :
                        state = 4;
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
                            revertToParentFromText(value);
                            return ;
                        case  3 :
                            state = 4;
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
