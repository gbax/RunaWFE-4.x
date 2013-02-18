
package ru.runa.wfe.webservice;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
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
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "assignSwimlane", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.AssignSwimlane")
    @ResponseWrapper(localName = "assignSwimlaneResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.AssignSwimlaneResponse")
    public void assignSwimlane(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        WfExecutor arg3);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "assignTask", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.AssignTask")
    @ResponseWrapper(localName = "assignTaskResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.AssignTaskResponse")
    public void assignTask(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        WfExecutor arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        WfExecutor arg3);

    /**
     * 
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "cancelProcess", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.CancelProcess")
    @ResponseWrapper(localName = "cancelProcessResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.CancelProcessResponse")
    public void cancelProcess(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @throws TaskDoesNotExistException_Exception
     * @throws TaskAlreadyCompletedException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "completeTaskWS", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.CompleteTaskWS")
    @ResponseWrapper(localName = "completeTaskWSResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.CompleteTaskWSResponse")
    public void completeTaskWS(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        List<WfVariable> arg2)
        throws TaskAlreadyCompletedException_Exception, TaskDoesNotExistException_Exception
    ;

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.WfTask>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getActiveTasks", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetActiveTasks")
    @ResponseWrapper(localName = "getActiveTasksResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetActiveTasksResponse")
    public List<WfTask> getActiveTasks(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAllProcessesCount", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetAllProcessesCount")
    @ResponseWrapper(localName = "getAllProcessesCountResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetAllProcessesCountResponse")
    public int getAllProcessesCount(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        BatchPresentation arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns ru.runa.wfe.webservice.WfProcess
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getParentProcess", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetParentProcess")
    @ResponseWrapper(localName = "getParentProcessResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetParentProcessResponse")
    public WfProcess getParentProcess(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns ru.runa.wfe.webservice.WfProcess
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getProcess", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcess")
    @ResponseWrapper(localName = "getProcessResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessResponse")
    public WfProcess getProcess(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns byte[]
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getProcessDiagram", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessDiagram")
    @ResponseWrapper(localName = "getProcessDiagramResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessDiagramResponse")
    public byte[] getProcessDiagram(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        Long arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        Long arg3);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.GraphElementPresentation>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getProcessGraphElements", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessGraphElements")
    @ResponseWrapper(localName = "getProcessGraphElementsResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessGraphElementsResponse")
    public List<GraphElementPresentation> getProcessGraphElements(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns byte[]
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getProcessHistoryDiagram", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessHistoryDiagram")
    @ResponseWrapper(localName = "getProcessHistoryDiagramResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessHistoryDiagramResponse")
    public byte[] getProcessHistoryDiagram(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        Long arg2);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns byte[]
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getProcessLogValue", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessLogValue")
    @ResponseWrapper(localName = "getProcessLogValueResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessLogValueResponse")
    public byte[] getProcessLogValue(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns ru.runa.wfe.webservice.ProcessLogs
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getProcessLogs", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessLogs")
    @ResponseWrapper(localName = "getProcessLogsResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessLogsResponse")
    public ProcessLogs getProcessLogs(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        ProcessLogFilter arg1);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.GraphElementPresentation>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getProcessUIHistoryData", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessUIHistoryData")
    @ResponseWrapper(localName = "getProcessUIHistoryDataResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessUIHistoryDataResponse")
    public List<GraphElementPresentation> getProcessUIHistoryData(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        Long arg2);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.WfProcess>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getProcesses", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcesses")
    @ResponseWrapper(localName = "getProcessesResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetProcessesResponse")
    public List<WfProcess> getProcesses(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        BatchPresentation arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.SwimlaneDefinition>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getSwimlanes", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetSwimlanes")
    @ResponseWrapper(localName = "getSwimlanesResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetSwimlanesResponse")
    public List<SwimlaneDefinition> getSwimlanes(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.SystemLog>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getSystemLogs", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetSystemLogs")
    @ResponseWrapper(localName = "getSystemLogsResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetSystemLogsResponse")
    public List<SystemLog> getSystemLogs(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        BatchPresentation arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getSystemLogsCount", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetSystemLogsCount")
    @ResponseWrapper(localName = "getSystemLogsCountResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetSystemLogsCountResponse")
    public int getSystemLogsCount(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        BatchPresentation arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns ru.runa.wfe.webservice.WfTask
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTask", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetTask")
    @ResponseWrapper(localName = "getTaskResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetTaskResponse")
    public WfTask getTask(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.WfTask>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTasks", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetTasks")
    @ResponseWrapper(localName = "getTasksResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetTasksResponse")
    public List<WfTask> getTasks(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        BatchPresentation arg1);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns ru.runa.wfe.webservice.WfVariable
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getVariable", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetVariable")
    @ResponseWrapper(localName = "getVariableResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetVariableResponse")
    public WfVariable getVariable(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.VariableDefinition>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getVariables", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetVariables")
    @ResponseWrapper(localName = "getVariablesResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetVariablesResponse")
    public List<VariableDefinition> getVariables(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "markTaskOpened", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.MarkTaskOpened")
    @ResponseWrapper(localName = "markTaskOpenedResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.MarkTaskOpenedResponse")
    public void markTaskOpened(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1);

    /**
     * 
     * @param arg5
     * @param arg4
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @param arg6
     * @param arg7
     * @param arg8
     */
    @WebMethod
    @RequestWrapper(localName = "removeProcesses", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.RemoveProcesses")
    @ResponseWrapper(localName = "removeProcessesResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.RemoveProcessesResponse")
    public void removeProcesses(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        XMLGregorianCalendar arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        XMLGregorianCalendar arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        int arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        Long arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        Long arg6,
        @WebParam(name = "arg7", targetNamespace = "")
        boolean arg7,
        @WebParam(name = "arg8", targetNamespace = "")
        boolean arg8);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.Long
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "startProcessWS", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.StartProcessWS")
    @ResponseWrapper(localName = "startProcessWSResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.StartProcessWSResponse")
    public Long startProcessWS(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        List<WfVariable> arg2);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @throws ProcessDoesNotExistException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "updateVariablesWS", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.UpdateVariablesWS")
    @ResponseWrapper(localName = "updateVariablesWSResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.UpdateVariablesWSResponse")
    public void updateVariablesWS(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        List<WfVariable> arg2)
        throws ProcessDoesNotExistException_Exception
    ;

}
