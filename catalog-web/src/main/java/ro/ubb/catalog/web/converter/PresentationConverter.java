package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Presentation;
import ro.ubb.catalog.web.dto.PresentationDto;

@Component
public class PresentationConverter extends BaseConverter<Presentation, PresentationDto> {
    @Override
    public Presentation convertDtoToModel(PresentationDto dto) {
        Presentation presentation = new Presentation();
        presentation.setFileName(dto.getFileName());
        presentation.setId(dto.getId());
        return presentation;
    }

    @Override
    public PresentationDto convertModelToDto(Presentation presentation) {
        PresentationDto dto = PresentationDto.builder()
                .fileName(presentation.getFileName())
                .speakerId(presentation.getSpeaker().getId())
                .build();
        dto.setId(presentation.getId());
        return dto;
    }
}
