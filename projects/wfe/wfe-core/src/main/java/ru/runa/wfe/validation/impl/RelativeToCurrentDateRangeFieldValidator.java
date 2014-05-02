package ru.runa.wfe.validation.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import ru.runa.wfe.commons.CalendarUtil;
import ru.runa.wfe.commons.bc.BusinessCalendar;
import ru.runa.wfe.var.format.DateFormat;

public class RelativeToCurrentDateRangeFieldValidator extends AbstractRangeValidator<Date> {

    @Autowired
    private BusinessCalendar businessCalendar;

    protected boolean useBusinessCalendar() {
        return getParameter(boolean.class, "useBusinessCalendar", false);
    }

    private Date getParameter(String name, boolean add) {
        Integer daysCount = getParameter(Integer.class, name, null);
        if (daysCount == null) {
            return null;
        }
        if (!add && daysCount != 0) {
            daysCount = -1 * daysCount;
        }
        Calendar current = Calendar.getInstance();
        if (DateFormat.class.getName().equals(getVariableProvider().getVariableNotNull(getFieldName()).getDefinition().getFormat())) {
            CalendarUtil.setZeroTimeCalendar(current);
        }
        if (useBusinessCalendar()) {
            Date date = businessCalendar.apply(new Date(), daysCount + " business days");
            current.setTime(date);
        } else {
            current.add(Calendar.DAY_OF_MONTH, daysCount);
        }
        return current.getTime();
    }

    @Override
    protected Date getMaxComparatorValue() {
        return getParameter("max", true);
    }

    @Override
    protected Date getMinComparatorValue() {
        return getParameter("min", false);
    }

}
