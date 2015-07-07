package ru.runa.wf.web.tag;

import org.apache.ecs.Entities;
import org.apache.ecs.StringElement;
import org.apache.ecs.html.Form;
import org.apache.ecs.html.Input;
import org.apache.ecs.html.Label;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.Table;

import ru.runa.common.WebResources;
import ru.runa.common.web.HTMLUtils;
import ru.runa.common.web.Messages;
import ru.runa.common.web.Resources;
import ru.runa.common.web.form.FileForm;
import ru.runa.common.web.tag.TitledFormTag;
import ru.runa.wf.web.action.ImportDataFileAction;

/**
 * 
 * @author riven
 * @jsp.tag name = "importDataFile" body-content = "JSP"
 */
public class ImportDataFileTag extends TitledFormTag {
    private static final long serialVersionUID = 1L;

    @Override
    public boolean isVisible() {
        return WebResources.getBooleanProperty("action.datafile.enabled", false);
    }

    @Override
    protected void fillFormElement(TD tdFormElement) {
        getForm().setEncType(Form.ENC_UPLOAD);
        Table table = new Table();
        table.setClass(Resources.CLASS_LIST_TABLE);
        createAddDataRow(table);
        clearPasswordRow(table);
        table.addElement(HTMLUtils.createInputRow(Messages.getMessage("managesystem.datafile.title", pageContext), FileForm.FILE_INPUT_NAME, "",
                true, true, Input.FILE));
        tdFormElement.addElement(table);
    }

    private void createAddDataRow(Table table) {
        TD td = new TD();
        Input uploadInput = new Input(Input.RADIO, ImportDataFileAction.UPLOAD_PARAM, ImportDataFileAction.UPLOAD_ONLY);
        uploadInput.setID(ImportDataFileAction.UPLOAD_ONLY);
        uploadInput.setChecked(true);
        td.addElement(uploadInput);
        Label label = new Label(ImportDataFileAction.UPLOAD_ONLY);
        label.addElement(new StringElement(Messages.getMessage("managesystem.datafile.uploadonly.label", pageContext)));
        td.addElement(label);
        td.addElement(Entities.NBSP);
        Input uploadAndClearInput = new Input(Input.RADIO, ImportDataFileAction.UPLOAD_PARAM, ImportDataFileAction.CLEAR_BEFORE_UPLOAD);
        uploadAndClearInput.setID(ImportDataFileAction.CLEAR_BEFORE_UPLOAD);
        td.addElement(uploadAndClearInput);
        label = new Label(ImportDataFileAction.CLEAR_BEFORE_UPLOAD);
        label.addElement(new StringElement(Messages.getMessage("managesystem.datafile.clearbeforeupload.label", pageContext)));
        td.addElement(label);
        table.addElement(HTMLUtils.createRow(Messages.getMessage("managesystem.datafile.action.title", pageContext), td));
    }

    private void clearPasswordRow(Table table) {
        TD td = new TD();
        Input setPasswordInput = new Input(Input.RADIO, ImportDataFileAction.PASSWORD_PARAM, ImportDataFileAction.SET_PASSWORD);
        setPasswordInput.setID(ImportDataFileAction.SET_PASSWORD);
        setPasswordInput.setChecked(true);
        td.addElement(setPasswordInput);
        Label label = new Label(ImportDataFileAction.SET_PASSWORD);
        label.addElement(new StringElement(Messages.getMessage("managesystem.datafile.set.password.label", pageContext)));
        td.addElement(label);
        td.addElement(Entities.NBSP);
        Input clearPasswordInput = new Input(Input.RADIO, ImportDataFileAction.PASSWORD_PARAM, ImportDataFileAction.CLEAR_PASSWORD);
        clearPasswordInput.setID(ImportDataFileAction.CLEAR_PASSWORD);
        td.addElement(clearPasswordInput);
        label = new Label(ImportDataFileAction.CLEAR_PASSWORD);
        label.addElement(new StringElement(Messages.getMessage("managesystem.datafile.clear.password.label", pageContext)));
        td.addElement(label);
        table.addElement(HTMLUtils.createRow(Messages.getMessage("managesystem.datafile.action.password.title", pageContext), td));

        TD passInputTd = new TD();
        Input passwordText = new Input(Input.TEXT, ImportDataFileAction.PASSWORD_VALUE_PARAM, "123");
        passwordText.setID(ImportDataFileAction.PASSWORD_VALUE_PARAM);
        passwordText.setStyle("width: 300px;");
        passInputTd.addElement(passwordText);
        table.addElement(HTMLUtils.createRow(Messages.getMessage("managesystem.datafile.password.title", pageContext), passInputTd));
    }

    @Override
    protected String getTitle() {
        return Messages.getMessage(Messages.TITLE_IMPORT_DATAFILE, pageContext);
    }

    @Override
    public String getAction() {
        return ImportDataFileAction.ACTION_PATH;
    }

    @Override
    protected String getFormButtonName() {
        return Messages.getMessage(Messages.TITLE_IMPORT_DATAFILE, pageContext);
    }
}
