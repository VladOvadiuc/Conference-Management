package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Presentation extends BaseEntity<Long> {
    private String fileName;

    public Presentation(String fileName) {
        this.fileName = fileName;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "speaker_id")
    private Speaker speaker;
}
