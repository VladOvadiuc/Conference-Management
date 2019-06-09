package ro.ubb.catalog.core.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "SessionListener")
@Table(name = "session_listener")
@NoArgsConstructor
public class SessionListener {
    @EmbeddedId
    private SessionListenerId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("sessionId")
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("listenerId")
    private Listener listener;

    public SessionListener(Session session, Listener listener) {
        this.session = session;
        this.listener = listener;
        this.id = new SessionListenerId(session.getId(), listener.getId());
    }

    public SessionListenerId getId() {
        return id;
    }

    public void setId(SessionListenerId id) {
        this.id = id;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
