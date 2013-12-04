
package ru.runa.wfe.webservice;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2-12/14/2009 02:16 PM(ramkris)-
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ExecutionAPI", targetNamespace = "http://impl.service.wfe.runa.ru/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ExecutionAPI {


    /**
     * 
     * @param swimlaneName
     * @param processId
     * @param executor
     * @param user
     */
    @WebMethod
    @RequestWrapper(localName = "assignSwimlane", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.AssignSwimlane")
    @ResponseWrapper(localName = "assignSwimlaneResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.AssignSwimlaneResponse")
    public void assignSwimlane(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "processId", targetNamespace = "")
        Long processId,
        @WebParam(name = "swimlaneName", targetNamespace = "")
        String swimlaneName,
        @WebParam(name = "executor", targetNamespace = "")
        WfExecutor executor);

    /**
     * 
     * @param previousOwner
     * @param taskId
     * @param newExecutor
     * @param user
     */
    @WebMethod
    @RequestWrapper(localName = "assignTask", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.AssignTask")
    @ResponseWrapper(localName = "assignTaskResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.AssignTaskResponse")
    public void assignTask(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "taskId", targetNamespace = "")
        Long taskId,
        @WebParam(name = "previousOwner", targetNamespace = "")
        WfExecutor previousOwner,
        @WebParam(name = "newExecutor", targetNamespace = "")
        WfExecutor newExecutor);

    /**
     * 
     * @param processId
     * @param user
     */
    @WebMethod
    @RequestWrapper(localName = "cancelProcess", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.CancelProcess")
    @ResponseWrapper(localName = "cancelProcessResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.CancelProcessResponse")
    public void cancelProcess(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "processId", targetNamespace = "")
        Long processId);

    /**
     * 
     * @param taskId
     * @param swimlaneActorId
     * @param user
     * @param variables
     */
    @WebMethod
    @RequestWrapper(localName = "completeTaskWS", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.CompleteTaskWS")
    @ResponseWrapper(localName = "completeTaskWSResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.CompleteTaskWSResponse")
    public void completeTaskWS(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "taskId", targetNamespace = "")
        Long taskId,
        @WebParam(name = "variables", targetNamespace = "")
        List<WfVariable> variables,
        @WebParam(name = "swimlaneActorId", targetNamespace = "")
        Long swimlaneActorId);

    /**
     * 
     * @param batchPresentation
     * @param user
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getAllProcessesCount", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetAllProcessesCount")
    @ResponseWrapper(localName = "getAllProcessesCountResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetAllProcessesCountResponse")
    public int getAllProcessesCount(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "batchPresentation", targetNamespace = "")
        BatchPresentation batchPresentation);

    /**
     * 
     * @param id
     * @param user
     * @return
     *     returns ru.runa.wfe.webservice.WfProcess
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getParentProcess", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetParentProcess")
    @ResponseWrapper(localName = "getParentProcessResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetParentProcessResponse")
    public WfProcess getParentProcess(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "id", targetNamespace = "")
        Long id);

    /**
     * 
     * @param id
     * @param user
     * @return
     *     returns ru.runa.wfe.webservice.WfProcess
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getProcess", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcess")
    @ResponseWrapper(localName = "getProcessResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessResponse")
    public WfProcess getProcess(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "id", targetNamespace = "")
        Long id);

    /**
     * 
     * @param childProcessId
     * @param taskId
     * @param processId
     * @param user
     * @param subprocessId
     * @return
     *     returns byte[]
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getProcessDiagram", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessDiagram")
    @ResponseWrapper(localName = "getProcessDiagramResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessDiagramResponse")
    public byte[] getProcessDiagram(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "processId", targetNamespace = "")
        Long processId,
        @WebParam(name = "taskId", targetNamespace = "")
        Long taskId,
        @WebParam(name = "childProcessId", targetNamespace = "")
        Long childProcessId,
        @WebParam(name = "subprocessId", targetNamespace = "")
        String subprocessId);

    /**
     * 
     * @param processId
     * @param user
     * @param subprocessId
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.GraphElementPresentation>
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getProcessGraphElements", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessGraphElements")
    @ResponseWrapper(localName = "getProcessGraphElementsResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessGraphElementsResponse")
    public List<GraphElementPresentation> getProcessGraphElements(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "processId", targetNamespace = "")
        Long processId,
        @WebParam(name = "subprocessId", targetNamespace = "")
        String subprocessId);

    /**
     * 
     * @param taskId
     * @param processId
     * @param user
     * @return
     *     returns byte[]
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getProcessHistoryDiagram", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessHistoryDiagram")
    @ResponseWrapper(localName = "getProcessHistoryDiagramResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessHistoryDiagramResponse")
    public byte[] getProcessHistoryDiagram(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "processId", targetNamespace = "")
        Long processId,
        @WebParam(name = "taskId", targetNamespace = "")
        Long taskId);

    /**
     * 
     * @param taskId
     * @param processId
     * @param user
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.GraphElementPresentation>
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getProcessHistoryDiagramElements", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessHistoryDiagramElements")
    @ResponseWrapper(localName = "getProcessHistoryDiagramElementsResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessHistoryDiagramElementsResponse")
    public List<GraphElementPresentation> getProcessHistoryDiagramElements(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "processId", targetNamespace = "")
        Long processId,
        @WebParam(name = "taskId", targetNamespace = "")
        Long taskId);

    /**
     * 
     * @param logId
     * @param user
     * @return
     *     returns java.lang.Object
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getProcessLogValue", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessLogValue")
    @ResponseWrapper(localName = "getProcessLogValueResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessLogValueResponse")
    public Object getProcessLogValue(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "logId", targetNamespace = "")
        Long logId);

    /**
     * 
     * @param user
     * @param filter
     * @return
     *     returns ru.runa.wfe.webservice.ProcessLogs
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getProcessLogs", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessLogs")
    @ResponseWrapper(localName = "getProcessLogsResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessLogsResponse")
    public ProcessLogs getProcessLogs(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "filter", targetNamespace = "")
        ProcessLogFilter filter);

    /**
     * 
     * @param processId
     * @param user
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.WfTask>
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getProcessTasks", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessTasks")
    @ResponseWrapper(localName = "getProcessTasksResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessTasksResponse")
    public List<WfTask> getProcessTasks(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "processId", targetNamespace = "")
        Long processId);

    /**
     * 
     * @param batchPresentation
     * @param user
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.WfProcess>
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getProcesses", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcesses")
    @ResponseWrapper(localName = "getProcessesResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessesResponse")
    public List<WfProcess> getProcesses(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "batchPresentation", targetNamespace = "")
        BatchPresentation batchPresentation);

    /**
     * 
     * @param id
     * @param recursive
     * @param user
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.WfProcess>
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getSubprocesses", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetSubprocesses")
    @ResponseWrapper(localName = "getSubprocessesResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetSubprocessesResponse")
    public List<WfProcess> getSubprocesses(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "id", targetNamespace = "")
        Long id,
        @WebParam(name = "recursive", targetNamespace = "")
        boolean recursive);

    /**
     * 
     * @param processId
     * @param user
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.WfSwimlane>
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getSwimlanes", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetSwimlanes")
    @ResponseWrapper(localName = "getSwimlanesResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetSwimlanesResponse")
    public List<WfSwimlane> getSwimlanes(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "processId", targetNamespace = "")
        Long processId);

    /**
     * 
     * @param batchPresentation
     * @param user
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.SystemLog>
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getSystemLogs", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetSystemLogs")
    @ResponseWrapper(localName = "getSystemLogsResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetSystemLogsResponse")
    public List<SystemLog> getSystemLogs(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "batchPresentation", targetNamespace = "")
        BatchPresentation batchPresentation);

    /**
     * 
     * @param batchPresentation
     * @param user
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getSystemLogsCount", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetSystemLogsCount")
    @ResponseWrapper(localName = "getSystemLogsCountResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetSystemLogsCountResponse")
    public int getSystemLogsCount(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "batchPresentation", targetNamespace = "")
        BatchPresentation batchPresentation);

    /**
     * 
     * @param taskId
     * @param user
     * @return
     *     returns ru.runa.wfe.webservice.WfTask
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getTask", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetTask")
    @ResponseWrapper(localName = "getTaskResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetTaskResponse")
    public WfTask getTask(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "taskId", targetNamespace = "")
        Long taskId);

    /**
     * 
     * @param batchPresentation
     * @param user
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.WfTask>
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getTasks", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetTasks")
    @ResponseWrapper(localName = "getTasksResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetTasksResponse")
    public List<WfTask> getTasks(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "batchPresentation", targetNamespace = "")
        BatchPresentation batchPresentation);

    /**
     * 
     * @param variableName
     * @param processId
     * @param user
     * @return
     *     returns ru.runa.wfe.webservice.WfVariable
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getVariable", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetVariable")
    @ResponseWrapper(localName = "getVariableResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetVariableResponse")
    public WfVariable getVariable(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "processId", targetNamespace = "")
        Long processId,
        @WebParam(name = "variableName", targetNamespace = "")
        String variableName);

    /**
     * 
     * @param processId
     * @param user
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.WfVariable>
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getVariablesWS", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetVariablesWS")
    @ResponseWrapper(localName = "getVariablesWSResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetVariablesWSResponse")
    public List<WfVariable> getVariablesWS(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "processId", targetNamespace = "")
        Long processId);

    /**
     * 
     * @param taskId
     * @param user
     */
    @WebMethod
    @RequestWrapper(localName = "markTaskOpened", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.MarkTaskOpened")
    @ResponseWrapper(localName = "markTaskOpenedResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.MarkTaskOpenedResponse")
    public void markTaskOpened(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "taskId", targetNamespace = "")
        Long taskId);

    /**
     * 
     * @param user
     * @param filter
     */
    @WebMethod
    @RequestWrapper(localName = "removeProcesses", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.RemoveProcesses")
    @ResponseWrapper(localName = "removeProcessesResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.RemoveProcessesResponse")
    public void removeProcesses(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "filter", targetNamespace = "")
        ProcessFilter filter);

    /**
     * 
     * @param definitionName
     * @param user
     * @param variables
     * @return
     *     returns java.lang.Long
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "startProcessWS", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.StartProcessWS")
    @ResponseWrapper(localName = "startProcessWSResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.StartProcessWSResponse")
    public Long startProcessWS(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "definitionName", targetNamespace = "")
        String definitionName,
        @WebParam(name = "variables", targetNamespace = "")
        List<WfVariable> variables);

    /**
     * 
     * @param processId
     * @param user
     * @param variables
     */
    @WebMethod
    @RequestWrapper(localName = "updateVariablesWS", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.UpdateVariablesWS")
    @ResponseWrapper(localName = "updateVariablesWSResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.UpdateVariablesWSResponse")
    public void updateVariablesWS(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "processId", targetNamespace = "")
        Long processId,
        @WebParam(name = "variables", targetNamespace = "")
        List<WfVariable> variables);

}
