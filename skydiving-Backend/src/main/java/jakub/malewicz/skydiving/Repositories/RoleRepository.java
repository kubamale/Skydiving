package jakub.malewicz.skydiving.Repositories;

import jakub.malewicz.skydiving.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
