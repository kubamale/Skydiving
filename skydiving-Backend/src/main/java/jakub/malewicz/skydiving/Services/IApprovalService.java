package jakub.malewicz.skydiving.Services;

import jakub.malewicz.skydiving.DTOs.ApprovalDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IApprovalService {

    ResponseEntity<List<ApprovalDTO>> getUsersApprovalRequests(String email);
    ResponseEntity<String> rejectApprovalRequest(String email);

}
