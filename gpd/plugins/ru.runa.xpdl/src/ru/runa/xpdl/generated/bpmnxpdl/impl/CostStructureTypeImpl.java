//
// This file was ru.runa.xpdl.generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.23 at 09:51:37 PM MSD 
//


package ru.runa.xpdl.generated.bpmnxpdl.impl;

public class CostStructureTypeImpl implements ru.runa.xpdl.generated.bpmnxpdl.CostStructureType, com.sun.xml.bind.JAXBObject, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallableObject, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializable, ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.ValidatableObject
{

    protected com.sun.xml.bind.util.ListImpl _ResourceCosts;
    protected java.math.BigInteger _FixedCost;
    public final static java.lang.Class version = (ru.runa.xpdl.generated.bpmnxpdl.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (ru.runa.xpdl.generated.bpmnxpdl.CostStructureType.class);
    }

    protected com.sun.xml.bind.util.ListImpl _getResourceCosts() {
        if (_ResourceCosts == null) {
            _ResourceCosts = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _ResourceCosts;
    }

    public java.util.List getResourceCosts() {
        return _getResourceCosts();
    }

    public java.math.BigInteger getFixedCost() {
        return _FixedCost;
    }

    public void setFixedCost(java.math.BigInteger value) {
        _FixedCost = value;
    }

    public ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingEventHandler createUnmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context) {
        return new ru.runa.xpdl.generated.bpmnxpdl.impl.CostStructureTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_ResourceCosts == null)? 0 :_ResourceCosts.size());
        if ((_FixedCost == null)&&(((_ResourceCosts == null)? 0 :_ResourceCosts.size())>= 1)) {
            while (idx1 != len1) {
                if (_ResourceCosts.get(idx1) instanceof javax.xml.bind.Element) {
                    context.childAsBody(((com.sun.xml.bind.JAXBObject) _ResourceCosts.get(idx1 ++)), "ResourceCosts");
                } else {
                    context.startElement("http://www.wfmc.org/2008/XPDL2.1", "ResourceCosts");
                    int idx_0 = idx1;
                    context.childAsURIs(((com.sun.xml.bind.JAXBObject) _ResourceCosts.get(idx_0 ++)), "ResourceCosts");
                    context.endNamespaceDecls();
                    int idx_1 = idx1;
                    context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _ResourceCosts.get(idx_1 ++)), "ResourceCosts");
                    context.endAttributes();
                    context.childAsBody(((com.sun.xml.bind.JAXBObject) _ResourceCosts.get(idx1 ++)), "ResourceCosts");
                    context.endElement();
                }
            }
        } else {
            if ((_FixedCost!= null)&&(((_ResourceCosts == null)? 0 :_ResourceCosts.size()) == 0)) {
                context.startElement("http://www.wfmc.org/2008/XPDL2.1", "FixedCost");
                context.endNamespaceDecls();
                context.endAttributes();
                try {
                    context.text(javax.xml.bind.DatatypeConverter.printInteger(((java.math.BigInteger) _FixedCost)), "FixedCost");
                } catch (java.lang.Exception e) {
                    ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.Util.handlePrintConversionException(this, e, context);
                }
                context.endElement();
            }
        }
    }

    public void serializeAttributes(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_ResourceCosts == null)? 0 :_ResourceCosts.size());
        if ((_FixedCost == null)&&(((_ResourceCosts == null)? 0 :_ResourceCosts.size())>= 1)) {
            while (idx1 != len1) {
                if (_ResourceCosts.get(idx1) instanceof javax.xml.bind.Element) {
                    context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _ResourceCosts.get(idx1 ++)), "ResourceCosts");
                } else {
                    idx1 += 1;
                }
            }
        }
    }

    public void serializeURIs(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_ResourceCosts == null)? 0 :_ResourceCosts.size());
        if ((_FixedCost == null)&&(((_ResourceCosts == null)? 0 :_ResourceCosts.size())>= 1)) {
            while (idx1 != len1) {
                if (_ResourceCosts.get(idx1) instanceof javax.xml.bind.Element) {
                    context.childAsURIs(((com.sun.xml.bind.JAXBObject) _ResourceCosts.get(idx1 ++)), "ResourceCosts");
                } else {
                    idx1 += 1;
                }
            }
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (ru.runa.xpdl.generated.bpmnxpdl.CostStructureType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun."
+"msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gramm"
+"ar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expression"
+"\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000bex"
+"pandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsr\u0000 com.sun.msv.grammar.OneOrMoreE"
+"xp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003e"
+"xpq\u0000~\u0000\u0002xq\u0000~\u0000\u0003sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000"
+"~\u0000\u0000q\u0000~\u0000\u000bpsr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun"
+".msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttribu"
+"tesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003q\u0000~\u0000\u000bp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0007q\u0000~\u0000\u000bpsr\u0000 "
+"com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnam"
+"eClassq\u0000~\u0000\u000exq\u0000~\u0000\u0003q\u0000~\u0000\u000bpsr\u00002com.sun.msv.grammar.Expression$An"
+"yStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\n\u0001q\u0000~\u0000\u0016sr\u0000 com.sun.ms"
+"v.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.Nam"
+"eClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$Epsilo"
+"nExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u0017q\u0000~\u0000\u001csr\u0000#com.sun.msv.gramma"
+"r.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String"
+";L\u0000\fnamespaceURIq\u0000~\u0000\u001exq\u0000~\u0000\u0019t\u0000 ru.runa.xpdl.generated.bpmnxpdl.ResourceCos"
+"tst\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000\rq\u0000~\u0000\u000bp"
+"\u0000sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsq\u0000~\u0000"
+"\rpp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0007q\u0000~\u0000\u000bpsq\u0000~\u0000\u0013q\u0000~\u0000\u000bpq\u0000~\u0000\u0016q\u0000~\u0000\u001aq\u0000~\u0000\u001csq\u0000~\u0000\u001dt\u0000$"
+"ru.runa.xpdl.generated.bpmnxpdl.ResourceCostsTypeq\u0000~\u0000!sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0013q\u0000~\u0000\u000b"
+"psr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/rela"
+"xng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/u"
+"til/StringPair;xq\u0000~\u0000\u0003ppsr\u0000\"com.sun.msv.datatype.xsd.QnameTyp"
+"e\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fna"
+"mespaceUriq\u0000~\u0000\u001eL\u0000\btypeNameq\u0000~\u0000\u001eL\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/"
+"datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001"
+"/XMLSchemat\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd.WhiteSpacePro"
+"cessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.White"
+"SpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expressio"
+"n$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u000bpsr\u0000\u001bcom.sun.msv.ut"
+"il.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001eL\u0000\fnamespaceURIq\u0000~\u0000"
+"\u001expq\u0000~\u00008q\u0000~\u00007sq\u0000~\u0000\u001dt\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchem"
+"a-instanceq\u0000~\u0000\u001csq\u0000~\u0000\u001dt\u0000\rResourceCostst\u0000 http://www.wfmc.org/"
+"2008/XPDL2.1q\u0000~\u0000\u001csq\u0000~\u0000\rpp\u0000sq\u0000~\u0000#ppsq\u0000~\u0000-ppsr\u0000$com.sun.msv.da"
+"tatype.xsd.IntegerType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000+com.sun.msv.datatype.xs"
+"d.IntegerDerivedType\u0099\u00f1]\u0090&6k\u00be\u0002\u0000\u0001L\u0000\nbaseFacetst\u0000)Lcom/sun/msv/"
+"datatype/xsd/XSDatatypeImpl;xq\u0000~\u00002q\u0000~\u00007t\u0000\u0007integerq\u0000~\u0000;sr\u0000,co"
+"m.sun.msv.datatype.xsd.FractionDigitsFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\u0005scal"
+"exr\u0000;com.sun.msv.datatype.xsd.DataTypeWithLexicalConstraintF"
+"acetT\u0090\u001c>\u001azb\u00ea\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.DataTypeWithFace"
+"t\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0005Z\u0000\fisFacetFixedZ\u0000\u0012needValueCheckFlagL\u0000\bbaseTypeq"
+"\u0000~\u0000KL\u0000\fconcreteTypet\u0000\'Lcom/sun/msv/datatype/xsd/ConcreteType"
+";L\u0000\tfacetNameq\u0000~\u0000\u001exq\u0000~\u00004ppq\u0000~\u0000;\u0001\u0000sr\u0000#com.sun.msv.datatype.xs"
+"d.NumberType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u00002q\u0000~\u00007t\u0000\u0007decimalq\u0000~\u0000;q\u0000~\u0000Tt\u0000\u000efra"
+"ctionDigits\u0000\u0000\u0000\u0000q\u0000~\u0000=sq\u0000~\u0000>q\u0000~\u0000Mq\u0000~\u00007sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0013q\u0000~\u0000\u000bpq\u0000~\u0000"
+"0q\u0000~\u0000@q\u0000~\u0000\u001csq\u0000~\u0000\u001dt\u0000\tFixedCostq\u0000~\u0000Esr\u0000\"com.sun.msv.grammar.Ex"
+"pressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/Ex"
+"pressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.Expression"
+"Pool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt"
+"\u0000$Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\f\u0001pq\u0000~\u0000\u0012q\u0000~\u0000\'q\u0000~\u0000"
+"\u0005q\u0000~\u0000\u0006q\u0000~\u0000\u0011q\u0000~\u0000&q\u0000~\u0000Gq\u0000~\u0000$q\u0000~\u0000+q\u0000~\u0000Xq\u0000~\u0000\fq\u0000~\u0000\tx"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context) {
            super(context, "------");
        }

        protected Unmarshaller(ru.runa.xpdl.generated.bpmnxpdl.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return ru.runa.xpdl.generated.bpmnxpdl.impl.CostStructureTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  4 :
                        if (("ResourceCostName" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getResourceCosts().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.ResourceCostsTypeImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.ResourceCostsTypeImpl.class), 5, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        break;
                    case  3 :
                        if (("ResourceCosts" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getResourceCosts().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.ResourceCostsImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.ResourceCostsImpl.class), 3, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ResourceCosts" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 4;
                            return ;
                        }
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  0 :
                        if (("ResourceCosts" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            _getResourceCosts().add(((ru.runa.xpdl.generated.bpmnxpdl.impl.ResourceCostsImpl) spawnChildFromEnterElement((ru.runa.xpdl.generated.bpmnxpdl.impl.ResourceCostsImpl.class), 3, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ResourceCosts" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 4;
                            return ;
                        }
                        if (("FixedCost" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 1;
                            return ;
                        }
                        state = 3;
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
                        if (("FixedCost" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
                    case  3 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  0 :
                        state = 3;
                        continue outer;
                    case  5 :
                        if (("ResourceCosts" == ___local)&&("http://www.wfmc.org/2008/XPDL2.1" == ___uri)) {
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
                    case  0 :
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
                    case  3 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  0 :
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
                        case  1 :
                            state = 2;
                            eatText1(value);
                            return ;
                        case  3 :
                            revertToParentFromText(value);
                            return ;
                        case  0 :
                            state = 3;
                            continue outer;
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

        private void eatText1(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _FixedCost = javax.xml.bind.DatatypeConverter.parseInteger(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

    }

}
