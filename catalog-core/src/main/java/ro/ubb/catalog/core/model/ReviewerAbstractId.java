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
public class ReviewerAbstractId implements Serializable {
    @Column(name = "reviewer_id")
    private Long reviewerId;

    @Column(name = "abstract_id")
    private Long abstractId;

    public Long getReviewerId() {
        return reviewerId;
    }

    public Long getAbstractId() {
        return abstractId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewerAbstractId that = (ReviewerAbstractId) o;
        return Objects.equals(reviewerId, that.reviewerId) &&
                Objects.equals(abstractId, that.abstractId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewerId, abstractId);
    }
}
