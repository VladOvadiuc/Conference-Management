package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Reviewer;
import ro.ubb.catalog.core.model.ReviewerAbstract;
import ro.ubb.catalog.core.service.ReviewerService;
import ro.ubb.catalog.web.converter.ReviewerConverter;
import ro.ubb.catalog.web.dto.*;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class ReviewerController {
    private static final Logger log =
            LoggerFactory.getLogger(ReviewerController.class);

    @Autowired
    private ReviewerService reviewerService;
    @Autowired
    private ReviewerConverter reviewerConverter;

    @RequestMapping(value = "/bid", method = RequestMethod.POST)
    ReviewerAbstractDto bid(@RequestBody ReviewerAbstractDto dto) {
        log.trace("bid enterd: dto={}", dto);

        reviewerService.bid(dto.getReviewerId(), dto.getAbstractId(), dto.getResult());

        return dto;
    }

    @RequestMapping(value="/assign", method = RequestMethod.POST)
    ReviewerPaperDto assignPaper(@RequestBody ReviewerPaperDto dto) throws ExecutionException, InterruptedException {
        log.trace("assign paper: dto={}", dto);
        reviewerService.assignPaper(dto.getReviewerId(), dto.getPaperId());
        return dto;
    }

    @RequestMapping(value="/review", method = RequestMethod.POST)
    ReviewerPaperDto review(@RequestBody ReviewerPaperDto dto) throws ExecutionException, InterruptedException{
        log.trace("review paper entered: dto={}", dto);
        reviewerService.review(dto.getReviewerId(), dto.getPaperId(), dto.getResult());
        return dto;
    }


    @RequestMapping(value="/reviewersget/{username}",method= RequestMethod.GET)
    Response getReviewerByUsername(@PathVariable String username) throws ExecutionException, InterruptedException {
        log.trace("getReviewerByUsername for username: {}",username);
        Reviewer user=this.reviewerService.getReviewerByUsername(username);
        ReviewerDto result = reviewerConverter.convertModelToDto(user);
        log.trace("getReviewerByUsername in controller left");
        return Response.status(Response.Status.OK).entity(result.getId()).build();

    }

    @RequestMapping(value = "/reviewers", method = RequestMethod.GET)
    ReviewersDto getAllReviewers() throws ExecutionException, InterruptedException {
        log.trace("getAllUsers --- method entered");

        List<Reviewer> confferenceUsers = reviewerService.getAllReviewers();
        List<ReviewerDto> dtos = reviewerConverter.convertModelsToDtos(confferenceUsers);
        ReviewersDto result = new ReviewersDto(dtos);

        return result;
    }
}
