package ru.runa.wfe.audit.presentation;

import java.io.Serializable;

public class ExecutorNameValue implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    public ExecutorNameValue() {
    }

    public ExecutorNameValue(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
