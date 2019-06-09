package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.PCMember;
import ro.ubb.catalog.core.model.validators.UserException;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PCMemberService {
    PCMember signUp(PCMember pcMember, String type) throws UserException, ExecutionException, InterruptedException;
    List<PCMember> getAllPCMembers() throws ExecutionException, InterruptedException;
    PCMember updatePCMember(Long id, PCMember pcMember) throws UserException;
    PCMember getPCMemberById(Long id) throws ExecutionException, InterruptedException;
    PCMember getPCMemberByUsername(String username) throws ExecutionException, InterruptedException;
    PCMember logIn(String username, String password) throws ExecutionException, InterruptedException;
}
