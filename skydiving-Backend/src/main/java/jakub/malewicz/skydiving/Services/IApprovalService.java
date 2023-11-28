package jakub.malewicz.skydiving.Services;

import jakub.malewicz.skydiving.DTOs.ApprovalDTO;
import jakub.malewicz.skydiving.DTOs.ApprovedDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IApprovalService {

    ResponseEntity<List<ApprovalDTO>> getUsersApprovalRequests(String email);
    ResponseEntity<ApprovalDTO> rejectApprovalRequest(String email);

    ResponseEntity<ApprovalDTO> approveRequest(ApprovedDTO approvedDTO);
}
