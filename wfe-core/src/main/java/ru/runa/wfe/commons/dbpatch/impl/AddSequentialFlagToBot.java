package ru.runa.wfe.commons.dbpatch.impl;

import java.sql.Types;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import ru.runa.wfe.commons.dbpatch.DBPatch;

public class AddSequentialFlagToBot extends DBPatch {

    @Override
    protected List<String> getDDLQueriesBefore() {
        List<String> sql = super.getDDLQueriesBefore();
        sql.add(getDDLCreateColumn("BOT_TASK", new ColumnDef("IS_SEQUENTIAL", dialect.getTypeName(Types.BOOLEAN))));
        sql.add(getDDLCreateColumn("BOT", new ColumnDef("IS_SEQUENTIAL", dialect.getTypeName(Types.BOOLEAN))));
        return sql;
    }

    @Override
    protected void applyPatch(Session session) throws Exception {
        for (String table : new String[] { "BOT", "BOT_TASK" }) {
            String sql = "UPDATE " + table + " SET IS_SEQUENTIAL=:isSeq WHERE IS_SEQUENTIAL IS NULL";
            SQLQuery query = session.createSQLQuery(sql);
            query.setBoolean("isSeq", false);
            query.executeUpdate();
        }
    }
}
