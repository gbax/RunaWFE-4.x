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
package ru.runa.af;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Permissions for relations.
 * @see {@link Permission}
 */
public class RelationPermission extends Permission {

    private static final long serialVersionUID = 1L;

    /**
     * Name of permission for relation update. Update permission allows adding/deleting and so on.
     */
    public static final String UPDATE_RELATION_PERMISSION_NAME = "permission.update_relation";

    /**
     * Permission for relation update.
     */
    public static final Permission UPDATE_RELATION = new Permission((byte) 2, UPDATE_RELATION_PERMISSION_NAME);

    /**
     * All permissions, declared for relations.
     */
    private static final List<Permission> RELATION_PERMISSIONS = fillPermissions();

    public RelationPermission() {
        super();
    }

    @Override
    public List<Permission> getAllPermissions() {
        return Lists.newArrayList(RELATION_PERMISSIONS);
    }

    private static List<Permission> fillPermissions() {
        List<Permission> superPermissions = new Permission().getAllPermissions();
        List<Permission> result = Lists.newArrayList(superPermissions);
        result.add(UPDATE_RELATION);
        return result;
    }

}
