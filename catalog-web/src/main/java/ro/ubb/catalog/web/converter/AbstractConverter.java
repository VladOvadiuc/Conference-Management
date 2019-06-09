package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Abstract;
import ro.ubb.catalog.web.dto.AbstractDto;

@Component
public class AbstractConverter extends BaseConverter<Abstract, AbstractDto> {
    @Override
    public Abstract convertDtoToModel(AbstractDto dto) {
        Abstract abstrct = new Abstract();
        abstrct.setTitle(dto.getTitle());
        abstrct.setAbstractPath(dto.getAbstractPath());
        abstrct.setTopics(dto.getTopics());
        abstrct.setKeywords(dto.getKeywords());
        abstrct.setAuthorNames(dto.getAuthorNames());
        abstrct.setStatus(dto.getStatus());
        abstrct.setId(dto.getId());
        return abstrct;
    }

    @Override
    public AbstractDto convertModelToDto(Abstract abstrct) {
        AbstractDto dto = AbstractDto.builder()
                .title(abstrct.getTitle())
                .abstractPath(abstrct.getAbstractPath())
                .topics(abstrct.getTopics())
                .keywords(abstrct.getKeywords())
                .authorNames(abstrct.getAuthorNames())
                .authorId(abstrct.getAuthor().getId())
                .status(abstrct.getStatus())
                .build();
        dto.setId(abstrct.getId());
        return dto;
    }
}
