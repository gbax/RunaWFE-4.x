package ru.runa.wf.web.datafile.builder;

import java.util.List;
import java.util.zip.ZipOutputStream;

import org.dom4j.Document;
import org.dom4j.Element;

import ru.runa.wfe.bot.BotStation;
import ru.runa.wfe.commons.xml.XmlUtils;
import ru.runa.wfe.definition.dto.WfDefinition;
import ru.runa.wfe.presentation.BatchPresentationFactory;
import ru.runa.wfe.relation.Relation;
import ru.runa.wfe.security.Identifiable;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.user.Actor;
import ru.runa.wfe.user.Executor;
import ru.runa.wfe.user.Group;
import ru.runa.wfe.user.User;

public class PermissionsDataFileBuilder implements DataFileBuilder {
    private final List<? extends Identifiable> identifiablies;
    private final String xmlElement;
    private final User user;

    public PermissionsDataFileBuilder(User user, List<? extends Identifiable> identifiablies, String xmlElement) {
        this.user = user;
        this.identifiablies = identifiablies;
        this.xmlElement = xmlElement;
    }

    @Override
    public void build(ZipOutputStream zos, Document script) {
        for (Identifiable identifiable : identifiablies) {
            List<Executor> executors = Delegates.getAuthorizationService().getExecutorsWithPermission(user, identifiable,
                    BatchPresentationFactory.EXECUTORS.createDefault(), true);
            for (Executor executor : executors) {
                List<Permission> permissions = Delegates.getAuthorizationService().getIssuedPermissions(user, executor, identifiable);
                if (permissions.isEmpty()) {
                    // this is the case for privileged executors
                    continue;
                }
                Element element = script.getRootElement().addElement(xmlElement, XmlUtils.RUNA_NAMESPACE);
                if (!"addPermissionsOnBotStations".equals(xmlElement) && !"addPermissionsOnSystem".equals(xmlElement)) {
                    element.addAttribute("name", getIdentifiableName(identifiable));
                }
                element.addAttribute("executor", executor.getName());
                for (Permission permission : permissions) {
                    Element permissionElement = element.addElement("permission", XmlUtils.RUNA_NAMESPACE);
                    permissionElement.addAttribute("name", permission.getName());
                }
            }
        }
    }

    private String getIdentifiableName(Identifiable identifiable) {
        if (identifiable instanceof Actor) {
            return ((Actor) identifiable).getName();
        }
        if (identifiable instanceof Group) {
            return ((Group) identifiable).getName();
        }
        if (identifiable instanceof WfDefinition) {
            return ((WfDefinition) identifiable).getName();
        }
        if (identifiable instanceof BotStation) {
            return ((BotStation) identifiable).getName();
        }
        if (identifiable instanceof Relation) {
            return ((Relation) identifiable).getName();
        }
        return "";
    }
}
