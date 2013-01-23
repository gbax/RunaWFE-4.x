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
package ru.runa.wf.logic.bot.mswordreport;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Map;

import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.var.IVariableProvider;
import ru.runa.wfe.var.dto.WfVariable;

import com.google.common.collect.Maps;

/**
 * 
 * Created on 24.11.2006
 * 
 */
public class MSWordReportTaskSettings {
    private final String templateFileLocation;
    private final String reportFileName;
    private final String reportVariableName;
    private final Map<String, BookmarkVariableMapping> bookmarkMapping = Maps.newHashMap();

    public MSWordReportTaskSettings(String templateFileLocation, String reportFileName, String reportVariableName) {
        this.templateFileLocation = templateFileLocation;
        this.reportFileName = reportFileName;
        this.reportVariableName = reportVariableName;
    }

    public String getReportFileName() {
        return reportFileName;
    }

    public String getReportVariableName() {
        return reportVariableName;
    }

    public String getTemplateFileLocation() {
        return templateFileLocation;
    }

    public String getTemplateFilePath() {
        File file = new File(templateFileLocation);
        if (file.exists()) {
            return templateFileLocation;
        }
        try {
            file = new File(getClass().getResource(templateFileLocation).toURI());
            if (file.exists()) {
                return file.getAbsolutePath();
            }
            throw new InternalApplicationException("No template found at location '" + templateFileLocation + "'");
        } catch (URISyntaxException e) {
            throw new InternalApplicationException("No template found at location '" + templateFileLocation + "'", e);
        }
    }

    public void addBookmarkMapping(BookmarkVariableMapping bookmarkVariableMapping) {
        bookmarkMapping.put(bookmarkVariableMapping.getBookmarkName(), bookmarkVariableMapping);
    }

    public Map<String, BookmarkVariableMapping> getBookmarkMapping() {
        return bookmarkMapping;
    }

    public String format(String bookmark, IVariableProvider variableProvider) {
        BookmarkVariableMapping bookmarkVariableMapping = bookmarkMapping.get(bookmark);
        if (bookmarkVariableMapping == null) {
            throw new IllegalArgumentException("In template found not mapped by bot task configuration bookmark '" + bookmark + "'");
        }
        WfVariable variable = variableProvider.getVariableNotNull(bookmarkVariableMapping.getVariableName());
        return variable.getDefinition().getFormat().format(variable.getValue());
    }

}
