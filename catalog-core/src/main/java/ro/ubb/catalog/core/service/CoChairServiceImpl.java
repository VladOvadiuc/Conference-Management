package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Chair;
import ro.ubb.catalog.core.model.CoChair;
import ro.ubb.catalog.core.model.validators.UserException;
import ro.ubb.catalog.core.repository.jpar.CoChairRepository;

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
public class CoChairServiceImpl implements CoChairService {
    private static final Logger log = LoggerFactory.getLogger(
            CoChairServiceImpl.class);
    private ExecutorService executorService= Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());


    @Autowired
    private CoChairRepository repository;

    @Override
    public CoChair addCoChair(CoChair coChair) throws UserException {
        log.trace("--add entered: coChair = {}", coChair);
        repository.save(coChair);
        log.trace("--add left: ");

        return coChair;

    }

    @Override
    public List<CoChair> getAllCoChairs() throws ExecutionException, InterruptedException {
        log.trace("--get all coChairs entered");
        List<CoChair> coChairs=repository.findAll()
                .stream()
                .sorted((c1,c2)->c1.getUsername().compareTo(c2.getUsername()))
                .collect(Collectors.toList());
        log.trace("--get all coChairs left");
        return coChairs;
    }

    @Override
    public CoChair updateCoChair(Long id, CoChair coChair) throws UserException {
        log.trace("updateUser: id={}",id);
        Objects.requireNonNull(id);
        repository.findById(id)
                .ifPresentOrElse(coChair1 -> {
                    coChair1.setUsername(coChair.getUsername());
                    coChair1.setName(coChair.getName());
                    coChair1.setEmail(coChair.getEmail());
                    coChair1.setAffiliation(coChair.getAffiliation());
                    coChair1.setPassword(coChair.getPassword());
                    coChair1.setWebpage(coChair.getWebpage());
                    repository.save(coChair1);
                    log.debug("update --- coChair1 updated? --- " +
                            "coChair1={}", coChair1);
                }, ()->{
                    throw new UserException("No client with such id");
                });
        log.trace("updateUser --- method finished");
        return coChair;
    }

    @Override
    public CoChair getCoChairById(Long id) throws ExecutionException, InterruptedException {
        log.trace("getCoChairById: id={}",id);
        Future<CoChair> coChairFuture=executorService.submit(()->{
            Optional<CoChair> cl=repository.findById(id);
            return cl.get();
        });
        log.trace("getUserById: result={}",coChairFuture.get());
        return coChairFuture.get();
    }

    @Override
    public CoChair getCoChairByUsername(String username) throws ExecutionException, InterruptedException {
        log.trace("getCoChairByUsername: username={}",username);
        try {
            Future<CoChair> coChair = executorService.submit(() -> {
                long nr = repository.findAll()
                        .stream()
                        .filter(e -> e.getUsername().equals(username))
                        .count();

                if (nr!=0){
                    Optional<CoChair> coChairOptional = repository.findAll()
                            .stream()
                            .filter(e -> e.getUsername().equals(username))
                            .findFirst();
                    return coChairOptional.get();
                }else{
                    return null;
                }
            });

            log.trace("getCoChairByUsername: result={}",coChair.get());
            if (coChair.get()==null){
                log.trace("so null");
                return null;
            }else{
                return coChair.get();
            }
        }catch(NoSuchElementException ex){
            log.trace("getCoChairByUsername: no user");
            return null;
        }
    }
}
