package ru.runa.gpd.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

import ru.runa.gpd.BotCache;
import ru.runa.gpd.Localization;
import ru.runa.gpd.PluginLogger;
import ru.runa.gpd.ProcessCache;
import ru.runa.gpd.editor.BotTaskEditor;
import ru.runa.gpd.editor.ProcessEditorBase;
import ru.runa.gpd.editor.gef.GEFProcessEditor;
import ru.runa.gpd.editor.graphiti.GraphitiProcessEditor;
import ru.runa.gpd.lang.Language;
import ru.runa.gpd.lang.ProcessSerializer;
import ru.runa.gpd.lang.model.BotTask;
import ru.runa.gpd.lang.model.ProcessDefinition;
import ru.runa.gpd.lang.model.Subprocess;
import ru.runa.gpd.lang.model.SubprocessDefinition;
import ru.runa.gpd.lang.model.TaskState;
import ru.runa.gpd.lang.par.ParContentProvider;
import ru.runa.gpd.ui.custom.Dialogs;
import ru.runa.gpd.ui.dialog.RenameBotDialog;
import ru.runa.gpd.ui.dialog.RenameBotStationDialog;
import ru.runa.gpd.ui.dialog.RenameBotTaskDialog;
import ru.runa.gpd.ui.dialog.RenameProcessDefinitionDialog;
import ru.runa.gpd.ui.wizard.CompactWizardDialog;
import ru.runa.gpd.ui.wizard.CopyBotTaskWizard;
import ru.runa.gpd.ui.wizard.CopyProcessDefinitionWizard;
import ru.runa.gpd.ui.wizard.ExportBotElementWizardPage;
import ru.runa.gpd.ui.wizard.ExportBotWizard;
import ru.runa.gpd.ui.wizard.ExportParWizard;
import ru.runa.gpd.ui.wizard.ImportBotElementWizardPage;
import ru.runa.gpd.ui.wizard.ImportBotWizard;
import ru.runa.gpd.ui.wizard.ImportParWizard;
import ru.runa.gpd.ui.wizard.NewBotStationWizard;
import ru.runa.gpd.ui.wizard.NewBotTaskWizard;
import ru.runa.gpd.ui.wizard.NewBotWizard;
import ru.runa.gpd.ui.wizard.NewFolderWizard;
import ru.runa.gpd.ui.wizard.NewProcessDefinitionWizard;
import ru.runa.gpd.ui.wizard.NewProcessProjectWizard;
import ru.runa.wfe.definition.ProcessDefinitionAccessType;

