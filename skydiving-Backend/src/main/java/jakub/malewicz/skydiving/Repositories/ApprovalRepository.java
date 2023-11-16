package jakub.malewicz.skydiving.Repositories;

import jakub.malewicz.skydiving.Models.ApproveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalRepository extends JpaRepository<ApproveRequest, Long> {

    List<ApproveRequest> findByApproverId(long id);


    List<ApproveRequest> findByRequesterId(long id);
}
