package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Speaker;
import ro.ubb.catalog.web.dto.SpeakerDto;

@Component
public class SpeakerConverter extends BaseConverter<Speaker, SpeakerDto> {
    @Override
    public Speaker convertDtoToModel(SpeakerDto dto) {
        Speaker speaker = new Speaker();
        speaker.setUsername(dto.getUsername());
        speaker.setPassword(dto.getPassword());
        speaker.setName(dto.getName());
        speaker.setAffiliation(dto.getAffiliation());
        speaker.setEmail(dto.getEmail());
        speaker.setId(dto.getId());
        return speaker;
    }

    @Override
    public SpeakerDto convertModelToDto(Speaker speaker) {
        SpeakerDto dto = SpeakerDto.builder()
                .username(speaker.getUsername())
                .password(speaker.getPassword())
                .name(speaker.getName())
                .affiliation(speaker.getAffiliation())
                .email(speaker.getEmail())
                .build();
        dto.setId(speaker.getId());
        return dto;
    }
}
