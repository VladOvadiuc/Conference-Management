package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Paper;
import ro.ubb.catalog.web.dto.PaperDto;

@Component
public class PaperConverter extends BaseConverter<Paper, PaperDto> {
    @Override
    public Paper convertDtoToModel(PaperDto dto) {
        Paper paper = new Paper();
        paper.setTitle(dto.getTitle());
        paper.setFilePath(dto.getFilePath());
        paper.setId(dto.getId());
        return paper;
    }

    @Override
    public PaperDto convertModelToDto(Paper paper) {
        PaperDto dto = PaperDto.builder()
                .title(paper.getTitle())
                .filePath(paper.getFilePath())
                .authorId(paper.getAuthor().getId())
                .build();
        dto.setId(paper.getId());
        return dto;
    }
}
