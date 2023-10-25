package jakub.malewicz.skydiving.Repositories;

import jakub.malewicz.skydiving.Models.Plane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaneRepository extends JpaRepository<Plane, Long> {
    Optional<Plane> findByName(String name);
}
