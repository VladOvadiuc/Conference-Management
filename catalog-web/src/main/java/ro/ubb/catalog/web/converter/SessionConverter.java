package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Session;
import ro.ubb.catalog.web.dto.SessionDto;

@Component
public class SessionConverter extends BaseConverter<Session, SessionDto> {
    @Override
    public Session convertDtoToModel(SessionDto dto) {
        Session session = new Session();
        session.setLocation(dto.getLocation());
        session.setDateTime(dto.getDateTime());
        session.setId(dto.getId());
        return session;
    }

    @Override
    public SessionDto convertModelToDto(Session session) {
        SessionDto dto = SessionDto.builder()
                .location(session.getLocation())
                .dateTime(session.getDateTime())
                .build();
        dto.setId(session.getId());
        return dto;
    }
}
