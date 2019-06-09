package ro.ubb.catalog.core.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Author")
@Table(name = "author")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Author extends ConferenceUser {
    public Author(String username, String password, String name, String affiliation, String email) {
        super(username, password, name, affiliation, email);
    }

    @OneToOne(mappedBy = "author", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, optional = true, orphanRemoval = true)
    private Abstract abstrct;

    public void addAbstract(Abstract abstrct) {
        abstrct.setAuthor(this);
        this.abstrct = abstrct;
    }

    @OneToOne(mappedBy = "author", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, optional = true)
    private Paper paper;

    public void addPaper(Paper paper) {
        paper.setAuthor(this);
        this.paper = paper;
    }

    @Override
    public String toString() {
        return "Author{" +
                "abstrct=" + abstrct.getTitle() +
                ", paper=" + paper.getTitle() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Author author = (Author) o;
        return Objects.equals(abstrct.getId(), author.abstrct.getId()) &&
                Objects.equals(paper.getId(), author.paper.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), abstrct.getId(), paper.getId());
    }
}
