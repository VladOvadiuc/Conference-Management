package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.CoChair;
import ro.ubb.catalog.core.model.validators.UserException;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CoChairService {
    CoChair addCoChair(CoChair coChair) throws UserException;
    List<CoChair> getAllCoChairs() throws ExecutionException, InterruptedException;
    CoChair updateCoChair(Long id, CoChair coChair) throws UserException;
    CoChair getCoChairById(Long id) throws ExecutionException, InterruptedException;
    CoChair getCoChairByUsername(String username) throws ExecutionException, InterruptedException;
}
