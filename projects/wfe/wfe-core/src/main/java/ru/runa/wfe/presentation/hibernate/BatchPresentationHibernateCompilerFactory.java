package ru.runa.wfe.presentation.hibernate;

import ru.runa.wfe.presentation.BatchPresentation;

public class BatchPresentationHibernateCompilerFactory<T extends Object> implements IBatchPresentationCompilerFactory<T> {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public IBatchPresentationCompiler<T> createCompiler(BatchPresentation batchPresentation) {
        return new BatchPresentationHibernateCompiler(batchPresentation);
    }

}
