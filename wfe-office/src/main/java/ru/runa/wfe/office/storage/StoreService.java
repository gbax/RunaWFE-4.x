package ru.runa.wfe.office.storage;

import java.util.List;
import java.util.Properties;

import ru.runa.wfe.office.storage.binding.ExecutionResult;
import ru.runa.wfe.var.dto.WfVariable;

public interface StoreService {

    public static final String PROP_CONSTRAINTS = "constaraints";
    public static final String PROP_PATH = "path";
    public static final String PROP_FORMAT = "format";

    void createFileIfNotExist(String path) throws Exception;

    ExecutionResult findByFilter(Properties properties, String condition, List<WfVariable> variables) throws Exception;

    void update(Properties properties, WfVariable variable, String condition, List<WfVariable> variables) throws Exception;

    void delete(Properties properties, WfVariable variable, String condition, List<WfVariable> variables) throws Exception;

    void save(Properties properties, WfVariable variable, boolean appendTo) throws Exception;

}
