package jakub.malewicz.skydiving.Controllers;

import jakub.malewicz.skydiving.DTOs.ApprovalDTO;
import jakub.malewicz.skydiving.Services.IApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/approval")
public class ApprovalController {

    private final IApprovalService approvalService;
    @GetMapping
    public ResponseEntity<List<ApprovalDTO>> getUsersApprovalRequests(@RequestParam String email){
        return approvalService.getUsersApprovalRequests(email);
    }

    @DeleteMapping
    public ResponseEntity<String> rejectApprovalRequest(@RequestParam String email){
        return approvalService.rejectApprovalRequest(email);
    }

}
