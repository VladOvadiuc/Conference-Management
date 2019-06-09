package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.ConferenceUser;
import ro.ubb.catalog.core.model.validators.UserException;
import ro.ubb.catalog.core.service.UserService;
import ro.ubb.catalog.web.converter.UserConverter;
import ro.ubb.catalog.web.dto.UserDto;
import ro.ubb.catalog.web.dto.UsersDto;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class UserController {
    private static final Logger log =
            LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    UsersDto getAllUsers() throws ExecutionException, InterruptedException {
        log.trace("getAllUsers --- method entered");

        List<ConferenceUser> confferenceUsers = userService.getAllUsers();
        List<UserDto> dtos = userConverter.convertModelsToDtos(confferenceUsers);
        UsersDto result = new UsersDto(dtos);

        return result;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    UserDto getUser(@PathVariable Long id) throws ExecutionException, InterruptedException {
        log.trace("getUser --- method entered");

        List<ConferenceUser> confferenceUsers = userService.getAllUsers();
        ConferenceUser c= confferenceUsers.stream().filter(cc -> cc.getId().equals(id)).findFirst().get();
        UserDto result= userConverter.convertModelToDto(c);
        log.trace("getUser --- method left");
        return result;


    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    Response signUp(@RequestBody UserDto dto) throws ExecutionException, InterruptedException {
        log.trace("signUp for user: dto={}", dto);
        ConferenceUser saved=new ConferenceUser();
        try{
            saved= this.userService.signUp(
                    userConverter.convertDtoToModel(dto)
            );
        } catch(UserException ex){
            return Response.status(500).entity(ex.getMessage()).build();
        }

        UserDto result = userConverter.convertModelToDto(saved);

        log.trace("signUp for user: result={}", result);

        return Response.status(200).entity(result).build();
    }


    @RequestMapping(value="/usersget/{username}",method= RequestMethod.GET)
    Response getUserByUsername(@PathVariable String username) throws ExecutionException, InterruptedException {
        log.trace("getUserByUsername for username: {}",username);
        ConferenceUser user=this.userService.getUserByUsername(username);
        UserDto result = userConverter.convertModelToDto(user);
        log.trace("getUserByUsername in controller left");
        return Response.status(Response.Status.OK).entity(result).build();

    }

    @RequestMapping(value="/users/{username}/{password}", method= RequestMethod.GET)
    Response login(@PathVariable String username, @PathVariable String password) throws ExecutionException, InterruptedException {
        log.trace("login for user: username={}, password={}", username,password);

        ConferenceUser user;
        try {
            user=this.userService.logIn(username,password);
        }catch(UserException ex){
            return Response.status(500).entity(ex.getMessage()).build();
        }
        UserDto result=userConverter.convertModelToDto(user);


        log.trace("login for user: result={}", result);

        return Response.status(200).entity(result).build();

    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    UserDto updateClient(@PathVariable Long id,
                         @RequestBody UserDto dto) {
        log.trace("updateClient: id={},dto={}", id, dto);

        ConferenceUser update = userService.updateUser(
                id,
                userConverter.convertDtoToModel(dto));
        UserDto result = userConverter.convertModelToDto(update);

        return result;
    }

//    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
//    ResponseEntity<?> deleteClient(@PathVariable Long id) {
//        log.trace("deleteClient: id={}", id);
//
//        userService.deleteClient(id);
//
//        log.trace("deleteClient --- method finished");
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
