package ro.ubb.catalog.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity(name = "Reviewer")
@Table(name="reviewer")
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class Reviewer extends PCMember {
    public Reviewer(String username, String password, String name, String affiliation, String email, String webpage) {
        super(username, password, name, affiliation, email, webpage);
    }
    @OneToMany(mappedBy = "reviewer",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private List<ReviewerAbstract> abstracts = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Reviewer reviewer = (Reviewer) o;
        return Objects.equals(abstracts, reviewer.abstracts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), abstracts);
    }

    public void addAbstract(Abstract abstrct, String result) {
        ReviewerAbstract reviewerAbstract = new ReviewerAbstract(this, abstrct,result);
        abstracts.add(reviewerAbstract);
        abstrct.getReviewers().add(reviewerAbstract);
    }

    public void removeAbstract(Abstract abstrct) {
        for (Iterator<ReviewerAbstract> iterator = abstracts.iterator(); iterator.hasNext();) {
            ReviewerAbstract reviewerAbstract = iterator.next();
            if (reviewerAbstract.getReviewer().equals(this) && reviewerAbstract.getAbstrct().equals(abstrct)) {
                iterator.remove();
                reviewerAbstract.getAbstrct().getReviewers().remove(reviewerAbstract);
                reviewerAbstract.setAbstrct(null);
                reviewerAbstract.setReviewer(null);
            }
        }
    }

    @OneToMany(mappedBy = "reviewer",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ReviewerPaper> papers = new ArrayList<>();

    public void addPaper(Paper paper, String result) {
        ReviewerPaper reviewerPaper = new ReviewerPaper(this, paper);
        papers.add(reviewerPaper);
        paper.getReviewers().add(reviewerPaper);
    }

    public void addPaper(Paper paper) {
        ReviewerPaper reviewerPaper = new ReviewerPaper(this, paper);
        papers.add(reviewerPaper);
        paper.getReviewers().add(reviewerPaper);
    }
}
