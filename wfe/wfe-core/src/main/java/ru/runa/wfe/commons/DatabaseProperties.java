package ru.runa.wfe.commons;

public class DatabaseProperties {
    private static final PropertyResources RESOURCES = new PropertyResources("database.properties", false);

    public static String getUserTransactionJndiName() {
        return RESOURCES.getStringPropertyNotNull("user.transaction.jndi.name");
    }

}
