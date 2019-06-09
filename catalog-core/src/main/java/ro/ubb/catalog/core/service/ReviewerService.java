package ro.ubb.catalog.core.service;

import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Reviewer;
import ro.ubb.catalog.core.model.ReviewerAbstract;
import ro.ubb.catalog.core.model.validators.UserException;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ReviewerService {
    Reviewer getReviewerByUsername(String username) throws ExecutionException, InterruptedException;

    List<Reviewer> getAllReviewers() throws ExecutionException, InterruptedException;

    Reviewer getReviewerById(Long id) throws ExecutionException, InterruptedException;

    Reviewer addReviewer(Reviewer reviewer) throws UserException, ExecutionException, InterruptedException;
    void bid(Long reviewerId, Long abstractId, String result);
    void assignPaper(Long reviewerId, Long paperId) throws  ExecutionException, InterruptedException;
    void review(Long reviewerId, Long paperId, String result) throws ExecutionException,InterruptedException;
    /*void bid(Reviewer reviewer, Abstract abstrct, String result);*/
}
