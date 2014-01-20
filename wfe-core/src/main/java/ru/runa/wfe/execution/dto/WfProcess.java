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
package ru.runa.wfe.execution.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import ru.runa.wfe.execution.Process;
import ru.runa.wfe.security.IdentifiableBase;
import ru.runa.wfe.security.SecuredObjectType;

import com.google.common.base.Objects;

/**
 * Created on 02.11.2004
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WfProcess extends IdentifiableBase {
    private static final long serialVersionUID = 4862220986262286596L;
    public static final String SELECTED_TRANSITION_KEY = "RUNAWFE_SELECTED_TRANSITION";

    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private int version;
    private Long definitionId;
    private String hierarchySubProcess;

    public WfProcess() {
    }

    public WfProcess(Process process) {
        id = process.getId();
        name = process.getDeployment().getName();
        definitionId = process.getDeployment().getId();
        version = process.getDeployment().getVersion().intValue();
        startDate = process.getStartDate();
        endDate = process.getEndDate();
        hierarchySubProcess = process.getHierarchySubProcess();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public SecuredObjectType getSecuredObjectType() {
        return SecuredObjectType.PROCESS;
    }

    public String getName() {
        return name;
    }

    /**
     * @return true if process is ended.
     */
    public boolean isEnded() {
        return endDate != null;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getVersion() {
        return version;
    }

    public Long getDefinitionId() {
        return definitionId;
    }

    public String getHierarchySubProcess() {
        return hierarchySubProcess;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WfProcess) {
            return Objects.equal(id, ((WfProcess) obj).id);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", id).add("name", name).toString();
    }

}