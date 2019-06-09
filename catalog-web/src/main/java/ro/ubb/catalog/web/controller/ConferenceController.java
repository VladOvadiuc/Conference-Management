package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.catalog.core.model.*;
import ro.ubb.catalog.core.service.*;
import ro.ubb.catalog.web.converter.ConferenceConverter;
import ro.ubb.catalog.web.dto.ConferenceDto;

import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

@RestController
public class ConferenceController {
    private static final Logger log =
            LoggerFactory.getLogger(ConferenceController.class);

    @Autowired
    private ListenerService listenerService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private SpeakerService speakerService;
    @Autowired
    private CoChairService coChairService;
    @Autowired
    private ChairService chairService;
    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private ConferenceConverter conferenceConverter;

    @RequestMapping(value="/userStatus/{username}", method = RequestMethod.GET)
    Response getUserStatus(@PathVariable String username) throws ExecutionException, InterruptedException {
        Listener listener = listenerService.getListenerByUsername(username);
        if (listener == null) {
            return Response.status(200).entity("user").build();
        }
        else {
            Author author = authorService.getAuthorByUsername(username);
            if (author == null) {
                return Response.status(200).entity("listener").build();
            }
            else {
                Speaker speaker = speakerService.getSpeakerByUsername(username);
                if (speaker == null) {
                    return Response.status(200).entity("author").build();
                }
                else {
                    return Response.status(200).entity("speaker").build();
                }
            }
        }
    }

    @RequestMapping(value="/pcMemberStatus/{username}", method = RequestMethod.GET)
    Response getPCMemberStatus(@PathVariable String username) throws ExecutionException, InterruptedException {
        Chair chair = chairService.getChairByUsername(username);
        if (chair == null) {
            CoChair coChair = coChairService.getCoChairByUsername(username);
            if (coChair == null) {
                return Response.status(200).entity("reviewer").build();
            }
            else {
                return Response.status(200).entity("cochair").build();
            }
        }
        else {
            return Response.status(200).entity("chair").build();
        }
    }

    @RequestMapping(value="/conferences", method = RequestMethod.GET)
    ConferenceDto getConference() throws ExecutionException, InterruptedException {
        log.trace("get conference");
        Conference conference = conferenceService.getFirstConference();
        ConferenceDto conferenceDto = conferenceConverter.convertModelToDto(conference);
        return conferenceDto;
    }
}
