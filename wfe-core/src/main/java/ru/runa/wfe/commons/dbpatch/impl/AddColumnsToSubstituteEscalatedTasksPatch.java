package ru.runa.wfe.commons.dbpatch.impl;

import java.sql.Types;
import java.util.List;

import org.hibernate.Session;

import ru.runa.wfe.commons.dbpatch.DBPatch;

public class AddColumnsToSubstituteEscalatedTasksPatch extends DBPatch {

    @Override
    protected List<String> getDDLQueriesBefore() {
        List<String> sql = super.getDDLQueriesAfter();

        // sql.add(getDDLRemoveColumn("EXECUTOR", "PROCESS_ID"));
        // sql.add(getDDLRemoveColumn("EXECUTOR", "NODE_ID"));

        sql.add(getDDLCreateColumn("EXECUTOR", new ColumnDef("PROCESS_ID", Types.BIGINT, true)));
        sql.add(getDDLCreateColumn("EXECUTOR", new ColumnDef("NODE_ID", dialect.getTypeName(Types.VARCHAR, 255, 255, 255), true)));

        log.info(String.format("getDDLQueriesBefore: sql: %s", sql));

        return sql;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void applyPatch(Session session) throws Exception {
        List<Number> ids = session.createSQLQuery("SELECT ID FROM EXECUTOR WHERE DISCRIMINATOR IN ('E', 'T')").list();

        for (Number id : ids) {
            try {
                log.info(String.format("applyPatch: id: %s set PROCESS_ID", id));
                session.createSQLQuery(String.format("UPDATE EXECUTOR SET PROCESS_ID=0 WHERE ID=%s", id)).executeUpdate();
            } catch (Exception e) {
                log.warn(String.format("applyPatch: set PROCESS_ID for id: %s exc: %s", id, e));
            }
        }

        ids = session.createSQLQuery("SELECT ID FROM EXECUTOR WHERE DISCRIMINATOR IN ('E')").list();

        for (Number id : ids) {
            log.info(String.format("applyPatch: id: %s set NODE_ID", id));
            try {
                session.createSQLQuery(String.format("UPDATE EXECUTOR SET NODE_ID='' WHERE ID=%s", id)).executeUpdate();
            } catch (Exception e) {
                log.warn(String.format("applyPatch: set NODE_ID for id: %s exc: %s", id, e));
            }
        }
    }

}
