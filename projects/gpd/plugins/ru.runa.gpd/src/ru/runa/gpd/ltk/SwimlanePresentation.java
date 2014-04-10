package ru.runa.gpd.ltk;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;

import ru.runa.gpd.lang.model.Swimlane;
import ru.runa.gpd.lang.model.Variable;
import ru.runa.gpd.swimlane.SwimlaneInitializer;
import ru.runa.gpd.swimlane.SwimlaneInitializerParser;

public class SwimlanePresentation extends VariableRenameProvider<Swimlane> {
    public SwimlanePresentation(Swimlane swimlane) {
        setElement(swimlane);
    }

    @Override
    public List<Change> getChanges(Variable oldVariable, Variable newVariable) throws Exception {
        List<Change> changes = new ArrayList<Change>();
        String config = element.getDelegationConfiguration();
        SwimlaneInitializer swimlaneInitializer = SwimlaneInitializerParser.parse(config);
        if (swimlaneInitializer.hasReference(oldVariable)) {
            changes.add(new SwimlaneInitializerChange(element, oldVariable.getName(), newVariable.getName()));
        }
        return changes;
    }

    private class SwimlaneInitializerChange extends TextCompareChange {
        
        public SwimlaneInitializerChange(Object element, String currentVariableName, String previewVariableName) {
            super(element, currentVariableName, previewVariableName);
        }

        @Override
        protected void performInUIThread() {
            element.setDelegationConfiguration(getReplacementConfig());
        }

        private String getReplacementConfig() {
            String config = element.getDelegationConfiguration();
            SwimlaneInitializer swimlaneInitializer = SwimlaneInitializerParser.parse(config);
            swimlaneInitializer.onVariableRename(currentVariableName, replacementVariableName);
            return swimlaneInitializer.toString();
        }

        @Override
        public String getCurrentContent(IProgressMonitor pm) throws CoreException {
            return element.getDelegationConfiguration();
        }

        @Override
        public String getPreviewContent(IProgressMonitor pm) throws CoreException {
            return getReplacementConfig();
        }

        @Override
        protected String toPreviewContent(String varName) {
            throw new UnsupportedOperationException();
        }
    }
}
