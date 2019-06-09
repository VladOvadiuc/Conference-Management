package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Created by Patricia on 03/02/2019
 **/

@Entity
@Table(name="cfuser")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class ConferenceUser extends BaseEntity<Long> {
    private String username;
    private String password;
    private String name;
    private String affiliation;
    private String email;



}


