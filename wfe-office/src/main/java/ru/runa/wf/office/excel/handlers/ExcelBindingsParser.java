package ru.runa.wf.office.excel.handlers;

import java.util.List;

import org.dom4j.Element;

import ru.runa.wf.office.excel.IExcelConstraints;
import ru.runa.wf.office.shared.FilesSupplierConfigParser;

import com.google.common.base.Preconditions;

public class ExcelBindingsParser extends FilesSupplierConfigParser<ExcelBindings> {

    @Override
    protected ExcelBindings instantiate() {
        return new ExcelBindings();
    }

    @SuppressWarnings("unchecked")
	@Override
    protected void parseCustom(Element root, ExcelBindings bindings) throws Exception {
        List<Element> bindingElements = root.elements("binding");
        for (Element bindingElement : bindingElements) {
            String className = bindingElement.attributeValue("class");
            Preconditions.checkNotNull(className, "Missed 'class' attribute in binding element");
            String variableName = bindingElement.attributeValue("variable");
            Preconditions.checkNotNull(variableName, "Missed 'variable' attribute in binding element");
            IExcelConstraints constraints = (IExcelConstraints) Class.forName(className).newInstance();
            Element configElement = bindingElement.element("config");
            Preconditions.checkNotNull(configElement, "Missed 'config' element in binding element");
            constraints.configure(configElement);
            ExcelBinding binding = new ExcelBinding();
            binding.setConstraints(constraints);
            binding.setVariableName(variableName);
            bindings.getBindings().add(binding);
        }
    }

}
