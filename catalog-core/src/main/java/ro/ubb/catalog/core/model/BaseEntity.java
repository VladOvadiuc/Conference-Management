package ro.ubb.catalog.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by Patricia on 03/02/2019
 **/
@MappedSuperclass
@NoArgsConstructor
@Data
public class BaseEntity<ID> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private ID id;
}
