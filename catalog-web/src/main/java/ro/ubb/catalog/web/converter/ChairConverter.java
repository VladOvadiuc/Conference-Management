package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Chair;
import ro.ubb.catalog.web.dto.ChairDto;

@Component
public class ChairConverter extends BaseConverter<Chair, ChairDto> {
    @Override
    public Chair convertDtoToModel(ChairDto dto) {
        Chair chair=new Chair();
        chair.setUsername(dto.getUsername());
        chair.setPassword(dto.getPassword());
        chair.setName(dto.getName());
        chair.setAffiliation(dto.getAffiliation());
        chair.setEmail(dto.getEmail());
        chair.setWebpage(dto.getWebpage());
        chair.setId(dto.getId());
        return chair;
    }

    @Override
    public ChairDto convertModelToDto(Chair chair) {
        ChairDto dto = ChairDto.builder()
                .username(chair.getUsername())
                .password(chair.getPassword())
                .name(chair.getName())
                .affiliation(chair.getAffiliation())
                .email(chair.getEmail())
                .webpage(chair.getWebpage())
                .build();
        dto.setId(chair.getId());
        return dto;
    }
}
