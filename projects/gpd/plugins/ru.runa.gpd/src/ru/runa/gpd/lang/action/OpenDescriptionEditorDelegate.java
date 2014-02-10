package ru.runa.gpd.lang.action;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.ide.IDE;

import ru.runa.gpd.PluginLogger;
import ru.runa.gpd.lang.par.ParContentProvider;
import ru.runa.gpd.util.IOUtils;

public class OpenDescriptionEditorDelegate extends BaseModelActionDelegate {
    private static final String EDITOR_ID = "tk.eclipse.plugin.wysiwyg.WYSIWYGHTMLEditor";

    @Override
    public void run(IAction action) {
        try {
            IFile file = IOUtils.getAdjacentFile(getDefinitionFile(), ParContentProvider.PROCESS_DEFINITION_DESCRIPTION_FILE_NAME);
            if (!file.exists()) {
                IOUtils.createFile(file);
            }
            IDE.openEditor(getWorkbenchPage(), file, EDITOR_ID);
        } catch (Exception e) {
            PluginLogger.logError(e);
        }
    }
}