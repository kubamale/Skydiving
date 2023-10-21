package jakub.malewicz.skydiving.Repositories;

import jakub.malewicz.skydiving.Models.Skydiver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkydiverRepository extends JpaRepository<Skydiver, Long> {
}
