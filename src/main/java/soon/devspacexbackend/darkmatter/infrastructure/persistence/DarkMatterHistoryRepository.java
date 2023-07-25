package soon.devspacexbackend.darkmatter.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soon.devspacexbackend.darkmatter.domain.DarkMatterHistory;
import soon.devspacexbackend.user.domain.User;

import java.util.List;

@Repository
public interface DarkMatterHistoryRepository extends JpaRepository<DarkMatterHistory, Long> {
    List<DarkMatterHistory> findDarkMatterHistoriesByUserOrderByCreatedAtDesc(User user);
}
