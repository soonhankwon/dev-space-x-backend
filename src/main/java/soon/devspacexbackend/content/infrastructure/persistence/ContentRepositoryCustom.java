package soon.devspacexbackend.content.infrastructure.persistence;

import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.review.domain.ReviewType;

import java.util.List;

public interface ContentRepositoryCustom {
    List<Content> findTop3ContentsByReviewType(ReviewType type);
}
