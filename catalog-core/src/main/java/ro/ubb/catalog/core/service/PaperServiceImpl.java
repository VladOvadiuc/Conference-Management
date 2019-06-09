package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Author;
import ro.ubb.catalog.core.model.Paper;
import ro.ubb.catalog.core.model.ReviewerPaper;
import ro.ubb.catalog.core.model.Speaker;
import ro.ubb.catalog.core.model.validators.UserException;
import ro.ubb.catalog.core.repository.jpar.AuthorRepository;
import ro.ubb.catalog.core.repository.jpar.PaperRepository;
import ro.ubb.catalog.core.repository.jpar.ReviewerRepository;
import ro.ubb.catalog.core.repository.jpar.SpeakerRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class PaperServiceImpl implements PaperService {
    private static final Logger log = LoggerFactory.getLogger(
            UserServiceImpl.class);
    private ExecutorService executorService= Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ReviewerRepository reviewerRepository;
    @Autowired
    private SpeakerRepository speakerRepository;

    @Override
    public List<Paper> getAllPapers() throws ExecutionException, InterruptedException {
        log.trace("--get all papers entered");
        List<Paper> users=paperRepository.findAll()
                .stream()
                .sorted((c1,c2)->c1.getTitle().compareTo(c2.getTitle()))
                .collect(Collectors.toList());
        log.trace("--get all papers left");
        return users;
    }

    @Override
    public Paper addPaper(Paper paper, Author author)  throws UserException, ExecutionException, InterruptedException {
        log.trace("--add paper: user = {}", paper);
        Paper paper1 = getPaperByTitle(paper.getTitle());
        if (paper1!=null){
            throw new UserException("Duplicate title!");
        }
        paperRepository.save(paper);
        author.addPaper(paper);
        log.trace("--added Paper");

        return paper;


    }

    @Override
    @Transactional
    public Paper getPaperByTitle(String title)  throws ExecutionException, InterruptedException{
        log.trace("getPaperByTitle: title={}",title);
        try {
            Future<Paper> paperFuture = executorService.submit(() -> {
                long nr = paperRepository.findAll()
                        .stream()
                        .filter(e -> e.getTitle().equals(title))
                        .count();

                if (nr!=0){
                    Optional<Paper> abstrct = paperRepository.findAll()
                            .stream()
                            .filter(e -> e.getTitle().equals(title))
                            .findFirst();
                    return abstrct.get();
                }else{
                    return null;
                }
            });

            log.trace("getPaperByTitle: result={}",paperFuture.get());
            if (paperFuture.get()==null){
                log.trace("so null");
                return null;
            }else{
                return paperFuture.get();
            }
        }catch(NoSuchElementException ex){
            log.trace("getPaperByTitle: no paper");
            return null;
        }

    }

    @Override
    public Paper getPaperByAuthorId(Long id) throws ExecutionException, InterruptedException {
        log.trace("getPaperByAuthorId: id={}", id);

        try {
            Future<Paper> paperFuture = executorService.submit(() -> {
                long nr = paperRepository.findAll()
                        .stream()
                        .filter(a -> a.getAuthor().getId().equals(id))
                        .count();

                if (nr!=0){
                    Optional<Paper> paperOptional= paperRepository.findAll()
                            .stream()
                            .filter(a -> a.getAuthor().getId().equals(id))
                            .findFirst();
                    return paperOptional.get();
                }else{
                    return null;
                }
            });

            log.trace("getPaperByAuthorId: result={}",paperFuture.get());
            if (paperFuture.get()==null){
                log.trace("so null");
                return null;
            }else{
                return paperFuture.get();
            }
        }catch(NoSuchElementException ex){
            log.trace("getPaperByAuthorId: no user");
            return null;
        }

    }

    @Override
    @Transactional
    public Paper updatePaper(Long id, Paper new_paper) throws ExecutionException, InterruptedException {
        log.trace("--update paper: paper= {}", new_paper);
        Optional<Paper> updatedPaper = paperRepository.findById(id);
        updatedPaper.ifPresent(p -> {
            p.setFilePath(new_paper.getFilePath());
            //paperRepository.save(p);
            log.debug("abstract updated");
        });
        return updatedPaper.get();
    }

    @Override
    @Transactional
    public List<Paper> getPapersByReviewerId(Long id) throws ExecutionException, InterruptedException {
        List<Paper> papers = paperRepository.findAll().stream()
                .filter(p -> p.getReviewers().stream().filter(rp -> rp.getReviewer().getId().equals(id)).count() > 0)
                .collect(Collectors.toList());
        return papers;
    }

    @Override
    @Transactional
    public void acceptPapers() throws ExecutionException, InterruptedException {
        for (Paper p : paperRepository.findAll()) {
            int cntGood = 0;
            int cntBad = 0;
            for (ReviewerPaper rp : p.getReviewers()) {
                if (rp.getResult().contains("accept")) {
                    cntGood++;
                }
                else if (rp.getResult().contains("reject")) {
                    cntBad++;
                }
            }
            if (cntGood >= cntBad) {
                p.getAuthor().getAbstrct().setStatus("accepted");
                Author a = p.getAuthor();
                Speaker s = new Speaker(a.getUsername(), a.getPassword(), a.getName(), a.getAffiliation(), a.getEmail());
                speakerRepository.save(s);
            }
            else {
                p.getAuthor().getAbstrct().setStatus("declined");
            }
        }
    }
}
