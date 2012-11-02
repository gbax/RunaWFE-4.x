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
package ru.runa.wfe.os.func;

import java.util.List;

import ru.runa.wfe.commons.ArraysCommons;
import ru.runa.wfe.commons.TypeConversionUtil;
import ru.runa.wfe.os.OrgFunctionBase;
import ru.runa.wfe.os.dao.OrganizationHierarchyDAO;

/**
 * Uses first argument as SQL and the rest arguments as actor codes
 * 
 * Created on Jul 12, 2006
 * 
 */
public class SQLFunction extends OrgFunctionBase {

    @Override
    protected List<Long> getExecutorCodes(Object... parameters) {
        String sql = TypeConversionUtil.convertTo(parameters[0], String.class);
        return OrganizationHierarchyDAO.getActorCodes(sql, ArraysCommons.remove(parameters, 0));
    }
}
