package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Presentation;
import ro.ubb.catalog.core.repository.jpar.PresentationRepository;

import java.util.List;

@Service
public class PresentationServiceImpl implements  PresentationService{
    private static final Logger log = LoggerFactory.getLogger(
            PresentationServiceImpl.class);
    @Autowired
    private PresentationRepository presentationRepository;
    @Override
    public List<Presentation> getAllPresentations() {
        log.trace("get all presentations");
        return presentationRepository.findAll();
    }
}
