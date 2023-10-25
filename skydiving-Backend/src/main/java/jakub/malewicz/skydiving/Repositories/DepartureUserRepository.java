package jakub.malewicz.skydiving.Repositories;

import jakub.malewicz.skydiving.Models.DepartureUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartureUserRepository extends JpaRepository<DepartureUser, Long> {
    List<DepartureUser> getByDepartureId(long id);
    Optional<DepartureUser> findByDepartureIdAndSkydiverId(long departure_id, long skydiver_id);
}