import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public class WorkspaceOperations {
    public static void deleteResources(List<IResource> resources) {
        List<IFile> deletedDefinitions = new ArrayList<IFile>();
        for (IResource resource : resources) {
            try {
                resource.refreshLocal(IResource.DEPTH_INFINITE, null);
                boolean projectResource = (resource instanceof IProject);
                boolean folderResource = (resource instanceof IFolder);
                boolean fileResource = (resource instanceof IFile);
                String messageKey;
                if (projectResource) {
                    messageKey = "Delete.project.message";
                } else if (folderResource) {
                    if (IOUtils.isProcessDefinitionFolder((IFolder) resource)) {
                        messageKey = "Delete.process.message";
                    } else {
                        messageKey = "Delete.folder.message";
                    }
                } else if (fileResource) {
                    messageKey = "Delete.process.message";
                } else {
                    throw new IllegalArgumentException("Unexpected " + resource);
                }
                if (Dialogs.confirm(Localization.getString(messageKey, resource.getName()))) {
                    List<IFile> tmpFiles = new ArrayList<IFile>();
                    if (projectResource) {
                        tmpFiles.addAll(IOUtils.getProcessDefinitionFiles((IProject) resource));
                    } else if (folderResource) {
                        if (IOUtils.isProcessDefinitionFolder((IFolder) resource)) {
                            tmpFiles.add(IOUtils.getProcessDefinitionFile((IFolder) resource));
                        } else {
                            tmpFiles.addAll(IOUtils.getProcessDefinitionFiles((IFolder) resource));
                        }
                    } else {
                        IFile definitionFile = (IFile) resource;
                        try {
                            SubprocessDefinition subprocessDefinition = (SubprocessDefinition) ProcessCache.getProcessDefinition(definitionFile);
                            subprocessDefinition.getParent().getEmbeddedSubprocesses().remove(subprocessDefinition.getId());
                        } catch (Exception e) {
                            PluginLogger.logErrorWithoutDialog("Unable to deregister embedded subprocess", e);
                        }
                        int index = definitionFile.getName().indexOf(ParContentProvider.PROCESS_DEFINITION_FILE_NAME);
                        Preconditions.checkArgument(index != -1, "not a subprocess definition file");
                        String subprocessFileStart = definitionFile.getName().substring(0, index);
                        tmpFiles.add(definitionFile);
                        for (IResource testResource : definitionFile.getParent().members()) {
                            if (testResource.getName().startsWith(subprocessFileStart)) {
                                testResource.delete(true, null);
                            }
                        }
                    }
                    resource.delete(true, null);
                    deletedDefinitions.addAll(tmpFiles);
                }
            } catch (CoreException e) {
                PluginLogger.logError("Error deleting", e);
            }
        }
        for (IFile definitionFile : deletedDefinitions) {
            ProcessCache.processDefinitionWasDeleted(definitionFile);
        }
    }

    private static String getConfirmMessage(IResource resource) {
        if (resource instanceof IProject) {
            return "Delete.botStation.message";
        } else if (resource instanceof IFolder) {
            return "Delete.bot.message";
        } else {
            return "Delete.botTask.message";
        }
    }

    public static void refreshResources(List<IResource> resources) {
        for (IResource resource : resources) {
            refreshResource(resource);
        }
    }

    public static void refreshResource(IResource resource) {
        try {
            resource.refreshLocal(IResource.DEPTH_INFINITE, null);
            IOUtils.setUtfCharsetRecursively(resource);
        } catch (CoreException e) {
            PluginLogger.logError("Unable to refresh resource " + resource, e);
        }
    }

    public static void createNewProject() {
        NewProcessProjectWizard wizard = new NewProcessProjectWizard();
        wizard.init(PlatformUI.getWorkbench(), null);
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
        dialog.open();
    }

    public static void createNewFolder(IStructuredSelection selection) {
        NewFolderWizard wizard = new NewFolderWizard();
        wizard.init(PlatformUI.getWorkbench(), selection);
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
        dialog.open();
    }

    public static ProcessDefinition createNewProcessDefinition(IStructuredSelection selection, ProcessDefinitionAccessType accessType) {
        NewProcessDefinitionWizard wizard = new NewProcessDefinitionWizard(accessType);
        wizard.init(PlatformUI.getWorkbench(), selection);
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
        if (dialog.open() == Window.OK) {
            return ProcessCache.getProcessDefinition(wizard.getDefinitionFile());
        }
        return null;
    }

    public static void copyProcessDefinition(IStructuredSelection selection) {
        IFolder processDefinitionFolder = (IFolder) selection.getFirstElement();
        IDE.saveAllEditors(new IResource[] { processDefinitionFolder }, true);
        CopyProcessDefinitionWizard wizard = new CopyProcessDefinitionWizard();
        wizard.init(PlatformUI.getWorkbench(), selection);
        CompactWizardDialog dialog = new CompactWizardDialog(wizard);
        dialog.open();
    }

    public static void renameProcessDefinition(IStructuredSelection selection) {
        IFolder definitionFolder = (IFolder) selection.getFirstElement();
        IFile definitionFile = IOUtils.getProcessDefinitionFile(definitionFolder);
        RenameProcessDefinitionDialog dialog = new RenameProcessDefinitionDialog(definitionFolder);
        ProcessDefinition definition = ProcessCache.getProcessDefinition(definitionFile);
        dialog.setName(definition.getName());
        if (dialog.open() == IDialogConstants.OK_ID) {
            String newName = dialog.getName();
            try {
                IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                IEditorPart editor = page.findEditor(new FileEditorInput(definitionFile));
                if (editor != null) {
                    page.closeEditor(editor, false);
                }
                IPath oldPath = definitionFolder.getFullPath();
                IPath newPath = definitionFolder.getParent().getFolder(new Path(newName)).getFullPath();
                definitionFolder.copy(newPath, true, null);
                ProcessCache.processDefinitionWasDeleted(definitionFile);
                definitionFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(newPath);
                IFile newDefinitionFile = IOUtils.getProcessDefinitionFile(definitionFolder);
                definition.setName(newName);
                saveProcessDefinition(newDefinitionFile, definition);
                ProcessCache.newProcessDefinitionWasCreated(definitionFile);
                ResourcesPlugin.getWorkspace().getRoot().getFolder(oldPath).delete(true, null);
                refreshResource(definitionFolder);
            } catch (Exception e) {
                PluginLogger.logError(e);
            }
        }
    }

    public static void saveProcessDefinition(IFile definitionFile, ProcessDefinition definition) throws Exception {
        ProcessSerializer serializer = definition.getLanguage().getSerializer();
        Document document = serializer.getInitialProcessDefinitionDocument(definition.getName(), null);
        serializer.saveToXML(definition, document);
        byte[] bytes = XmlUtil.writeXml(document);
        ParContentProvider.saveAuxInfo(definitionFile, definition);
        definitionFile.setContents(new ByteArrayInputStream(bytes), true, true, null);
    }

    public static ProcessEditorBase openProcessDefinition(IFile definitionFile) {
        try {
            ProcessDefinition processDefinition = ProcessCache.getProcessDefinition(definitionFile);
            String editorId;
            if (processDefinition.getLanguage() == Language.BPMN) {
                editorId = GraphitiProcessEditor.ID;
            } else {
                editorId = GEFProcessEditor.ID;
            }
            IEditorPart editorPart = IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), definitionFile, editorId, true);
            if (editorPart instanceof ProcessEditorBase) {
                return (ProcessEditorBase) editorPart;
            }
        } catch (PartInitException e) {
            PluginLogger.logError("Unable open diagram", e);
        }
        return null;
    }

    /**
     * @return process editor or <code>null</code>
     */
    public static void openSubprocessDefinition(Subprocess subprocess) {
        if (Strings.isNullOrEmpty(subprocess.getSubProcessName())) {
            return;
        }
        if (subprocess.isEmbedded()) {
            SubprocessDefinition definition = subprocess.getProcessDefinition().getEmbeddedSubprocessByName(subprocess.getSubProcessName());
            String id = definition.getId();
            IFile definitionFile = IOUtils.getFile(id + "." + ParContentProvider.PROCESS_DEFINITION_FILE_NAME);
            openProcessDefinition(definitionFile);
        } else {
            IFile definitionFile = ProcessCache.getFirstProcessDefinitionFile(subprocess.getSubProcessName());
            if (definitionFile != null) {
                openProcessDefinition(definitionFile);
            }
        }
    }

    public static void exportProcessDefinition(IStructuredSelection selection) {
        ExportParWizard wizard = new ExportParWizard();
        wizard.init(PlatformUI.getWorkbench(), selection);
        CompactWizardDialog dialog = new CompactWizardDialog(wizard);
        dialog.open();
    }

    public static void importProcessDefinition(IStructuredSelection selection) {
        ImportParWizard wizard = new ImportParWizard();
        wizard.init(PlatformUI.getWorkbench(), selection);
        CompactWizardDialog dialog = new CompactWizardDialog(wizard);
        dialog.open();
    }

    public static void deleteBotResources(List<IResource> resources) {
        for (IResource resource : resources) {
            try {
                resource.refreshLocal(IResource.DEPTH_INFINITE, null);
                if (Dialogs.confirm(Localization.getString(getConfirmMessage(resource), resource.getName()))) {
                    if (resource instanceof IFile) {
                        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                        IEditorPart editor = page.findEditor(new FileEditorInput((IFile) resource));
                        if (editor != null) {
                            page.closeEditor(editor, false);
                        }
                    }
                    resource.delete(true, null);
                }
            } catch (CoreException e) {
                PluginLogger.logError("Error deleting", e);
            }
        }
        BotCache.reload();
    }

    public static void createNewBotStation(IStructuredSelection selection) {
        NewBotStationWizard wizard = new NewBotStationWizard();
        wizard.init(PlatformUI.getWorkbench(), selection);
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
        if (dialog.open() == IDialogConstants.OK_ID) {
            BotCache.reload();
        }
    }

    public static void createNewBot(IStructuredSelection selection) {
        NewBotWizard wizard = new NewBotWizard();
        wizard.init(PlatformUI.getWorkbench(), selection);
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
        if (dialog.open() == IDialogConstants.OK_ID) {
            BotCache.reload();
        }
    }

    public static void createNewBotTask(IStructuredSelection selection, boolean parameterized) {
        NewBotTaskWizard wizard = new NewBotTaskWizard(parameterized);
        wizard.init(PlatformUI.getWorkbench(), selection);
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
        dialog.open();
    }

    public static void saveBotTask(IFile botTaskFile, BotTask botTask) throws CoreException {
        InputStream inputStream = BotTaskUtils.createBotTaskInfo((IFolder) botTaskFile.getParent(), botTask);
        if (botTaskFile.exists()) {
            botTaskFile.setContents(inputStream, true, true, null);
        } else {
            IOUtils.createFile(botTaskFile, inputStream);
        }
    }

    public static void openBotTask(IFile botTaskFile) {
        try {
            IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), botTaskFile, BotTaskEditor.ID, true);
        } catch (PartInitException e) {
            PluginLogger.logError("Unable open bot task", e);
        }
    }

    public static void exportBotElement(IStructuredSelection selection, ExportBotElementWizardPage page) {
        ExportBotWizard wizard = new ExportBotWizard(page);
        wizard.init(PlatformUI.getWorkbench(), selection);
        CompactWizardDialog dialog = new CompactWizardDialog(wizard);
        dialog.open();
    }

    public static void importBotElement(IStructuredSelection selection, ImportBotElementWizardPage page) {
        ImportBotWizard wizard = new ImportBotWizard(page);
        wizard.init(PlatformUI.getWorkbench(), selection);
        CompactWizardDialog dialog = new CompactWizardDialog(wizard);
        if (dialog.open() == IDialogConstants.OK_ID) {
            BotCache.reload();
        }
    }

    @SuppressWarnings("unchecked")
    public static void renameBotStationFolder(IStructuredSelection selection) {
        try {
            IProject botStationProject = (IProject) selection.getFirstElement();
            IFile botStationFile = botStationProject.getFolder("/src/botstation/").getFile("botstation");
            BufferedReader botStationReader = new BufferedReader(new InputStreamReader(botStationFile.getContents(), Charsets.UTF_8));
            String oldName = botStationReader.readLine();
            String oldRmi = botStationReader.readLine();
            botStationReader.close();
            RenameBotStationDialog dialog = new RenameBotStationDialog(oldName, oldRmi);
            if (dialog.open() == IDialogConstants.OK_ID) {
                String newName = dialog.getName();
                String newRmi = dialog.getRmi();
                if (!newName.equals(oldName)) {
                    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                    List<IFolder> bots = IOUtils.getBotFolders(botStationProject);
                    for (IFolder botFolder : bots) {
                        List<IFile> botTasks = IOUtils.getBotTaskFiles(botFolder);
                        for (IFile botTask : botTasks) {
                            IEditorPart editor = page.findEditor(new FileEditorInput(botTask));
                            if (editor != null) {
                                page.closeEditor(editor, false);
                            }
                        }
                    }
                    IPath newPath = ResourcesPlugin.getWorkspace().getRoot().getProject(newName).getFullPath(); // botStationProject.getParent().getFolder(new
                                                                                                                // Path(newName)).getFullPath();
                    botStationProject.copy(newPath, true, null);
                    botStationProject = ResourcesPlugin.getWorkspace().getRoot().getProject(newName);
                    // rename in file
                    IFile file = botStationProject.getFolder("/src/botstation/").getFile("botstation");
                    file.setContents(BotTaskUtils.createBotStationInfo(newName, newRmi), true, true, null);
                    ResourcesPlugin.getWorkspace().getRoot().getProject(oldName).delete(true, null);
                } else if (!newRmi.equals(oldRmi)) {
                    IFile file = botStationProject.getFolder("/src/botstation/").getFile("botstation");
                    file.setContents(BotTaskUtils.createBotStationInfo(newName, newRmi), true, true, null);
                    refreshResources(selection.toList());
                }
            }
            BotCache.reload();
        } catch (Exception e) {
            PluginLogger.logError(e);
        }
    }

    public static void renameBotFolder(IStructuredSelection selection) {
        IFolder botFolder = (IFolder) selection.getFirstElement();
        RenameBotDialog dialog = new RenameBotDialog(botFolder);
        dialog.setName(botFolder.getName());
        if (dialog.open() == IDialogConstants.OK_ID) {
            String newName = dialog.getName();
            try {
                IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                List<IFile> botTasks = IOUtils.getBotTaskFiles(botFolder);
                for (IFile botTask : botTasks) {
                    IEditorPart editor = page.findEditor(new FileEditorInput(botTask));
                    if (editor != null) {
                        page.closeEditor(editor, false);
                    }
                }
                IPath oldPath = botFolder.getFullPath();
                IPath newPath = botFolder.getParent().getFolder(new Path(newName)).getFullPath();
                botFolder.copy(newPath, true, null);
                botFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(newPath);
                ResourcesPlugin.getWorkspace().getRoot().getFolder(oldPath).delete(true, null);
                BotCache.reload();
            } catch (Exception e) {
                PluginLogger.logError(e);
            }
        }
    }

    public static void renameBotTaskFile(IStructuredSelection selection) {
        IFile botTaskFile = (IFile) selection.getFirstElement();
        RenameBotTaskDialog dialog = new RenameBotTaskDialog(botTaskFile);
        dialog.setName(botTaskFile.getName());
        if (dialog.open() == IDialogConstants.OK_ID) {
            IFolder botFolder = (IFolder) botTaskFile.getParent();
            List<String> dependentDefinitions = validateDependentTasks(botFolder, botTaskFile.getName());
            if (dependentDefinitions.size() > 0) {
                showDependentTasksDialog(dependentDefinitions);
                return;
            }
            String newName = dialog.getName();
            try {
                IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                IEditorPart editor = page.findEditor(new FileEditorInput(botTaskFile));
                if (editor != null) {
                    page.closeEditor(editor, false);
                }
                IPath oldPath = botTaskFile.getFullPath();
                IPath newPath = botTaskFile.getParent().getFullPath().append(newName);
                botTaskFile.copy(newPath, true, null);
                IFile confFile = ResourcesPlugin.getWorkspace().getRoot().getFile(botTaskFile.getParent().getFullPath().append(new Path(botTaskFile.getName() + ".conf")));
                if (confFile.exists()) {
                    BotTask botTask = BotCache.getBotTaskNotNull(botTaskFile);
                    botTask.setName(newName);
                    botTaskFile = ResourcesPlugin.getWorkspace().getRoot().getFile(newPath);
                    saveBotTask(botTaskFile, botTask);
                    confFile.delete(true, null);
                }
                ResourcesPlugin.getWorkspace().getRoot().getFile(oldPath).delete(true, null);
                BotCache.reload();
            } catch (Exception e) {
                PluginLogger.logError(e);
            }
        }
    }

    private static List<String> validateDependentTasks(IFolder botFolder, String botTaskName) {
        List<String> dependentDefinitions = new ArrayList<String>();
        Set<ProcessDefinition> definitions = ProcessCache.getAllProcessDefinitions();
        for (ProcessDefinition definition : definitions) {
            for (TaskState taskState : definition.getChildren(TaskState.class)) {
                if (botTaskName.equals(taskState.getName())) {
                    if (Objects.equal(botFolder.getName(), taskState.getSwimlaneBotName())) {
                        dependentDefinitions.add(definition.getName());
                        break;
                    }
                }
            }
        }
        return dependentDefinitions;
    }

    private static void showDependentTasksDialog(List<String> dependentDefinitions) {
        StringBuffer detailsMessage = new StringBuffer();
        Iterator<String> iterator = dependentDefinitions.iterator();
        detailsMessage.append(Localization.getString("DependentTasksDialog.detailsMessage"));
        detailsMessage.append(iterator.next());
        while (iterator.hasNext()) {
            detailsMessage.append(", ");
            detailsMessage.append(iterator.next());
        }
        Dialogs.error(Localization.getString("DependentTasksDialog.errorMessage"), detailsMessage.toString());
    }

    public static void copyBotTask(IStructuredSelection selection) {
        CopyBotTaskWizard wizard = new CopyBotTaskWizard();
        wizard.init(PlatformUI.getWorkbench(), selection);
        CompactWizardDialog dialog = new CompactWizardDialog(wizard);
        dialog.open();
        BotCache.reload();
    }
}