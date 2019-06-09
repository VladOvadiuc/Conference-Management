package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Conference;
import ro.ubb.catalog.core.repository.jpar.ConferenceRepository;

@Service
public class ConferenceServiceImpl implements ConferenceService {
    private static final Logger log = LoggerFactory.getLogger(
            ConferenceServiceImpl.class);
    @Autowired
    private ConferenceRepository conferenceRepository;

    @Override
    public Conference getFirstConference() {
        return conferenceRepository.findAll().get(0);
    }
}
