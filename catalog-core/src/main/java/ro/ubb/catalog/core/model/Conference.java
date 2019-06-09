package ro.ubb.catalog.core.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="conference")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Conference extends BaseEntity<Long> {
     private String name;
     private String location;
     private String deadlineAbstract;

     private String deadlinePaper;
     private String startPaper;

     private String startDate;
     private String endDate;
     //call for papers
     //pc
     //sections
     //participants number

}
