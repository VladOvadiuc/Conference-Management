package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Chair;
import ro.ubb.catalog.core.model.validators.UserException;

import java.util.concurrent.ExecutionException;

public interface ChairService {
    Chair addChair(Chair chair) throws UserException;
    Chair getChair() throws ExecutionException, InterruptedException;
    Chair updateChair(Long id, Chair chair) throws UserException;
    Chair getChairById(Long id) throws ExecutionException, InterruptedException;
    Chair getChairByUsername(String username) throws ExecutionException, InterruptedException;
}
