package ro.ubb.catalog.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.catalog.core.model.ConferenceUser;
import ro.ubb.catalog.core.model.Listener;
import ro.ubb.catalog.core.service.UserService;
import ro.ubb.catalog.web.converter.UserConverter;
import ro.ubb.catalog.web.dto.UserDto;

import java.util.concurrent.ExecutionException;

@RestController
public class ListenerController {
    private static final Logger log =
            LoggerFactory.getLogger(ListenerController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @RequestMapping(value = "/listeners", method = RequestMethod.POST)
    UserDto payRegistration(@RequestBody UserDto dto) throws ExecutionException, InterruptedException {

        log.trace("pay registration");
        Listener l=this.userService.payRegistration(
                userConverter.convertDtoToModel(dto));

        ConferenceUser u=(ConferenceUser)l;
        UserDto dtoo=userConverter.convertModelToDto(u);

        return dtoo;
    }
}
