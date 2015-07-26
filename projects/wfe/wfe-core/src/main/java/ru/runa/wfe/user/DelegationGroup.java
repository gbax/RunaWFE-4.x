package ru.runa.wfe.user;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * Delegation group
 *
 * @author groupName
 */
@Entity
@DiscriminatorValue(value = "D")
public class DelegationGroup extends TemporaryGroup {

    private static final long serialVersionUID = 1L;

    /**
     * Prefix for delegation group name.
     */
    public static final String GROUP_PREFIX = "DelegationGroup_";

    private Long processId;

    @Column(name = "PROCESS_ID")
    public long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public static DelegationGroup create(Long processId, Long taskId, User user, Executor currentOwner) {
        String groupName = String.format("%s%s_%s_%s_%s", GROUP_PREFIX, user.getActor().getId().toString(), currentOwner.getId().toString(),
                processId.toString(), taskId.toString());
        DelegationGroup delegationGroup = new DelegationGroup();
        delegationGroup.setCreateDate(new Date());
        delegationGroup.setName(groupName);
        delegationGroup.setDescription(taskId.toString());
        delegationGroup.setProcessId(processId);
        return delegationGroup;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", getId()).add("name", getName()).add("processId", getProcessId())
                .toString();
    }

}
