package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.ConferenceUser;
import ro.ubb.catalog.core.model.Listener;
import ro.ubb.catalog.core.model.validators.UserException;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserService {
    ConferenceUser signUp(ConferenceUser confferenceUser) throws UserException, ExecutionException, InterruptedException;
    List<ConferenceUser> getAllUsers() throws ExecutionException, InterruptedException;
    ConferenceUser updateUser(Long id, ConferenceUser c) throws UserException;
    ConferenceUser getUserById(Long id) throws ExecutionException, InterruptedException;
    ConferenceUser getUserByUsername(String username) throws ExecutionException, InterruptedException;
    ConferenceUser logIn(String username, String password) throws ExecutionException, InterruptedException;
    Listener payRegistration(ConferenceUser user);
    //void setPageSize(int size);
    //List<ConfferenceUser> getNextClients() throws ExecutionException, InterruptedException;
    //    void delete(Long id) throws ClientException;
//    List<ConfferenceUser> sortClientByName() throws ExecutionException, InterruptedException;
    //   Set<ConfferenceUser> filterClientsByFirstName(String firstName) throws ExecutionException, InterruptedException;
    //  Set<ConfferenceUser> filterClientsByAge(int age) throws ExecutionException, InterruptedException;
}
