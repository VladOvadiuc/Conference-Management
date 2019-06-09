package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Session;
import ro.ubb.catalog.core.repository.jpar.SessionRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SessionServiceImpl implements SessionService {
    private static final Logger log = LoggerFactory.getLogger(
            CoChairServiceImpl.class);
    private ExecutorService executorService= Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    @Autowired
    private SessionRepository sessionRepository;
    @Override
    public Session addSession(Session session) {
        log.trace("--add session: id = {}", session.getId());
        sessionRepository.save(session);
        log.trace("--session added");
        return session;
    }
}
