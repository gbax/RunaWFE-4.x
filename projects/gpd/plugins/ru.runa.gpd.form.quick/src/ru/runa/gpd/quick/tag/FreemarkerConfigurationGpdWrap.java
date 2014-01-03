package ru.runa.gpd.quick.tag;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import ru.runa.wfe.commons.xml.XmlUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

@SuppressWarnings("unchecked")
public class FreemarkerConfigurationGpdWrap {
    private static final String CONFIG = "ftl.form.tags.xml";
    private static final String TAG_ELEMENT = "ftltag";
    private static final String NAME_ATTR = "name";
    private static final String CLASS_ATTR = "class";
    private final Map<String, Class<? extends FreemarkerTagGpdWrap>> tags = Maps.newHashMap();

    private static FreemarkerConfigurationGpdWrap instance;

    public static FreemarkerConfigurationGpdWrap getInstance() {
        if (instance == null) {
            instance = new FreemarkerConfigurationGpdWrap();
        }
        return instance;
    }

    public String getRegistrationInfo() {
        return Joiner.on(", ").join(tags.values());
    }

    private FreemarkerConfigurationGpdWrap() {
        parseTags(CONFIG);
        //parseTags(SystemProperties.RESOURCE_EXTENSION_PREFIX + CONFIG, false);
    }

    private void parseTags(String fileName)  {
        InputStream is = null;
        Bundle bundle = Platform.getBundle("ru.runa.gpd.form.quick");
        //if (required) {
            //is = ClassLoaderUtil.getAsStreamNotNull(fileName, getClass());
        	try {
				is = bundle.getEntry(fileName).openStream();
			} catch (IOException e) {
				
			}
        /*} else {
            is = ClassLoaderUtil.getAsStream(fileName, getClass());
        }*/
        if (is != null) {
            Document document = XmlUtils.parseWithoutValidation(is);
            Element root = document.getRootElement();
            List<Element> tagElements = root.elements(TAG_ELEMENT);
            for (Element tagElement : tagElements) {
                String name = tagElement.attributeValue(NAME_ATTR);
                try {
                    String className = tagElement.attributeValue(CLASS_ATTR);
                    Class<? extends FreemarkerTagGpdWrap> tagClass = (Class<? extends FreemarkerTagGpdWrap>) bundle.loadClass(className);
                    addTag(name, tagClass);
                } catch (Throwable e) {
                    LogFactory.getLog(getClass()).warn("Unable to create freemarker tag " + name, e);
                }
            }
        }
    }

    private void addTag(String name, Class<? extends FreemarkerTagGpdWrap> tagClass) {
        tags.put(name, tagClass);
    }

    public FreemarkerTagGpdWrap getTag(String name) throws InstantiationException, IllegalAccessException {
        /*if (!tags.containsKey(name)) {
            String possibleTagClassName = "ru.runa.wf.web.ftl.method." + name + "Tag";
            try {
                Class<? extends FreemarkerTag> tagClass = (Class<? extends FreemarkerTag>) ClassLoaderUtil.loadClass(possibleTagClassName);
                addTag(name, tagClass);
            } catch (Exception e) {
                log.warn("Unable to load tag " + name + " as " + possibleTagClassName + ". Check your " + CONFIG);
                addTag(name, null);
            }
        }*/
        Class<? extends FreemarkerTagGpdWrap> tagClass = tags.get(name);
        if(tagClass != null) {
        return tagClass.newInstance();
        }
        return null;
    }
    
    public Set<String> getTagsName() {
    	return tags.keySet();
    }
}
