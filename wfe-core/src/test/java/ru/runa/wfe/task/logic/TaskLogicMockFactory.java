package ru.runa.wfe.task.logic;

import ru.runa.wfe.audit.ProcessLog;
import ru.runa.wfe.audit.dao.IProcessLogDAO;
import ru.runa.wfe.commons.dao.IGenericDAO;
import ru.runa.wfe.definition.dao.IProcessDefinitionLoader;
import ru.runa.wfe.execution.IExecutorContextFactory;
import ru.runa.wfe.presentation.hibernate.IBatchPresentationCompilerFactory;
import ru.runa.wfe.ss.logic.ISubstitutionLogic;
import ru.runa.wfe.task.Task;
import ru.runa.wfe.task.dto.IWfTaskFactory;
import ru.runa.wfe.user.dao.IExecutorDAO;

public class TaskLogicMockFactory {

    public IWfTaskFactory createMockWfTaskFactory() {
        /* TODO: implement */
        return null;
    }

    public IExecutorDAO createMockExecutorDAO() {
        /* TODO: implement */
        return null;
    }

    public ISubstitutionLogic createMockSubstitutionLogic() {
        /* TODO: implement */
        return null;
    }

    public IProcessDefinitionLoader createMockProcessDefinitionLoader() {
        /* TODO: implement */
        return null;
    }

    public IGenericDAO<Task> createMockGenericDAO() {
        /* TODO: implement */
        return null;
    }

    public IExecutorContextFactory createMockExecutorContextFactory() {
        /* TODO: implement */
        return null;
    }

    public IBatchPresentationCompilerFactory<?> createMockBatchPresentationCompilerFactory() {
        /* TODO: implement */
        return null;
    }

    public IProcessLogDAO<ProcessLog> createMockProcessLogDAO() {
        /* TODO: implement */
        return null;
    }
}
