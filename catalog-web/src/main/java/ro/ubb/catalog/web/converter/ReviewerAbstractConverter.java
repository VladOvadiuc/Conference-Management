package ro.ubb.catalog.web.converter;


import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.ReviewerAbstract;
import ro.ubb.catalog.web.dto.ReviewerAbstractDto;

@Component
public class ReviewerAbstractConverter  {


    public ReviewerAbstractDto convertModelToDto(ReviewerAbstract reviewerAbstract) {
        ReviewerAbstractDto reviewerAbstractDto= ReviewerAbstractDto.builder()
                    .abstractId(reviewerAbstract.getAbstrct().getId())
                    .reviewerId(reviewerAbstract.getAbstrct().getId())
                    .result(reviewerAbstract.getResult())
                    .build();
        return reviewerAbstractDto;
    }
}
