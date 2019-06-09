package ro.ubb.catalog.core.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="chair")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Chair extends CoChair {
    public Chair(String username, String password, String name, String affiliation, String email, String webpage) {
        super(username, password, name, affiliation, email, webpage);
    }
}
