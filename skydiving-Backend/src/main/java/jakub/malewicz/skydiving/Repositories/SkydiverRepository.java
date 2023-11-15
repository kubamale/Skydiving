package jakub.malewicz.skydiving.Repositories;

import jakub.malewicz.skydiving.Models.Skydiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SkydiverRepository extends JpaRepository<Skydiver, Long> {

    Optional<Skydiver> findByEmail(String email);

    @Query(value = "SELECT * FROM skydiver s JOIN basic_user b ON s.id = b.id  WHERE role_id NOT IN (SELECT id FROM _role WHERE name = 'USER' OR name = 'AWAITING_APPROVAL')", nativeQuery = true)
    List<Skydiver> findApprovers();
}
