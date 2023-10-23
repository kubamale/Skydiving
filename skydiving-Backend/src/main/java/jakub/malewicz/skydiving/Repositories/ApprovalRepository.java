package jakub.malewicz.skydiving.Repositories;

import jakub.malewicz.skydiving.Models.ApproveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRepository extends JpaRepository<ApproveRequest, Long> {



}
