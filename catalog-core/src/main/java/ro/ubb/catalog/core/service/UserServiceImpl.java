package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.ConferenceUser;
import ro.ubb.catalog.core.model.Listener;
import ro.ubb.catalog.core.model.validators.UserException;
import ro.ubb.catalog.core.repository.jpar.ListenerRepository;
import ro.ubb.catalog.core.repository.jpar.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(
           UserServiceImpl.class);
    private ExecutorService executorService= Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    @Autowired
    private UserRepository repository;

    @Autowired
    private ListenerRepository listenerRepository;

    @Override
    public ConferenceUser signUp(ConferenceUser confferenceUser) throws UserException, ExecutionException, InterruptedException {
        log.trace("--signUp entered: user = {}", confferenceUser);
        ConferenceUser user=getUserByUsername(confferenceUser.getUsername());
        if (user!=null){
            throw new UserException("Duplicate username!");
        }

        repository.save(confferenceUser);
        log.trace("--signUp left: ");

        return confferenceUser;

    }

    @Override
    public List<ConferenceUser> getAllUsers() throws ExecutionException, InterruptedException {
        log.trace("--get all users entered");
        List<ConferenceUser> users=repository.findAll()
                .stream()
                .sorted((c1,c2)->c1.getUsername().compareTo(c2.getUsername()))
                .collect(Collectors.toList());
        log.trace("--get all users left");
        return users;
    }

    @Override
    @Transactional
    public ConferenceUser updateUser(Long id, ConferenceUser user1) throws UserException {
        log.trace("updateUser: id={}",id);
        Objects.requireNonNull(id);
        repository.findById(id)
                .ifPresentOrElse(user -> {
                    user.setUsername(user1.getUsername());
                    user.setName(user1.getName());
                    user.setEmail(user1.getEmail());
                    user.setAffiliation(user1.getAffiliation());
                    user.setPassword(user1.getPassword());
                    repository.save(user);
                    log.debug("update --- user updated? --- " +
                            "user={}", user);
                }, ()->{
                    throw new UserException("No client with such id");
                });
        log.trace("updateUser --- method finished");
        return user1;
    }

    @Override
    @Transactional
    public ConferenceUser getUserById(Long id) throws ExecutionException, InterruptedException {
        log.trace("getUserById: id={}",id);
        Future<ConferenceUser> user=executorService.submit(()->{
            Optional<ConferenceUser> cl=repository.findById(id);
            return cl.get();
        });
        log.trace("getUserById: result={}",user.get());
        return user.get();
    }

    @Override
    @Transactional
    public ConferenceUser getUserByUsername(String username)  throws ExecutionException, InterruptedException{
        log.trace("getUserByUsername: username={}",username);
        try {
            Future<ConferenceUser> user = executorService.submit(() -> {
                long nr = repository.findAll()
                        .stream()
                        .filter(e -> e.getUsername().equals(username))
                        .count();

                if (nr!=0){
                    Optional<ConferenceUser> cuser = repository.findAll()
                            .stream()
                            .filter(e -> e.getUsername().equals(username))
                            .findFirst();
                    return cuser.get();
                }else{
                    return null;
                }
            });

            log.trace("getUserByUsername: result={}",user.get());
            if (user.get()==null){
                log.trace("so null");
                return null;
            }else{
                return user.get();
            }
        }catch(NoSuchElementException ex){
            log.trace("getUserByUsername: no user");
            return null;
        }

    }

    @Override
    public ConferenceUser logIn(String username, String password) throws ExecutionException, InterruptedException {
       ConferenceUser confferenceUser=getUserByUsername(username);

       if (confferenceUser==null){
           throw new UserException("No user with this username!");
       }
       if (!confferenceUser.getPassword().equals(password)){
           throw new UserException("Wrong password");
       }
       return confferenceUser;
    }
//
//    @Override
//    public ConferenceUser logIn(String username, String password) throws ExecutionException, InterruptedException {
//        ConfferenceUser confferenceUser=getUserByUsername(username);
//        System.out.println("sd");
//
//
//        Abstract ab = new Abstract("sds","Abs","sdfs saf sad","sadwda","sad asf,afs");
//        Abstract ab1 = abstrctService.addAbstract(ab);
//        Author author= new Author("d","assad","ana","asddas","sfa");
//        Author au = authorService.addAuthor(author);
//        authorService.addAbstract(au,ab1);
//
//        Reviewer reviewer = new Reviewer("sfdfds","sdsdf","fdfds","dsffd","dsff","sd");
//        Reviewer r1 = reviewerService.addReviewer(reviewer);
//
//        reviewerService.bid(r1,ab1,"bun");
//
//        if (confferenceUser==null){
//            throw new UserException("No user with this username!");
//        }
//        if (!confferenceUser.getPassword().equals(password)){
//            throw new UserException("Wrong password");
//        }
//        return confferenceUser;
//    }

    @Override
    public Listener payRegistration(ConferenceUser user){

        Listener lst= new Listener(user.getUsername(),user.getPassword(),user.getName(),user.getAffiliation(),user.getEmail());

        listenerRepository.save(lst);
        return lst;

    }
}
