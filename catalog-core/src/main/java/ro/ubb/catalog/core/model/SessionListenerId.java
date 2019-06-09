package ro.ubb.catalog.core.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class SessionListenerId implements Serializable {
    @Column(name = "session_id")
    private Long sessionId;

    @Column(name = "listener_id")
    private Long listenerId;

    public Long getSessionId() {
        return sessionId;
    }

    public Long getListenerId() {
        return listenerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionListenerId that = (SessionListenerId) o;
        return Objects.equals(sessionId, that.sessionId) &&
                Objects.equals(listenerId, that.listenerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, listenerId);
    }
}
