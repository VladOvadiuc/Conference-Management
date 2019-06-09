package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Author;
import ro.ubb.catalog.core.model.Speaker;

import java.util.concurrent.ExecutionException;

public interface SpeakerService {
    Speaker uploadPresentation(Author author);

    Speaker getSpeakerByUsername(String username) throws ExecutionException, InterruptedException;
}
