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
package ru.runa.af.organizationfunction;

import java.util.List;

import ru.runa.af.organizationfunction.dao.OrganizationHierarchyDAO;
import ru.runa.af.organizationfunction.dao.Resources;

/**
 * 
 * Created on Jul 12, 2006
 *
 */
public class ChiefRecursiveFunction extends BaseOrganizationHierarchyFunction {

    protected List<Long> getCodes(Long code) {
        return OrganizationHierarchyDAO.getActorCodesRecurisve(Resources.getChiefCodeBySubordinateCodeSQL(), new Long[] { code });
    }
}
