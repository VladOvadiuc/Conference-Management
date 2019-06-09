package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Paper")
@Table(name="paper")
@NoArgsConstructor
@Data
public class Paper extends BaseEntity<Long> {
    private String title;
    private String filePath;

    public Paper(String title, String filePath) {
        this.title = title;
        this.filePath = filePath;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @OneToMany(mappedBy = "paper",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ReviewerPaper> reviewers = new ArrayList<>();

}
