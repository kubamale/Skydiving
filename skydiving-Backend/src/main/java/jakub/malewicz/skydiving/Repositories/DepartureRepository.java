package jakub.malewicz.skydiving.Repositories;

import jakub.malewicz.skydiving.Models.Departure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartureRepository extends JpaRepository<Departure, Long> {
}
