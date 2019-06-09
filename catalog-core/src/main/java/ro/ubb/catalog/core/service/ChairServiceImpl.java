package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Chair;
import ro.ubb.catalog.core.model.ConferenceUser;
import ro.ubb.catalog.core.model.validators.UserException;
import ro.ubb.catalog.core.repository.jpar.ChairRepository;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ChairServiceImpl implements ChairService {
    private static final Logger log = LoggerFactory.getLogger(
            ChairServiceImpl.class);
    private ExecutorService executorService= Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    @Autowired
    private ChairRepository repository;

    @Override
    public Chair addChair(Chair chair) throws UserException {
        log.trace("--addChair entered: user = {}", chair);
        if (repository.count()>0){
            throw new UserException("One chair allowed for a confference!");
        }
        repository.save(chair);
        log.trace("--addChair left: ");

        return chair;

    }

    @Override
    public Chair getChair() throws ExecutionException, InterruptedException {
        log.trace("--get chair entered");
        Optional<Chair> chair=repository.findAll()
                .stream()
                .sorted((c1,c2)->c1.getUsername().compareTo(c2.getUsername()))
                .findFirst();
        log.trace("--get chair left");
        return chair.get();
    }

    @Override
    public Chair updateChair(Long id, Chair chair1) throws UserException {
        log.trace("updateChair: id={}",id);
        Objects.requireNonNull(id);
        repository.findById(id)
                .ifPresentOrElse(chair -> {
                    chair.setUsername(chair1.getUsername());
                    chair.setName(chair1.getName());
                    chair.setEmail(chair1.getEmail());
                    chair.setAffiliation(chair1.getAffiliation());
                    chair.setPassword(chair1.getPassword());
                    chair.setWebpage(chair1.getWebpage());
                    repository.save(chair);
                    log.debug("update --- chair updated? --- " +
                            "chair={}", chair);
                }, ()->{
                    throw new UserException("No chair with such id");
                });
        log.trace("updateChair --- method finished");
        return chair1;
    }

    @Override
    public Chair getChairById(Long id) throws ExecutionException, InterruptedException {
        log.trace("getChairById: id={}",id);
        Future<Chair> chairFuture=executorService.submit(()->{
            Optional<Chair> cl=repository.findById(id);
            return cl.get();
        });
        log.trace("getUserById: result={}",chairFuture.get());
        return chairFuture.get();
    }

    @Override
    public Chair getChairByUsername(String username) throws ExecutionException, InterruptedException {
        log.trace("getChairByUsername: username={}",username);
        try {
            Future<Chair> chair = executorService.submit(() -> {
                long nr = repository.findAll()
                        .stream()
                        .filter(e -> e.getUsername().equals(username))
                        .count();

                if (nr!=0){
                    Optional<Chair> chairOptional = repository.findAll()
                            .stream()
                            .filter(e -> e.getUsername().equals(username))
                            .findFirst();
                    return chairOptional.get();
                }else{
                    return null;
                }
            });

            log.trace("getChairByUsername: result={}",chair.get());
            if (chair.get()==null){
                log.trace("so null");
                return null;
            }else{
                return chair.get();
            }
        }catch(NoSuchElementException ex){
            log.trace("getChairByUsername: no user");
            return null;
        }
    }
}
