package ro.ubb.catalog.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Abstract")
@Table(name = "abstract")
@NoArgsConstructor
@Data
public class Abstract extends BaseEntity<Long> {
    private String abstractPath;
    private String title;
    private String keywords; //list of keywords -> trim
    private String topics; //list of topics

    private String authorNames;
    private String status;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @OneToMany(mappedBy = "abstrct",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private List<ReviewerAbstract> reviewers = new ArrayList<>();

    @Override
    public String toString() {
        return "Abstract{" +
                "abstractPath='" + abstractPath + '\'' +
                ", title='" + title + '\'' +
                ", keywords='" + keywords + '\'' +
                ", topics='" + topics + '\'' +
                ", authorNames='" + authorNames + '\'' +
                ", author=" + author.getUsername() +
                '}';
    }
}
