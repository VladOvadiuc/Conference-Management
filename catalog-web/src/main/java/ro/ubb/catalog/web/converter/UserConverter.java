package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.ConferenceUser;
import ro.ubb.catalog.web.dto.UserDto;


@Component
public class UserConverter extends BaseConverter<ConferenceUser, UserDto> {
    @Override
    public ConferenceUser convertDtoToModel(UserDto dto) {
        ConferenceUser user = ConferenceUser.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .name(dto.getName())
                .affiliation(dto.getAffiliation())
                .email(dto.getEmail())
                .build();
        user.setId(dto.getId());
        return user;
    }

    @Override
    public UserDto convertModelToDto(ConferenceUser user) {
       UserDto dto = UserDto.builder()
                .username(user.getUsername())
               .password(user.getPassword())
               .name(user.getName())
               .affiliation(user.getAffiliation())
               .email(user.getEmail())
                .build();
        dto.setId(user.getId());
        return dto;
    }
}
