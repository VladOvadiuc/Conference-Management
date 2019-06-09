package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Listener;
import ro.ubb.catalog.web.dto.ListenerDto;

@Component
public class ListenerConverter extends BaseConverter<Listener, ListenerDto> {
    @Override
    public Listener convertDtoToModel(ListenerDto dto) {
        Listener listener = new Listener();
        listener.setUsername(dto.getUsername());
        listener.setPassword(dto.getPassword());
        listener.setName(dto.getName());
        listener.setAffiliation(dto.getAffiliation());
        listener.setEmail(dto.getEmail());
        listener.setId(dto.getId());
        return listener;
    }

    @Override
    public ListenerDto convertModelToDto(Listener listener) {
        ListenerDto dto = ListenerDto.builder()
                .username(listener.getUsername())
                .password(listener.getPassword())
                .name(listener.getName())
                .affiliation(listener.getAffiliation())
                .email(listener.getEmail())
                .build();
        dto.setId(listener.getId());
        return dto;
    }
}
