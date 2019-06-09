package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.*;
import ro.ubb.catalog.core.model.Abstract;
import ro.ubb.catalog.core.service.AbstrctService;
import ro.ubb.catalog.core.service.AuthorService;
import ro.ubb.catalog.core.service.UserService;
import ro.ubb.catalog.web.converter.AbstractConverter;
import ro.ubb.catalog.web.converter.AuthorConverter;
import ro.ubb.catalog.web.converter.UserConverter;
import ro.ubb.catalog.web.dto.*;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class AbstractController {

    private static final Logger log =
            LoggerFactory.getLogger(AbstractController.class);
    @Autowired
    private AbstrctService abstractService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorConverter authorConverter;

    @Autowired
    private AbstractConverter abstractConverter;

    @RequestMapping(value = "/abstracts", method = RequestMethod.POST)
    AbstractDto addAbstract(@RequestBody AbstractDto adto) throws ExecutionException, InterruptedException {

        log.trace("add abstract");

        Abstract l=this.authorService.addAbstract(adto.getAuthorId(),abstractConverter.convertDtoToModel(adto));

        Abstract u=(Abstract)l;
        AbstractDto dtoo=abstractConverter.convertModelToDto(u);

        return dtoo;
    }

    @RequestMapping(value = "/abstracts", method = RequestMethod.GET)
    AbstractsDto getAllAbstracts() throws ExecutionException, InterruptedException {
        log.trace("getAllAbstracts --- method entered");

        List<Abstract> abstracts = abstractService.getAllAbstracts();
        List<AbstractDto> dtos = abstractConverter.convertModelsToDtos(abstracts);
        AbstractsDto result = new AbstractsDto(dtos);

        return result;
    }

    @RequestMapping(value = "/abstracts/{id}", method = RequestMethod.GET)
    AbstractDto getById(@PathVariable Long id) throws ExecutionException, InterruptedException {
        log.trace("getById --- method entered");

        Abstract a=abstractService.getAbstractById(id);
        AbstractDto dto=abstractConverter.convertModelToDto(a);

        return dto;
    }

    @RequestMapping(value="/abstractsget/{title}",method= RequestMethod.GET)
    Response getAbstractByTitle(@PathVariable String title) throws ExecutionException, InterruptedException {
        log.trace("ggetAbstractByTitle for title: {}",title);
        Abstract user=this.abstractService.getAbstractByTitle(title);
        AbstractDto result = abstractConverter.convertModelToDto(user);
        log.trace("getAbstractByTitle in controller left");
        return Response.status(Response.Status.OK).entity(result).build();

    }
    @Transactional
    @RequestMapping(value="/abstracts/byAuthorId/{id}", method =  RequestMethod.GET)
    AbstractDto getAbstractByAuthorId(@PathVariable Long id) throws ExecutionException, InterruptedException {
        log.trace("getAbstractByAuthorId -- method entered");
        Abstract abstrct = abstractService.getAbstractByAuthorId(id);
        AbstractDto result = abstractConverter.convertModelToDto(abstrct);
        log.trace("getAbstractByAthorId in controller left");
        return result;
    }


    @RequestMapping(value="/abstracts/{id}", method = RequestMethod.PUT)
    AbstractDto updateAbstract(@PathVariable Long id, @RequestBody AbstractDto abstractDto) throws ExecutionException, InterruptedException {
        log.trace("updateAbstract -- method entered");
        Abstract abstrct = abstractConverter.convertDtoToModel(abstractDto);
        Abstract new_abstract = abstractService.updateAbstract(id, abstrct);
        AbstractDto result = abstractConverter.convertModelToDto(new_abstract);
        return result;
    }


}
