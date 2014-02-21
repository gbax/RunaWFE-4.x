package ru.runa.wfe.commons;

import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.runa.wfe.commons.cache.CachingLogic;

import com.google.common.base.Throwables;

public abstract class TransactionalExecutor {
    protected final Log log = LogFactory.getLog(getClass());
    private final UserTransaction transaction;

    public TransactionalExecutor(UserTransaction transaction) {
        this.transaction = transaction;
    }

    public TransactionalExecutor() {
        this(Utils.getUserTransaction());
    }

    public final void executeInTransaction(boolean throwExceptionOnError) {
        try {
            transaction.begin();
            doExecuteInTransaction();
            transaction.commit();
        } catch (Throwable th) {
            Utils.rollbackTransaction(transaction);
            if (throwExceptionOnError) {
                throw Throwables.propagate(th);
            } else {
                log.error("", th);
            }
        } finally {
            CachingLogic.onTransactionComplete();
        }
    }

    protected abstract void doExecuteInTransaction() throws Exception;

}
