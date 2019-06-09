package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Abstract;
import ro.ubb.catalog.core.model.Paper;
import ro.ubb.catalog.core.model.Reviewer;
import ro.ubb.catalog.core.model.ReviewerPaper;
import ro.ubb.catalog.core.model.validators.UserException;
import ro.ubb.catalog.core.repository.jpar.AbstractRepository;
import ro.ubb.catalog.core.repository.jpar.PaperRepository;
import ro.ubb.catalog.core.repository.jpar.ReviewerRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class ReviewerServiceImpl implements ReviewerService {
    private static final Logger log = LoggerFactory.getLogger(
            UserServiceImpl.class);
    private ExecutorService executorService= Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    @Autowired
    private ReviewerRepository reviewerRepository;

    @Autowired
    private AbstractRepository abstractRepository;
    @Autowired
    private PaperRepository paperRepository;

    @Override
    public List<Reviewer> getAllReviewers() throws ExecutionException, InterruptedException {
        log.trace("--get all reviewers entered");
        List<Reviewer> users=reviewerRepository.findAll()
                .stream()
                .sorted((c1,c2)->c1.getUsername().compareTo(c2.getUsername()))
                .collect(Collectors.toList());
        log.trace("--get all reviewers left");
        return users;
    }

    @Override
    @Transactional
    public Reviewer getReviewerById(Long id) throws ExecutionException, InterruptedException {
        log.trace("getReviewerById: id={}",id);
        Future<Reviewer> reviewer=executorService.submit(()->{
            Optional<Reviewer> cl=reviewerRepository.findById(id);
            return cl.get();
        });
        log.trace("getReviewerById: result={}",reviewer.get());
        return reviewer.get();
    }

    @Override
    public Reviewer addReviewer(Reviewer reviewer) throws UserException, ExecutionException, InterruptedException {
        log.trace("--add author: user = {}", reviewer);
        Reviewer user=getReviewerByUsername(reviewer.getUsername());
        if (user!=null){
            throw new UserException("Duplicate username!");
        }

        reviewerRepository.save(reviewer);
        log.trace("--signUp left: ");

        return reviewer;

    }/*
    @Override
    public void bid(Reviewer reviewer, Abstract abstrct, String result) {
        reviewer.addAbstract(abstrct, result);
    }*/

    @Override
    @Transactional
    public void bid(Long reviewerId, Long abstractId, String result) {

        Reviewer reviewer = this.reviewerRepository.findById(reviewerId).get();
        Abstract abs = this.abstractRepository.findById(abstractId).get();
        reviewer.addAbstract(abs, result);


    }

    @Override
    @Transactional
    public void review(Long reviewerId, Long paperId, String result) throws ExecutionException, InterruptedException {
        Reviewer reviewer = this.reviewerRepository.findById(reviewerId).get();
        ReviewerPaper reviewerPaper = reviewer.getPapers().stream().filter(rp -> rp.getPaper().getId().equals(paperId)).findFirst().get();
        reviewerPaper.setResult(result);
    }

    @Override
    @Transactional
    public void assignPaper(Long reviewerId, Long paperId) throws ExecutionException, InterruptedException {
        Reviewer reviewer = reviewerRepository.findById(reviewerId).get();
        Paper paper = paperRepository.findById(paperId).get();
        reviewer.addPaper(paper);
    }

    @Override
    @Transactional
    public Reviewer getReviewerByUsername(String username)  throws ExecutionException, InterruptedException{
        log.trace("getUserByUsername: username={}",username);
        try {
            Future<Reviewer> user = executorService.submit(() -> {
                long nr = reviewerRepository.findAll()
                        .stream()
                        .filter(e -> e.getUsername().equals(username))
                        .count();

                if (nr!=0){
                    Optional<Reviewer> reviewer = reviewerRepository.findAll()
                            .stream()
                            .filter(e -> e.getUsername().equals(username))
                            .findFirst();
                    return reviewer.get();
                }else{
                    return null;
                }
            });

            log.trace("getReviewerByUsername: result={}",user.get());
            if (user.get()==null){
                log.trace("so null");
                return null;
            }else{
                return user.get();
            }
        }catch(NoSuchElementException ex){
            log.trace("geReviewerByUsername: no user");
            return null;
        }

    }
}
