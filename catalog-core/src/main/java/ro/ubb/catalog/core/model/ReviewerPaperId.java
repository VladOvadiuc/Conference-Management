package ro.ubb.catalog.core.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ReviewerPaperId implements Serializable {
    @Column(name = "reviewer_id")
    private Long reviewerId;

    @Column(name = "paper_id")
    private Long paperId;

    public Long getReviewerId() {
        return reviewerId;
    }

    public Long getPaperId() {
        return paperId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewerPaperId that = (ReviewerPaperId) o;
        return Objects.equals(reviewerId, that.reviewerId) &&
                Objects.equals(paperId, that.paperId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewerId, paperId);
    }
}
