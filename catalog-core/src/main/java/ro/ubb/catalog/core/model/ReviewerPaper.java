package ro.ubb.catalog.core.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "ReviewerPaper")
@Table(name = "reviewer_paper")
@NoArgsConstructor
public class ReviewerPaper {
    @EmbeddedId
    private ReviewerPaperId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("reviewerId")
    private Reviewer reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("paperId")
    private Paper paper;

    @Column(name = "result")
    private String result;

    public ReviewerPaper(Reviewer reviewer, Paper paper, String result) {
        this.id = new ReviewerPaperId(reviewer.getId(), paper.getId());
        this.reviewer = reviewer;
        this.paper = paper;
        this.result = result;
    }

    public ReviewerPaper(Reviewer reviewer, Paper paper) {
        this.id = new ReviewerPaperId(reviewer.getId(), paper.getId());
        this.reviewer = reviewer;
        this.paper = paper;
    }

    public ReviewerPaperId getId() {
        return id;
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public Paper getPaper() {
        return paper;
    }

    public String getResult() {
        return result;
    }

    public void setId(ReviewerPaperId id) {
        this.id = id;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewerPaper that = (ReviewerPaper) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(reviewer, that.reviewer) &&
                Objects.equals(paper, that.paper) &&
                Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reviewer, paper, result);
    }
}
