/*
 * This file is part of the RUNA WFE project.
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation; version 2.1 
 * of the License. 
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU Lesser General Public License for more details. 
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package ru.runa.wfe.presentation.filter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ru.runa.wfe.commons.ClassLoaderUtil;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.var.Variable;
import ru.runa.wfe.var.impl.ByteArrayVariable;
import ru.runa.wfe.var.impl.DateVariable;
import ru.runa.wfe.var.impl.DoubleVariable;
import ru.runa.wfe.var.impl.LongVariable;
import ru.runa.wfe.var.impl.StringVariable;

/**
 * 
 * Created on 12.02.2007
 * 
 */
public class FilterCriteriaFactory {

    private static Map<String, FilterCriteria> filterCriterias = new HashMap<String, FilterCriteria>();

    static {
        filterCriterias.put(String.class.getName(), new StringFilterCriteria());
        filterCriterias.put(Integer.class.getName(), new IntegerFilterCriteria());
        filterCriterias.put(Variable.class.getName(), new AnywhereStringFilterCriteria());
        filterCriterias.put(ByteArrayVariable.class.getName(), new AnywhereStringFilterCriteria());
        filterCriterias.put(DateVariable.class.getName(), new AnywhereStringFilterCriteria());
        filterCriterias.put(DoubleVariable.class.getName(), new AnywhereStringFilterCriteria());
        filterCriterias.put(LongVariable.class.getName(), new AnywhereStringFilterCriteria());
        filterCriterias.put(StringVariable.class.getName(), new AnywhereStringFilterCriteria());
        filterCriterias.put(Date.class.getName(), new DateFilterCriteria());
    }

    public static FilterCriteria getFilterCriteria(BatchPresentation batchPresentation, int fieldId) {
        String fieldType = batchPresentation.getAllFields()[fieldId].fieldType;
        return getFilterCriteria(fieldType);
    }

    public static FilterCriteria getFilterCriteria(String fieldType) {
        FilterCriteria filter = filterCriterias.get(fieldType);
        if (filter != null) {
            return filter.clone();
        }
        return ClassLoaderUtil.instantiate(fieldType);
    }
}
