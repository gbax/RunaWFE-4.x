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
package ru.runa.common.web.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ru.runa.af.web.form.BotTasksForm.BotTaskForm;
import ru.runa.common.web.Messages;

/**
 * Created on 26.08.2014
 * 
 * @struts:form name = "propertiesFileForm"
 */
public class PropertiesFileForm extends ActionForm {

    private static final long serialVersionUID = 67L;
    private HashMap<String, PropertyForm> properties = new HashMap<String, PropertyForm>();
    
    public static String RESOURCE_INPUT_NAME = "resource";
    public static String OLD_VALUE_INPUT_NAME(String property) {
    	return "property(" + property + ").oldValue";
    }
    public static String NEW_VALUE_INPUT_NAME(String property) {
    	return "property(" + property + ").newValue";
    }
    
    private String resource;
    
    public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public Map<String, String> getModifiedProperties() {
		Map<String, String> res = new HashMap<String, String>();
		for (String p : properties.keySet()) {
			PropertyForm f = properties.get(p);
			if (!f.getNewValue().equals(f.getOldValue()))
				res.put(p, f.getNewValue());
		}
		return res;
	}
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        properties.clear();
    }
    
    public Object getProperty(String property) {
        if (!properties.containsKey(property)) {
            properties.put(property, new PropertyForm());
        }
        return properties.get(property);
    }

    public static class PropertyForm {
    	private String oldValue;
    	private String newValue;
		public String getOldValue() {
			return oldValue;
		}
		public void setOldValue(String oldValue) {
			this.oldValue = oldValue;
		}
		public String getNewValue() {
			return newValue;
		}
		public void setNewValue(String newValue) {
			this.newValue = newValue;
		}
    }
}
