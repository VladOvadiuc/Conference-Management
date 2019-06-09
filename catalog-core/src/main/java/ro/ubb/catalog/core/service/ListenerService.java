package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Author;
import ro.ubb.catalog.core.model.Listener;

import java.util.concurrent.ExecutionException;

public interface ListenerService {
    Author uploadFile(Listener listener);
    Listener getListenerByUsername(String username) throws ExecutionException, InterruptedException;
}
