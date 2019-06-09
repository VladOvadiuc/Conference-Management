package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Author;
import ro.ubb.catalog.core.model.Speaker;
import ro.ubb.catalog.core.service.AuthorService;
import ro.ubb.catalog.core.service.ListenerService;
import ro.ubb.catalog.core.service.SpeakerService;
import ro.ubb.catalog.web.converter.AuthorConverter;
import ro.ubb.catalog.web.converter.ListenerConverter;
import ro.ubb.catalog.web.converter.SpeakerConverter;
import ro.ubb.catalog.web.dto.AuthorDto;
import ro.ubb.catalog.web.dto.ListenerDto;
import ro.ubb.catalog.web.dto.SpeakerDto;

import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

@RestController
public class SpeakerController {
    private static final Logger log =
            LoggerFactory.getLogger(SpeakerController.class);
    @Autowired
    private AuthorService authorService;
    @Autowired
    private SpeakerService speakerService;


    @Autowired
    private AuthorConverter authorConverter;
    @Autowired
    private SpeakerConverter speakerConverter;

    @RequestMapping(value = "/speakers", method = RequestMethod.POST)
    SpeakerDto uploadPresentation(@RequestBody AuthorDto dto) throws ExecutionException, InterruptedException {

        log.trace("upload file");

        Speaker l=this.speakerService.uploadPresentation(
                authorConverter.convertDtoToModel(dto));

        Speaker u= (Speaker) l;
        SpeakerDto dtoo=speakerConverter.convertModelToDto(u);

        return dtoo;
    }

    @RequestMapping(value="/speakersget/{username}",method= RequestMethod.GET)
    Response getSpeakerByUsername(@PathVariable String username) throws ExecutionException, InterruptedException {
        log.trace("getSpeakerByUsername for username: {}",username);
        Speaker user=this.speakerService.getSpeakerByUsername(username);
        SpeakerDto result = speakerConverter.convertModelToDto(user);
        log.trace("getSpeakerByUsername in controller left");
        return Response.status(Response.Status.OK).entity(result).build();

    }
}
