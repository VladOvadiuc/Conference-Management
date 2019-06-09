package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Abstract;
import ro.ubb.catalog.core.model.Author;
import ro.ubb.catalog.core.model.ConferenceUser;
import ro.ubb.catalog.core.model.Paper;
import ro.ubb.catalog.core.model.validators.UserException;
import ro.ubb.catalog.core.repository.jpar.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private static final Logger log = LoggerFactory.getLogger(
            AuthorServiceImpl.class);
    private ExecutorService executorService= Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AbstractRepository abstractRepository;
    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Author getAuthorById(Long id) throws ExecutionException, InterruptedException {
        log.trace("getAuthorById: id={}",id);
        Future<Author> author=executorService.submit(()->{
            Optional<Author> cl=authorRepository.findById(id);
            return cl.get();
        });
        log.trace("getAuthorById: result={}",author.get());
        return author.get();
    }

    @Override
    @Transactional
    public Paper addPaper(Long authorId, Paper paper) throws ExecutionException, InterruptedException {
        Author author = authorRepository.findById(authorId).get();
        author.addPaper(paper);
        paperRepository.save(paper);
        return paper;
    }

    @Override
    @Transactional
    public Abstract addAbstract(Long authorId, Abstract abstrct) throws ExecutionException, InterruptedException {

        ConferenceUser l=userRepository.findById(authorId).get();

        Author a = new Author(l.getUsername(),l.getPassword(),l.getName(),l.getAffiliation(),l.getEmail());


        a.addAbstract(abstrct);
        authorRepository.save(a);
        abstractRepository.save(abstrct);




        return abstrct;

    }

    @Override
    public List<Author> getAllAuthors() throws ExecutionException, InterruptedException {
        log.trace("--get all authors entered");
        List<Author> users=authorRepository.findAll()
                .stream()
                .sorted((c1,c2)->c1.getUsername().compareTo(c2.getUsername()))
                .collect(Collectors.toList());
        log.trace("--get all authors left");
        return users;
    }
    @Override
    public Author addAuthor(Author author) throws UserException, ExecutionException, InterruptedException {
            log.trace("--add author: user = {}", author);
            Author user=getAuthorByUsername(author.getUsername());
            if (user!=null){
                throw new UserException("Duplicate username!");
            }

            authorRepository.save(author);
            log.trace("--signUp left: ");

            return author;
        }

    @Override
    @Transactional
    public Author getAuthorByUsername(String username)  throws ExecutionException, InterruptedException{
        log.trace("getAuthorByUsername: username={}",username);
        try {
            Future<Author> user = executorService.submit(() -> {
                long nr = authorRepository.findAll()
                        .stream()
                        .filter(e -> e.getUsername().equals(username))
                        .count();

                if (nr!=0){
                    Optional<Author> author = authorRepository.findAll()
                            .stream()
                            .filter(e -> e.getUsername().equals(username))
                            .findFirst();
                    return author.get();
                }else{
                    return null;
                }
            });

            log.trace("getAuthorByUsername: result={}",user.get());
            if (user.get()==null){
                log.trace("so null");
                return null;
            }else{
                return user.get();
            }
        }catch(NoSuchElementException ex){
            log.trace("geAuthorByUsername: no user");
            return null;
        }

    }
}
