package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Author;
import ro.ubb.catalog.core.model.Paper;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PaperService {
    List<Paper> getAllPapers() throws ExecutionException, InterruptedException;
    Paper updatePaper(Long id, Paper new_paper) throws ExecutionException, InterruptedException;
    Paper addPaper(Paper paper, Author author) throws ExecutionException, InterruptedException;
    Paper getPaperByTitle(String title) throws ExecutionException, InterruptedException;
    Paper getPaperByAuthorId(Long id) throws ExecutionException, InterruptedException;
    List<Paper> getPapersByReviewerId(Long id) throws ExecutionException, InterruptedException;
    void acceptPapers() throws ExecutionException, InterruptedException;
}
