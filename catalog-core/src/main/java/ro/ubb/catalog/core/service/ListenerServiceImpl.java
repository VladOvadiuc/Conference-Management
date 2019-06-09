package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Author;
import ro.ubb.catalog.core.model.Listener;
import ro.ubb.catalog.core.repository.jpar.AuthorRepository;
import ro.ubb.catalog.core.repository.jpar.ListenerRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ListenerServiceImpl implements ListenerService {
    private static final Logger log = LoggerFactory.getLogger(
            ListenerServiceImpl.class);
    private ExecutorService executorService= Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ListenerRepository repository;

    @Override
    public Author uploadFile(Listener listener) {
        Author author = new Author(listener.getUsername(),listener.getPassword(),listener.getName(),listener.getAffiliation(),listener.getEmail());

        authorRepository.save(author);
        return author;
    }

    @Override
    public Listener getListenerByUsername(String username) throws ExecutionException, InterruptedException {
        log.trace("getListenerByUsername: username={}",username);
        try {
            Future<Listener> listener = executorService.submit(() -> {
                long nr = repository.findAll()
                        .stream()
                        .filter(e -> e.getUsername().equals(username))
                        .count();

                if (nr!=0){
                    Optional<Listener> listenerOptional = repository.findAll()
                            .stream()
                            .filter(e -> e.getUsername().equals(username))
                            .findFirst();
                    return listenerOptional.get();
                }else{
                    return null;
                }
            });

            log.trace("getListenerByUsername: result={}",listener.get());
            if (listener.get()==null){
                log.trace("so null");
                return null;
            }else{
                return listener.get();
            }
        }catch(NoSuchElementException ex){
            log.trace("getListenerByUsername: no user");
            return null;
        }
    }
}
