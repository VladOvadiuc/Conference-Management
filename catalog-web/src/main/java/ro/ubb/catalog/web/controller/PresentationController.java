package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.catalog.core.model.Presentation;
import ro.ubb.catalog.core.service.PresentationService;
import ro.ubb.catalog.web.converter.PresentationConverter;
import ro.ubb.catalog.web.dto.PresentationDto;
import ro.ubb.catalog.web.dto.PresentationsDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class PresentationController {
    private static final Logger log =
            LoggerFactory.getLogger(PresentationController.class);
    @Autowired
    private PresentationService presentationService;
    @Autowired
    private PresentationConverter presentationConverter;

    @RequestMapping(value = "/presentations", method = RequestMethod.GET)
    PresentationsDto getAllPresentations() throws InterruptedException, ExecutionException {
        log.trace("getAllPresentations --- method entered");

        List<Presentation> presentations = presentationService.getAllPresentations();
        List<PresentationDto> dtos = presentationConverter.convertModelsToDtos(presentations);
        PresentationsDto result = new PresentationsDto(dtos);
        log.trace("getAllPresentations --- method left");
        return result;
    }

}
