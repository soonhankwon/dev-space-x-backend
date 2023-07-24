package soon.devspacexbackend.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.series.domain.Series;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    Page<Content> findAll(Pageable pageable);

    Page<Content> findAllBySeries(Series series, Pageable pageable);
}
