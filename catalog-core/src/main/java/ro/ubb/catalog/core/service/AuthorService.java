package ro.ubb.catalog.core.service;

import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Abstract;
import ro.ubb.catalog.core.model.Author;
import ro.ubb.catalog.core.model.Paper;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AuthorService {
    Abstract addAbstract(   Long author, Abstract abstrct) throws ExecutionException, InterruptedException;

    //Abstract addAbstract(Listener listener, Abstract abstrct) throws ExecutionException, InterruptedException;

    List<Author> getAllAuthors() throws ExecutionException, InterruptedException;

    Author addAuthor(Author author) throws ExecutionException, InterruptedException;
    Author getAuthorByUsername(String username) throws ExecutionException, InterruptedException;

    @Transactional
    Author getAuthorById(Long id) throws ExecutionException, InterruptedException;

    Paper addPaper(Long authorId, Paper paper) throws ExecutionException, InterruptedException;;



}
