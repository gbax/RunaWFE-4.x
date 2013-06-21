package ru.runa.wfe.service.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PerformanceObserver {
    private static final Log log = LogFactory.getLog("apicall");

    @AroundInvoke
    public Object process(InvocationContext ic) throws Exception {
        long startTime = System.currentTimeMillis();
        Object result = ic.proceed();
        long jobTime = System.currentTimeMillis() - startTime;
        if (jobTime > 1000) {
            log.info(jobTime + " milliseconds: " + DebugUtils.getDebugString(ic, true));
        }
        return result;
    }

}
