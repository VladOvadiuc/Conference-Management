package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Conference;
import ro.ubb.catalog.web.dto.ConferenceDto;

@Component
public class ConferenceConverter extends BaseConverter<Conference, ConferenceDto> {
    @Override
    public Conference convertDtoToModel(ConferenceDto dto) {
        Conference conference = new Conference();
        conference.setName(dto.getName());
        conference.setDeadlineAbstract(dto.getDeadlineAbstract());
        conference.setDeadlinePaper(dto.getDeadlinePaper());
        conference.setStartPaper(dto.getStartPaper());
        conference.setStartDate(dto.getStartDate());
        conference.setEndDate(dto.getEndDate());
        conference.setId(dto.getId());
        return conference;
    }

    @Override
    public ConferenceDto convertModelToDto(Conference conference) {
        ConferenceDto dto = ConferenceDto.builder()
                .name(conference.getName())
                .location(conference.getLocation())
                .deadlineAbstract(conference.getDeadlineAbstract())
                .deadlinePaper(conference.getDeadlinePaper())
                .startPaper(conference.getStartPaper())
                .startDate(conference.getStartDate())
                .endDate(conference.getEndDate())
                .build();
        dto.setId(conference.getId());
        return dto;
    }
}
