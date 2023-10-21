package jakub.malewicz.skydiving.Repositories;

import jakub.malewicz.skydiving.Models.Plane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaneRepository extends JpaRepository<Plane, Long> {

}
