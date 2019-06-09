package ro.ubb.catalog.core.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
public class Speaker extends Author {
    public Speaker(String username, String password, String name, String affiliation, String email) {
        super(username, password, name, affiliation, email);
    }

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OneToOne(mappedBy = "speaker", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, optional = true)
    private Presentation presentation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Speaker speaker = (Speaker) o;
        return Objects.equals(session, speaker.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), session);
    }
}
