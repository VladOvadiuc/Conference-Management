package ro.ubb.catalog.core.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="pcmember")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PCMember extends ConferenceUser {
    private String webpage;

    public PCMember(String username, String password, String name, String affiliation, String email, String webpage) {
        super(username, password, name, affiliation, email);
        this.webpage = webpage;
    }
}
