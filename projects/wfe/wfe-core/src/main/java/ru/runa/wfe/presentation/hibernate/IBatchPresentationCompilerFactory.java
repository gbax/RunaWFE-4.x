package ru.runa.wfe.presentation.hibernate;

import ru.runa.wfe.presentation.BatchPresentation;

public interface IBatchPresentationCompilerFactory<T> {

    public IBatchPresentationCompiler<T> createCompiler(BatchPresentation batchPresentation);

}
