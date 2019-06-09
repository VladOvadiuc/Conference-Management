package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Abstract;
import ro.ubb.catalog.core.model.Author;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AbstrctService {

    List<Abstract> getAllAbstracts() throws ExecutionException, InterruptedException;

    Abstract addAbstract(Abstract abstrct, Author author) throws ExecutionException, InterruptedException;
    Abstract getAbstractByTitle(String title) throws ExecutionException, InterruptedException;
    Abstract getAbstractByAuthorId(Long id) throws  ExecutionException, InterruptedException;
    Abstract getAbstractById(Long id);
    public Abstract updateAbstract(Long id, Abstract new_abstract) throws ExecutionException, InterruptedException;
}
