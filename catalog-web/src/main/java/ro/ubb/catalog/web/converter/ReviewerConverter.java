package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Reviewer;
import ro.ubb.catalog.web.dto.ReviewerDto;

@Component
public class ReviewerConverter extends BaseConverter<Reviewer, ReviewerDto> {
    @Override
    public Reviewer convertDtoToModel(ReviewerDto dto) {
        Reviewer reviewer=new Reviewer();
        reviewer.setUsername(dto.getUsername());
        reviewer.setPassword(dto.getPassword());
        reviewer.setName(dto.getName());
        reviewer.setAffiliation(dto.getAffiliation());
        reviewer.setEmail(dto.getEmail());
        reviewer.setWebpage(dto.getWebpage());
        reviewer.setId(dto.getId());
        return reviewer;
    }

    @Override
    public ReviewerDto convertModelToDto(Reviewer reviewer) {
        ReviewerDto dto = ReviewerDto.builder()
                .username(reviewer.getUsername())
                .password(reviewer.getPassword())
                .name(reviewer.getName())
                .affiliation(reviewer.getAffiliation())
                .email(reviewer.getEmail())
                .webpage(reviewer.getWebpage())
                .build();
        dto.setId(reviewer.getId());
        return dto;
    }
}
