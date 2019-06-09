package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Abstract;
import ro.ubb.catalog.core.model.Author;
import ro.ubb.catalog.core.model.validators.UserException;
import ro.ubb.catalog.core.repository.jpar.AbstractRepository;
import ro.ubb.catalog.core.repository.jpar.AuthorRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class AbstrctServiceImpl implements AbstrctService {
    private static final Logger log = LoggerFactory.getLogger(
            AbstrctServiceImpl.class);
    private ExecutorService executorService= Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    @Autowired
    private AbstractRepository abstractRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Abstract> getAllAbstracts() throws ExecutionException, InterruptedException {
        log.trace("--get all abstracts entered");
        List<Abstract> users=abstractRepository.findAll()
                .stream()
                .sorted((c1,c2)->c1.getTitle().compareTo(c2.getTitle()))
                .collect(Collectors.toList());
        log.trace("--get all abstracts left");
        return users;
    }

    @Override
    public Abstract addAbstract(Abstract abstrct, Author author) throws UserException, ExecutionException, InterruptedException {
        log.trace("--add abstract: abstract = {}", abstrct);
        Abstract ab=getAbstractByTitle(abstrct.getTitle());
        if (ab!=null){
            throw new UserException("Duplicate Title!");
        }

        abstractRepository.save(abstrct);
        author.addAbstract(abstrct);
        log.trace("--added abstract");

        return abstrct;

    }

    @Override
    @Transactional
    public Abstract getAbstractByTitle(String title)  throws ExecutionException, InterruptedException{
        log.trace("getAbstractByTitle: title={}",title);
        try {
            Future<Abstract> abstractFuture = executorService.submit(() -> {
                long nr = abstractRepository.findAll()
                        .stream()
                        .filter(e -> e.getTitle().equals(title))
                        .count();

                if (nr!=0){
                    Optional<Abstract> abstrct = abstractRepository.findAll()
                            .stream()
                            .filter(e -> e.getTitle().equals(title))
                            .findFirst();
                    return abstrct.get();
                }else{
                    return null;
                }
            });

            log.trace("getAbstractByTitle: result={}",abstractFuture.get());
            if (abstractFuture.get()==null){
                log.trace("so null");
                return null;
            }else{
                return abstractFuture.get();
            }
        }catch(NoSuchElementException ex){
            log.trace("getAbstractByTitle: no abstract");
            return null;
        }

    }

    @Override
    @Transactional
    public Abstract getAbstractByAuthorId(Long id) throws ExecutionException, InterruptedException {
        log.trace("getAbstractByAuthorId: id={}", id);
        try {
            Future<Abstract> abstractFuture = executorService.submit(() -> {
                long nr = abstractRepository.findAll()
                        .stream()
                        .filter(a -> a.getAuthor().getId().equals(id))
                        .count();

                if (nr!=0){
                    Optional<Abstract> abstractOptional= abstractRepository.findAll()
                            .stream()
                            .filter(a -> a.getAuthor().getId().equals(id))
                            .findFirst();
                    return abstractOptional.get();
                }else{
                    return null;
                }
            });

            log.trace("getPaperByAuthorId: result={}",abstractFuture.get());
            if (abstractFuture.get()==null){
                log.trace("so null");
                return null;
            }else{
                return abstractFuture.get();
            }
        }catch(NoSuchElementException ex){
            log.trace("getPaperByAuthorId: no user");
            return null;
        }
    }

    @Override
    public Abstract getAbstractById(Long id) {
        return this.abstractRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Abstract updateAbstract(Long id, Abstract new_abstract) throws ExecutionException, InterruptedException {
        log.trace("--update abstract: abstract = {}", new_abstract);
        Optional<Abstract> updatedAbstract = abstractRepository.findById(id);
        updatedAbstract.ifPresent(abs -> {
            abs.setAbstractPath(new_abstract.getAbstractPath());
            // abstractRepository.save(abs);
            log.debug("abstract updated");
        });
        return updatedAbstract.get();
    }
}
