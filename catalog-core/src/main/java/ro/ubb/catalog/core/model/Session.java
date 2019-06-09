package ro.ubb.catalog.core.model;


import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Session extends BaseEntity<Long> {
    private String location;
    private LocalDateTime dateTime;

    public Session(String location, LocalDateTime dateTime) {
        this.location = location;
        this.dateTime = dateTime;
    }

    @OneToMany(mappedBy = "session",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<SessionListener> listeners = new ArrayList<>();

    public void addListener(Listener listener) {
        SessionListener sessionListener = new SessionListener(this, listener);
        listeners.add(sessionListener);
        listener.getSessions().add(sessionListener);
    }

    public void removeListener(Listener listener) {
        for (Iterator<SessionListener> iterator = listeners.iterator(); iterator.hasNext();) {
            SessionListener sessionListener = iterator.next();
            if (sessionListener.getSession().equals(this) && sessionListener.getListener().equals(listener)) {
                iterator.remove();
                sessionListener.getListener().getSessions().remove(sessionListener);
                sessionListener.setSession(null);
                sessionListener.setListener(null);
            }
        }
    }

    @OneToMany(mappedBy = "session",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Speaker> speakers = new ArrayList<>();

    public void addSpeaker(Speaker speaker) {
        speakers.add(speaker);
        speaker.setSession(this);
    }

    public void removeSpeaker(Speaker speaker) {
        speakers.remove(speaker);
        speaker.setSession(null);
    }
}
