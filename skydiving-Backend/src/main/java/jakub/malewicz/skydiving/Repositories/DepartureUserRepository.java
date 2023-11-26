package jakub.malewicz.skydiving.Repositories;

import jakub.malewicz.skydiving.Models.DepartureUser;
import jakub.malewicz.skydiving.enums.JumpType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface DepartureUserRepository extends JpaRepository<DepartureUser, Long> {
    List<DepartureUser> getByDepartureId(long id);

    @Query(value = "SELECT d.id,d.aff_student, d.jump_type, d.customer_id, d.skydiver_id, d.departure_id FROM departure_users d WHERE d.departure_id = :dep AND d.skydiver_id IN :skydivers", nativeQuery = true)
    List<DepartureUser> findByDepartureIdWhereInSkydiverId(@Param("dep") Long departure_id, @Param("skydivers") List<Long> skydiver_id);

    List<DepartureUser> findByDepartureIdAndJumpType(long Id, JumpType jumpType);
}
