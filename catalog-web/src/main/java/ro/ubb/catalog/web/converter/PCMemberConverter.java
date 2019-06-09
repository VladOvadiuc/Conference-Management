package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.PCMember;
import ro.ubb.catalog.web.dto.PCMemberDto;

@Component
public class PCMemberConverter extends BaseConverter<PCMember, PCMemberDto> {
    @Override
    public PCMember convertDtoToModel(PCMemberDto dto) {
        PCMember member=new PCMember();
        member.setUsername(dto.getUsername());
        member.setPassword(dto.getPassword());
        member.setName(dto.getName());
        member.setAffiliation(dto.getAffiliation());
        member.setEmail(dto.getEmail());
        member.setWebpage(dto.getWebpage());
        member.setId(dto.getId());
        return member;
    }

    @Override
    public PCMemberDto convertModelToDto(PCMember user) {
        PCMemberDto dto = PCMemberDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .affiliation(user.getAffiliation())
                .email(user.getEmail())
                .webpage(user.getWebpage())
                .build();
        dto.setId(user.getId());
        return dto;
    }
}
