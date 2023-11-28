package jakub.malewicz.skydiving.Repositories;

import jakub.malewicz.skydiving.Models.ApproveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.List;
import java.util.Optional;

public interface ApprovalRepository extends JpaRepository<ApproveRequest, Long> {

    List<ApproveRequest> findByApproverId(long id);

    Optional<ApproveRequest> findByRequesterId(long id);
}
