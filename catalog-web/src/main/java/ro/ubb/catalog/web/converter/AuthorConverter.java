package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Author;
import ro.ubb.catalog.web.dto.AuthorDto;

@Component
public class AuthorConverter extends BaseConverter<Author, AuthorDto> {
    @Override
    public Author convertDtoToModel(AuthorDto dto) {
        Author author = new Author();
        author.setUsername(dto.getUsername());
        author.setPassword(dto.getPassword());
        author.setName(dto.getName());
        author.setAffiliation(dto.getAffiliation());
        author.setEmail(dto.getEmail());
        author.setId(dto.getId());
        return author;
    }

    @Override
    public AuthorDto convertModelToDto(Author author) {
        AuthorDto dto = AuthorDto.builder()
                .username(author.getUsername())
                .password(author.getPassword())
                .name(author.getName())
                .affiliation(author.getAffiliation())
                .email(author.getEmail())
                .build();
        dto.setId(author.getId());
        return dto;
    }
}
