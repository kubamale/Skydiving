package jakub.malewicz.skydiving.Repositories;

import jakub.malewicz.skydiving.Models.Skydiver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkydiverRepository extends JpaRepository<Skydiver, Long> {

    Optional<Skydiver> findByEmail(String email);
}
