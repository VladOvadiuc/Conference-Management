package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.*;
import ro.ubb.catalog.core.model.validators.UserException;
import ro.ubb.catalog.core.repository.jpar.PCMemberRepository;
import ro.ubb.catalog.core.repository.jpar.ReviewerRepository;
import ro.ubb.catalog.core.repository.jpar.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class PCMemberServiceImpl implements PCMemberService {
    private static final Logger log = LoggerFactory.getLogger(
            PCMemberServiceImpl.class);
    private ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());
    @Autowired
    private PCMemberRepository repository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChairService chairService;
    @Autowired
    private CoChairService coChairService;
    @Autowired
    private ReviewerRepository reviewerRepository;


    @Override
    public PCMember signUp(PCMember member, String type) throws UserException, ExecutionException, InterruptedException {
        log.trace("--signUp entered: member = {}", member);
        //save to users
        PCMember user1=getPCMemberByUsername(member.getUsername());
        if (user1!=null){
            throw new UserException("Duplicate username!");
        }
        ConferenceUser user = new ConferenceUser(member.getUsername(), member.getPassword(), member.getName(), member.getAffiliation(), member.getEmail());
        user.setId(member.getId());

        userRepository.save(user);
        repository.save(member);

        if (type.equals("chair")) {
            Chair chair = new Chair(member.getUsername(), member.getPassword(), member.getName(), member.getAffiliation(), member.getEmail(), member.getWebpage());
            chairService.addChair(chair);
        } else if (type.equals("coChair")) {
            CoChair coChair = new CoChair(member.getUsername(), member.getPassword(), member.getName(), member.getAffiliation(), member.getEmail(), member.getWebpage());
            coChairService.addCoChair(coChair);
        }
        else {
            Reviewer reviewer = new Reviewer(member.getUsername(), member.getPassword(), member.getName(), member.getAffiliation(), member.getEmail(), member.getWebpage());
            reviewerRepository.save(reviewer);
        }
        log.trace("--signUp left: ");

        return member;

    }

    @Override
    public List<PCMember> getAllPCMembers() throws ExecutionException, InterruptedException {
        log.trace("--get all members entered");
        List<PCMember> members = repository.findAll()
                .stream()
                .sorted((c1, c2) -> c1.getUsername().compareTo(c2.getUsername()))
                .collect(Collectors.toList());
        log.trace("--get all members left");
        return members;
    }

    @Override
    public PCMember updatePCMember(Long id, PCMember member) throws UserException {
        log.trace("updatePCMember: id={}", id);
        Objects.requireNonNull(id);
        repository.findById(id)
                .ifPresentOrElse(member1 -> {
                    member1.setUsername(member.getUsername());
                    member1.setName(member.getName());
                    member1.setEmail(member.getEmail());
                    member1.setAffiliation(member.getAffiliation());
                    member1.setPassword(member.getPassword());
                    member1.setWebpage(member.getWebpage());
                    repository.save(member1);
                    log.debug("update --- member1 updated? --- " +
                            "member1={}", member1);
                }, () -> {
                    throw new UserException("No member with such id");
                });
        log.trace("updateMember --- method finished");
        return member;
    }

    @Override
    public PCMember getPCMemberById(Long id) throws ExecutionException, InterruptedException {
        log.trace("getMemberById: id={}", id);
        Future<PCMember> user = executorService.submit(() -> {
            Optional<PCMember> cl = repository.findById(id);
            return cl.get();
        });
        log.trace("getUserById: result={}", user.get());
        return user.get();
    }

    @Override
    @Transactional
    public PCMember getPCMemberByUsername(String username) throws ExecutionException, InterruptedException {
        log.trace("getPCMemberByUsername: username={}", username);
        try {
            Future<PCMember> memberr = executorService.submit(() -> {
                long nr = repository.findAll()
                        .stream()
                        .filter(e -> e.getUsername().equals(username))
                        .count();

                if (nr != 0) {
                    Optional<PCMember> member = repository.findAll()
                            .stream()
                            .filter(e -> e.getUsername().equals(username))
                            .findFirst();
                    return member.get();
                } else {
                    return null;
                }
            });

            log.trace("getPCMemberByUsername: result={}", memberr.get());
            if (memberr.get() == null) {
                log.trace("so null");
                return null;
            } else {
                return memberr.get();
            }
        } catch (NoSuchElementException ex) {
            log.trace("getPCMemberByUsername: no member");
            return null;
        }

    }

    @Override
    public PCMember logIn(String username, String password) throws ExecutionException, InterruptedException {
        PCMember pcMember = getPCMemberByUsername(username);

        if (pcMember == null) {
            throw new UserException("No PCMember with this username!");
        }
        if (!pcMember.getPassword().equals(password)) {
            throw new UserException("Wrong password");
        }
        return pcMember;
    }


}
