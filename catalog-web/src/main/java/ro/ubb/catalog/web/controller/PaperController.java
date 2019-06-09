package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Abstract;
import ro.ubb.catalog.core.model.ConferenceUser;
import ro.ubb.catalog.core.model.Paper;
import ro.ubb.catalog.core.service.AuthorService;
import ro.ubb.catalog.core.service.PaperService;
import ro.ubb.catalog.web.converter.AbstractConverter;
import ro.ubb.catalog.web.converter.AuthorConverter;
import ro.ubb.catalog.web.converter.PaperConverter;
import ro.ubb.catalog.web.dto.*;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class PaperController {
    private static final Logger log =
            LoggerFactory.getLogger(PaperController.class);

    @Autowired
    private PaperService paperService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorConverter authorConverter;

    @Autowired
    private PaperConverter paperConverter;

    @RequestMapping(value = "/papers", method = RequestMethod.POST)
    PaperDto addPaper(@RequestBody PaperDto paperDto) throws ExecutionException, InterruptedException {

        log.trace("add paper");

        Paper p = this.authorService.addPaper(paperDto.getAuthorId(), paperConverter.convertDtoToModel(paperDto));

        Paper u = (Paper)p;
        PaperDto dto = paperConverter.convertModelToDto(u);

        return dto;
    }

    @RequestMapping(value = "/papers", method = RequestMethod.GET)
    PapersDto getAllPapers() throws ExecutionException, InterruptedException {
        log.trace("getAllPapers --- method entered");

        List<Paper> papers = paperService.getAllPapers();
        List<PaperDto> dtos = paperConverter.convertModelsToDtos(papers);
        PapersDto result = new PapersDto(dtos);

        return result;
    }

    @RequestMapping(value = "/papers/{id}", method = RequestMethod.GET)
    PapersDto getPapersByReviewer(@PathVariable Long id) throws ExecutionException, InterruptedException {
        log.trace("getAllPapersByReviewerId -- mehod enetered");
        List<Paper> papers = paperService.getPapersByReviewerId(id);
        List<PaperDto> dtos = paperConverter.convertModelsToDtos(papers);
        PapersDto result = new PapersDto(dtos);

        return result;
    }

    @RequestMapping(value="/papersget/{title}",method= RequestMethod.GET)
    Response getPaperByTitle(@PathVariable String title) throws ExecutionException, InterruptedException {
        log.trace("ggetPaperByTitle for title: {}",title);
        Paper user=this.paperService.getPaperByTitle(title);
        PaperDto result = paperConverter.convertModelToDto(user);
        log.trace("getPaperByTitle in controller left");
        return Response.status(Response.Status.OK).entity(result).build();

    }

    @Transactional
    @RequestMapping(value="/papers/byAuthorId/{id}", method = RequestMethod.GET)
    PaperDto getPaperByAuthorId(@PathVariable Long id) throws ExecutionException, InterruptedException {
        log.trace("getPaperByAuthorId -- method entered");
        Paper paper = paperService.getPaperByAuthorId(id);
        PaperDto result = paperConverter.convertModelToDto(paper);
        log.trace("getAbstractByAuthorId in controller left");
        return result;
    }

    @Transactional
    @RequestMapping(value="/papers/{id}", method = RequestMethod.PUT)
    PaperDto updatePapers(@PathVariable Long id, @RequestBody PaperDto paperDto) throws ExecutionException, InterruptedException {
        log.trace("updatePapers -- method entered");
        Paper paper = paperConverter.convertDtoToModel(paperDto);
        Paper new_paper = paperService.updatePaper(id, paper);
        PaperDto result = paperConverter.convertModelToDto(new_paper);
        return result;
    }

    @Transactional
    @RequestMapping(value = "/papers/accept", method = RequestMethod.GET)
    Response acceptPapers() throws ExecutionException, InterruptedException {
        paperService.acceptPapers();
        return Response.status(200).entity("OK").build();
    }
}
