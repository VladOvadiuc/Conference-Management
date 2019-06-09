package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.CoChair;
import ro.ubb.catalog.web.dto.CoChairDto;

@Component
public class CoChairConverter extends BaseConverter<CoChair, CoChairDto> {
    @Override
    public CoChair convertDtoToModel(CoChairDto dto) {
        CoChair coChair=new CoChair();
        coChair.setUsername(dto.getUsername());
        coChair.setPassword(dto.getPassword());
        coChair.setName(dto.getName());
        coChair.setAffiliation(dto.getAffiliation());
        coChair.setEmail(dto.getEmail());
        coChair.setWebpage(dto.getWebpage());
        coChair.setId(dto.getId());
        return coChair;
    }

    @Override
    public CoChairDto convertModelToDto(CoChair coChair) {
        CoChairDto dto = CoChairDto.builder()
                .username(coChair.getUsername())
                .password(coChair.getPassword())
                .name(coChair.getName())
                .affiliation(coChair.getAffiliation())
                .email(coChair.getEmail())
                .webpage(coChair.getWebpage())
                .build();
        dto.setId(coChair.getId());
        return dto;
    }
}
