package ru.runa.office.excel;

import ru.runa.office.FilesSupplierMode;
import ru.runa.office.resource.Messages;

public class ReadHandlerCellEditorProvider extends BaseExcelHandlerCellEditorProvider {

    @Override
    protected String getTitle() {
        return Messages.getString("ImportExcelHandlerConfig.title");
    }

    @Override
    protected FilesSupplierMode getMode() {
        return FilesSupplierMode.IN;
    }
}
