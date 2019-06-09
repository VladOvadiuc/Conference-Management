package ro.ubb.catalog.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Author;
import ro.ubb.catalog.core.model.ConferenceUser;
import ro.ubb.catalog.core.model.Listener;
import ro.ubb.catalog.core.service.AuthorService;
import ro.ubb.catalog.core.service.ListenerService;
import ro.ubb.catalog.core.service.UserService;
import ro.ubb.catalog.web.converter.AuthorConverter;
import ro.ubb.catalog.web.converter.ListenerConverter;
import ro.ubb.catalog.web.converter.UserConverter;
import ro.ubb.catalog.web.dto.*;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class AuthorController {
    private static final Logger log =
            LoggerFactory.getLogger(AuthorController.class);
    @Autowired
    private AuthorService authorService;

    @Autowired
    private ListenerService userService;

    @Autowired
    private ListenerConverter userConverter;
    @Autowired
    private AuthorConverter authorConverter;

    @RequestMapping(value = "/authors", method = RequestMethod.POST)
    AuthorDto uploadFile(@RequestBody ListenerDto dto) throws ExecutionException, InterruptedException {

        log.trace("upload file");
        Author l=this.userService.uploadFile(
                userConverter.convertDtoToModel(dto));

        Author u= (Author)l;
        AuthorDto dtoo=authorConverter.convertModelToDto(u);

        return dtoo;
    }

    @RequestMapping(value="/authorsget/{username}",method= RequestMethod.GET)
    Response getAuthorByUsername(@PathVariable String username) throws ExecutionException, InterruptedException {
        log.trace("getAuthorByUsername for username: {}",username);
        Author user=this.authorService.getAuthorByUsername(username);
        AuthorDto result = authorConverter.convertModelToDto(user);
        log.trace("getAuthorByUsername in controller left");
        return Response.status(Response.Status.OK).entity(result.getId()).build();

    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    AuthorsDto getAllAuthors() throws ExecutionException, InterruptedException {
        log.trace("getAllAuthors --- method entered");

        List<Author> authors = authorService.getAllAuthors();
        List<AuthorDto> dtos = authorConverter.convertModelsToDtos(authors);
        AuthorsDto result = new AuthorsDto(dtos);

        return result;
    }

//    @RequestMapping(value = "/users", method = RequestMethod.GET)
//    UsersDto getAllUsers() throws ExecutionException, InterruptedException {
//        log.trace("getAllUsers --- method entered");
//
//        List<ConferenceUser> confferenceUsers = userService.getAllUsers();
//        List<UserDto> dtos = userConverter.convertModelsToDtos(confferenceUsers);
//        UsersDto result = new UsersDto(dtos);
//
//        return result;
//    }

}
