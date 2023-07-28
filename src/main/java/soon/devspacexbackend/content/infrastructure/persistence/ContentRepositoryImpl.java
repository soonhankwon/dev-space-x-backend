package soon.devspacexbackend.content.infrastructure.persistence;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.domain.QContent;
import soon.devspacexbackend.review.domain.QReview;
import soon.devspacexbackend.review.domain.ReviewType;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ContentRepositoryImpl implements ContentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    QReview review = QReview.review;
    QContent content = QContent.content;

    @Override
    public List<Content> findTop3ContentsByReviewType(ReviewType type) {
        return queryFactory.select(content)
                .from(content)
                .join(review).on(content.id.eq(review.content.id))
                .where(typeEq(type))
                .groupBy(content)
                .orderBy(review.count().desc())
                .limit(3)
                .fetch();
    }

    private BooleanExpression typeEq(ReviewType type) {
        return type == null ? null : review.type.eq(type);
    }
}
