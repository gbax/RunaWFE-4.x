package ru.runa.wfe.office.storage;

import ru.runa.wfe.office.storage.binding.DataBinding;
import ru.runa.wfe.office.storage.binding.ExecutionResult;
import ru.runa.wfe.var.dto.WfVariable;
import ru.runa.wfe.var.format.VariableFormat;

public interface StoreHelper {

    void setVariableFormat(VariableFormat format);

    ExecutionResult execute(DataBinding binding, WfVariable variable);

}
