package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Author;
import ro.ubb.catalog.core.model.Speaker;
import ro.ubb.catalog.core.repository.jpar.SpeakerRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class SpeakerServiceImpl implements SpeakerService {
    private static final Logger log = LoggerFactory.getLogger(
            SpeakerServiceImpl.class);
    private ExecutorService executorService= Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    @Autowired
    private SpeakerRepository speakerRepository;


    @Override
    public Speaker uploadPresentation(Author author) {
        Speaker speaker = new Speaker(author.getUsername(),author.getPassword(),author.getName(),author.getAffiliation(),author.getEmail());

        speakerRepository.save(speaker);
        return speaker;
    }

    @Override
    public Speaker getSpeakerByUsername(String username) throws ExecutionException, InterruptedException {
        log.trace("getSpeakerByUsername: username={}", username);
        try {
            Future<Speaker> speaker = executorService.submit(() -> {
                long nr = speakerRepository.findAll()
                        .stream()
                        .filter(e -> e.getUsername().equals(username))
                        .count();

                if (nr != 0) {
                    Optional<Speaker> speakerOptional = speakerRepository.findAll()
                            .stream()
                            .filter(e -> e.getUsername().equals(username))
                            .findFirst();
                    return speakerOptional.get();
                } else {
                    return null;
                }
            });

            log.trace("getPCMemberByUsername: result={}", speaker.get());
            if (speaker.get() == null) {
                log.trace("so null");
                return null;
            } else {
                return speaker.get();
            }
        } catch (NoSuchElementException ex) {
            log.trace("getPCMemberByUsername: no member");
            return null;
        }
    }
}
