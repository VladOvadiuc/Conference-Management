package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.PCMember;
import ro.ubb.catalog.core.model.ReviewerAbstract;
import ro.ubb.catalog.core.model.validators.UserException;
import ro.ubb.catalog.core.service.PCMemberService;
import ro.ubb.catalog.web.converter.PCMemberConverter;
import ro.ubb.catalog.web.dto.PCMemberDto;
import ro.ubb.catalog.web.dto.PCMembersDto;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class PCMemberController {
    private static final Logger log =
            LoggerFactory.getLogger(PCMemberController.class);

    @Autowired
    private PCMemberService membersService;

    @Autowired
    private PCMemberConverter pcMemberConverter;

    @RequestMapping(value = "/members", method = RequestMethod.GET)
    PCMembersDto getAllUsers() throws ExecutionException, InterruptedException {
        log.trace("getAllMembers --- method entered");

        List<PCMember> confferenceUsers = membersService.getAllPCMembers();
        List<PCMemberDto> dtos = pcMemberConverter.convertModelsToDtos(confferenceUsers);
        PCMembersDto result = new PCMembersDto(dtos);
        log.trace("getAllMembers --- method left");
        return result;
    }

    @RequestMapping(value = "/members/{id}", method = RequestMethod.GET)
    PCMemberDto getUser(@PathVariable Long id) throws ExecutionException, InterruptedException {
        log.trace("getMember --- method entered");

        List<PCMember> confferenceUsers = membersService.getAllPCMembers();
        PCMember c= confferenceUsers.stream().filter(cc -> cc.getId().equals(id)).findFirst().get();
        PCMemberDto result= pcMemberConverter.convertModelToDto(c);
        log.trace("getMember --- method left");
        return result;


    }

    @RequestMapping(value = "/members/{status}", method = RequestMethod.POST)
    Response signUp(@RequestBody PCMemberDto dto, @PathVariable String status) throws ExecutionException, InterruptedException {
        log.trace("signUp for member: dto={}", dto);

        PCMember saved=new PCMember();
        try{
            saved= this.membersService.signUp(
                    pcMemberConverter.convertDtoToModel(dto),status
            );
        } catch(UserException ex){
            return Response.status(500).entity(ex.getMessage()).build();
        }

        PCMemberDto result = pcMemberConverter.convertModelToDto(saved);

        log.trace("signUp for member: result={}", result);

        return Response.status(200).entity(result).build();
    }

    @RequestMapping(value="/membersget/{username}",method= RequestMethod.GET)
    Response getUserByUsername(@PathVariable String username) throws ExecutionException, InterruptedException {
        log.trace("getMemberByUsername for username: {}",username);
        PCMember user=this.membersService.getPCMemberByUsername(username);
        PCMemberDto result = pcMemberConverter.convertModelToDto(user);
        log.trace("getMemberByUsername in controller left");
        return Response.status(Response.Status.OK).entity(result).build();

    }

    @RequestMapping(value="/members/{username}/{password}", method= RequestMethod.GET)
    Response login(@PathVariable String username, @PathVariable String password) throws ExecutionException, InterruptedException {
        log.trace("login for member: username={}, password={}", username,password);

        PCMember user;
        try {
            user=this.membersService.logIn(username,password);
        }catch(UserException ex){
            return Response.status(500).entity(ex.getMessage()).build();
        }
        PCMemberDto result= pcMemberConverter.convertModelToDto(user);


        log.trace("login for member: result={}", result);

        return Response.status(200).entity(result).build();

    }

    @RequestMapping(value = "/members/{id}", method = RequestMethod.PUT)
    PCMemberDto updateClient(@PathVariable Long id,
                             @RequestBody PCMemberDto dto) {
        log.trace("updateMember: id={},dto={}", id, dto);

        PCMember update = membersService.updatePCMember(
                id,
                pcMemberConverter.convertDtoToModel(dto));
        PCMemberDto result = pcMemberConverter.convertModelToDto(update);

        return result;
    }



}
