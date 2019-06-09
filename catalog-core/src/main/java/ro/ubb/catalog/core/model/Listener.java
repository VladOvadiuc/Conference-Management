package ro.ubb.catalog.core.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
public class Listener extends ConferenceUser {
    public Listener(String username, String password, String name, String affiliation, String email) {
        super(username, password, name, affiliation, email);
    }
    @OneToMany(mappedBy = "listener",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<SessionListener> sessions = new ArrayList<>();
}
