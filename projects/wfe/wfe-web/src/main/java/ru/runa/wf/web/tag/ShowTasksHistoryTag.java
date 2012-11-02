package ru.runa.wf.web.tag;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.ecs.html.TD;
import org.apache.ecs.html.TH;
import org.apache.ecs.html.TR;

import ru.runa.common.web.Messages;
import ru.runa.common.web.Resources;
import ru.runa.common.web.html.HeaderBuilder;
import ru.runa.common.web.html.RowBuilder;
import ru.runa.common.web.html.TRRowBuilder;
import ru.runa.common.web.html.TableBuilder;
import ru.runa.service.delegate.DelegateFactory;
import ru.runa.service.wf.ExecutionService;
import ru.runa.wf.web.action.CancelProcessAction;
import ru.runa.wfe.audit.ProcessLog;
import ru.runa.wfe.audit.ProcessLogFilter;
import ru.runa.wfe.audit.ProcessLogs;
import ru.runa.wfe.audit.TaskCreateLog;
import ru.runa.wfe.audit.TaskEndLog;
import ru.runa.wfe.commons.CalendarUtil;
import ru.runa.wfe.execution.ProcessPermission;
import ru.runa.wfe.security.Permission;

import com.google.common.collect.Lists;

/**
 * Display tasks history for process.
 * 
 * @author riven 18.08.2012
 * @jsp.tag name = "showTasksHistory" body-content = "JSP"
 */
public class ShowTasksHistoryTag extends ProcessBaseFormTag {
    private static final long serialVersionUID = 1L;

    @Override
    protected void fillFormData(TD tdFormElement) throws JspException {
        try {
            ExecutionService executionService = DelegateFactory.getExecutionService();
            ProcessLogFilter filter = new ProcessLogFilter(getIdentifiableId());
            filter.setIncludeSubprocessLogs(true);
            ProcessLogs logs = executionService.getProcessLogs(getSubject(), filter);
            List<TR> rows = processLogs(logs);
            HeaderBuilder tasksHistoryHeaderBuilder = new TasksHistoryHeaderBuilder();
            RowBuilder rowBuilder = new TRRowBuilder(rows);
            TableBuilder tableBuilder = new TableBuilder();
            tdFormElement.addElement(tableBuilder.build(tasksHistoryHeaderBuilder, rowBuilder));
        } catch (Exception e) {
            handleException(e);
        }
    }

    public List<TR> processLogs(ProcessLogs logs) throws Exception {
        try {
            List<TR> result = Lists.newArrayList();
            Map<TaskCreateLog, TaskEndLog> taskLogs = logs.getTaskLogs();
            for (ProcessLog log : logs.getLogs()) {
                if (log instanceof TaskCreateLog) {
                    TaskCreateLog createLog = (TaskCreateLog) log;
                    TaskEndLog endLog = taskLogs.get(createLog);
                    result.add(populateTaskRow(createLog, endLog));
                }
            }
            return result;
        } catch (Exception e) {
            throw new JspException(e);
        }
    }

    private TR populateTaskRow(TaskCreateLog createLog, TaskEndLog endLog) {
        Calendar taskCreateDate = CalendarUtil.dateToCalendar(createLog.getDate());
        Calendar taskEndDate = null;
        String taskEndDateString = "";
        String actorName = null;
        if (endLog != null) {
            taskEndDate = CalendarUtil.dateToCalendar(endLog.getDate());
            taskEndDateString = CalendarUtil.formatDateTime(taskEndDate);
            actorName = endLog.getExecutorName();
        }
        TR tr = new TR();
        tr.addElement(new TD().addElement(createLog.getTaskName()).setClass(Resources.CLASS_LIST_TABLE_TD));
        tr.addElement(new TD().addElement(actorName).setClass(Resources.CLASS_LIST_TABLE_TD));
        tr.addElement(new TD().addElement(CalendarUtil.formatDateTime(taskCreateDate)).setClass(Resources.CLASS_LIST_TABLE_TD));
        tr.addElement(new TD().addElement(taskEndDateString).setClass(Resources.CLASS_LIST_TABLE_TD));
        String period = "";
        if (taskEndDate != null) {
            int days = (int) CalendarUtil.daysBetween(taskCreateDate, taskEndDate);
            if (days > 1) {
                period = days + " days ";
            }
            long periodMillis = taskEndDate.getTimeInMillis() - taskCreateDate.getTimeInMillis();
            Calendar periodCal = Calendar.getInstance();
            periodCal.setTimeInMillis(periodMillis - periodCal.getTimeZone().getOffset(periodMillis));
            period += CalendarUtil.format(periodCal, CalendarUtil.HOURS_MINUTES_SECONDS_FORMAT);
        }
        tr.addElement(new TD().addElement(period).setClass(Resources.CLASS_LIST_TABLE_TD));
        return tr;
    }

    private class TasksHistoryHeaderBuilder implements HeaderBuilder {

        @Override
        public TR build() {
            TR tr = new TR();
            tr.addElement(new TH(Messages.getMessage(Messages.LABEL_TASK_HISTORY_TABLE_TASK_NAME, pageContext))
                    .setClass(Resources.CLASS_LIST_TABLE_TH));
            tr.addElement(new TH(Messages.getMessage(Messages.LABEL_TASK_HISTORY_TABLE_EXECUTOR, pageContext))
                    .setClass(Resources.CLASS_LIST_TABLE_TH));
            tr.addElement(new TH(Messages.getMessage(Messages.LABEL_TASK_HISTORY_TABLE_START_DATE, pageContext))
                    .setClass(Resources.CLASS_LIST_TABLE_TH));
            tr.addElement(new TH(Messages.getMessage(Messages.LABEL_TASK_HISTORY_TABLE_END_DATE, pageContext))
                    .setClass(Resources.CLASS_LIST_TABLE_TH));
            tr.addElement(new TH(Messages.getMessage(Messages.LABEL_TASK_HISTORY_TABLE_DURATION, pageContext))
                    .setClass(Resources.CLASS_LIST_TABLE_TH));
            return tr;
        }
    }

    @Override
    protected Permission getPermission() throws JspException {
        return ProcessPermission.READ;
    }

    @Override
    protected String getTitle() {
        return Messages.getMessage(Messages.LABEL_TASK_HISTORY_TABLE_NAME, pageContext);
    }

    @Override
    public String getAction() {
        return CancelProcessAction.ACTION_PATH;
    }

    @Override
    protected boolean isFormButtonVisible() {
        return false;
    }
}