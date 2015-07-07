package ru.runa.wfe.commons.dbpatch.impl;

import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import ru.runa.wfe.commons.CalendarUtil;
import ru.runa.wfe.commons.dbpatch.DBPatch;

import com.google.common.collect.Lists;

public class AddCreateDateColumns extends DBPatch {
    private static final String COLUMN_CREATE_DATE = "CREATE_DATE";
    private static final List<String> TABLES_TO_ADD_COLUMN = Lists.newArrayList();
    static {
        TABLES_TO_ADD_COLUMN.add("BATCH_PRESENTATION");
        TABLES_TO_ADD_COLUMN.add("BOT");
        TABLES_TO_ADD_COLUMN.add("BOT_STATION");
        TABLES_TO_ADD_COLUMN.add("BOT_TASK");
        TABLES_TO_ADD_COLUMN.add("BPM_JOB");
        TABLES_TO_ADD_COLUMN.add("BPM_SUBPROCESS");
        TABLES_TO_ADD_COLUMN.add("BPM_SWIMLANE");
        TABLES_TO_ADD_COLUMN.add("BPM_VARIABLE");
        TABLES_TO_ADD_COLUMN.add("EXECUTOR");
        TABLES_TO_ADD_COLUMN.add("EXECUTOR_GROUP_MEMBER");
        TABLES_TO_ADD_COLUMN.add("EXECUTOR_RELATION");
        TABLES_TO_ADD_COLUMN.add("EXECUTOR_RELATION_PAIR");
        TABLES_TO_ADD_COLUMN.add("LOCALIZATION");
        TABLES_TO_ADD_COLUMN.add("PROFILE");
        TABLES_TO_ADD_COLUMN.add("SUBSTITUTION");
        TABLES_TO_ADD_COLUMN.add("SUBSTITUTION_CRITERIA");
        // TABLES_TO_ADD_COLUMN.add("WFE_CONSTANTS");
    }

    @Override
    protected List<String> getDDLQueriesBefore() {
        List<String> sql = super.getDDLQueriesBefore();
        sql.add(getDDLRenameColumn("BPM_LOG", "LOG_DATE", new ColumnDef(COLUMN_CREATE_DATE, Types.TIMESTAMP)));
        sql.add(getDDLRenameColumn("BPM_PROCESS_DEFINITION", "DEPLOYED", new ColumnDef(COLUMN_CREATE_DATE, Types.TIMESTAMP)));
        sql.add(getDDLRenameColumn("SYSTEM_LOG", "TIME", new ColumnDef(COLUMN_CREATE_DATE, Types.TIMESTAMP)));
        for (String tableName : TABLES_TO_ADD_COLUMN) {
            sql.add(getDDLCreateColumn(tableName, new ColumnDef(COLUMN_CREATE_DATE, Types.TIMESTAMP)));
        }
        return sql;
    }

    @Override
    protected void applyPatch(Session session) throws Exception {
        Calendar fakeCreateCalendar = CalendarUtil.getZeroTimeCalendar(Calendar.getInstance());
        fakeCreateCalendar.set(Calendar.DAY_OF_YEAR, 1);
        Date fakeCreateDate = fakeCreateCalendar.getTime();
        List<String> tablesToUpdateNullCreateDate = Lists.newArrayList(TABLES_TO_ADD_COLUMN);
        tablesToUpdateNullCreateDate.add("BPM_PROCESS_DEFINITION");
        for (String tableName : tablesToUpdateNullCreateDate) {
            String sql = "UPDATE " + tableName + " SET " + COLUMN_CREATE_DATE + "=:createDate WHERE " + COLUMN_CREATE_DATE + " IS NULL";
            SQLQuery query = session.createSQLQuery(sql);
            query.setTimestamp("createDate", fakeCreateDate);
            int rowsUpdated = query.executeUpdate();
            log.info("'" + sql + "' executed for " + rowsUpdated + " rows");
        }
    }

}
