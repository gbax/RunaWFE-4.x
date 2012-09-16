package ru.runa.xpdl.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import ru.runa.bpm.ui.resource.Messages;

public class ImportFromXPDLWizard extends Wizard implements IImportWizard {

    private ImportFromXPDLWizardPage mainPage;

    @Override
    public boolean performFinish() {
        return mainPage.performFinish();
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        setWindowTitle(Messages.getString("ImportParWizard.wizard.title"));
        mainPage = new ImportFromXPDLWizardPage(Messages.getString("ImportParWizard.wizard.title"), selection);
    }

    @Override
    public void addPages() {
        addPage(mainPage);
    }

}
