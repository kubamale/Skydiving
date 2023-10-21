package jakub.malewicz.skydiving.Repositories;

import jakub.malewicz.skydiving.Models.DepartureUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartureUserRepository extends JpaRepository<DepartureUser, Long> {
}
