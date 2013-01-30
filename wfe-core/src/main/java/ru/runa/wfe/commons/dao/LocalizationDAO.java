package ru.runa.wfe.commons.dao;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * DAO for managing {@link Localization}.
 * 
 * @author dofs
 * @since 4.0
 */
public class LocalizationDAO extends GenericDAO<Localization> {

    private Map<String, String> localizations = Maps.newHashMap();

    @Override
    protected void initDao() throws Exception {
        try {
            for (Localization localization : getAll()) {
                localizations.put(localization.getName(), localization.getValue());
            }
        } catch (Exception e) {
            log.error("localization was not loaded (if this exception occurs in empty DB just ignore it)");
            log.debug("", e);
        }
    }

    /**
     * Load localized value.
     * 
     * @param name
     *            key
     * @return localized value or key if no localization exists
     */
    public String getLocalized(String name) {
        String value = localizations.get(name);
        if (value == null) {
            return name;
        }
        return value;
    }

    /**
     * Save localizations.
     * 
     * @param localizations
     *            localizations
     * @param rewrite
     *            rewrite existing localization
     */
    public void saveLocalizations(Map<String, String> localizations, boolean rewrite) {
        for (Map.Entry<String, String> entry : localizations.entrySet()) {
            saveLocalization(entry.getKey(), entry.getValue(), rewrite);
        }
    }

    /**
     * Save localization.
     * 
     * @param name
     *            key
     * @param value
     *            localized value
     * @param rewrite
     *            rewrite existing localization
     */
    private void saveLocalization(String name, String value, boolean rewrite) {
        Localization localization = findFirstOrNull("from Localization where name=?", name);
        if (localization == null || rewrite) {
            localizations.put(name, value);
        }
        if (localization == null) {
            create(new Localization(name, value));
        } else if (rewrite) {
            localization.setValue(value);
        }
    }

}
