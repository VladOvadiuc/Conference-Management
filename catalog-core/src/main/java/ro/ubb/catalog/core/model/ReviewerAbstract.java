package ro.ubb.catalog.core.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "ReviewerAbstract")
@Table(name = "reviewer_abstract")
@NoArgsConstructor
public class ReviewerAbstract {
    @EmbeddedId
    private ReviewerAbstractId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("reviewerId")
    private Reviewer reviewer;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("abstractId")
    private Abstract abstrct;

    //pleased to review, could review, does not want to review
    @Column(name = "result")
    private String result;

    public ReviewerAbstract(Reviewer reviewer, Abstract abstrct, String result) {
        this.id = new ReviewerAbstractId(reviewer.getId(), abstrct.getId());
        this.reviewer = reviewer;
        this.abstrct = abstrct;
        this.result=result;
    }

    public ReviewerAbstractId getId() {
        return id;
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public Abstract getAbstrct() {
        return abstrct;
    }

    public String getResult() {
        return result;
    }

    public void setId(ReviewerAbstractId id) {
        this.id = id;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    public void setAbstrct(Abstract abstrct) {
        this.abstrct = abstrct;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewerAbstract that = (ReviewerAbstract) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(reviewer, that.reviewer) &&
                Objects.equals(abstrct, that.abstrct) &&
                Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reviewer, abstrct, result);
    }
}
