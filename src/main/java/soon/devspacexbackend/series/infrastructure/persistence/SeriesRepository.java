package soon.devspacexbackend.series.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soon.devspacexbackend.series.domain.Series;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
    boolean existsSeriesByName(String name);

    Page<Series> findAll(Pageable pageable);
}
