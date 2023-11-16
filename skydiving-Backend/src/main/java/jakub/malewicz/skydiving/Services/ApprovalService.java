package jakub.malewicz.skydiving.Services;

import jakub.malewicz.skydiving.DTOs.ApprovalDTO;
import jakub.malewicz.skydiving.Exceptions.BadRequestException;
import jakub.malewicz.skydiving.Models.ApproveRequest;
import jakub.malewicz.skydiving.Models.Skydiver;
import jakub.malewicz.skydiving.Repositories.ApprovalRepository;
import jakub.malewicz.skydiving.Repositories.SkydiverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApprovalService implements IApprovalService{

    private final ApprovalRepository approvalRepository;
    private final SkydiverRepository skydiverRepository;

    @Override
    public ResponseEntity<List<ApprovalDTO>> getUsersApprovalRequests(String email) {
        Skydiver skydiver = skydiverRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("No user with email: " + email));
        List<ApproveRequest> approveRequests = approvalRepository.findByApproverId(skydiver.getId());

        return ResponseEntity.ok(approveRequests.stream().map(request -> new ApprovalDTO(
                request.getRequester().getFirstName(),
                request.getRequester().getLastName(),
                request.getRequester().getEmail(),
                request.getRequester().getLicence()
        )).toList());
    }

    @Override
    public ResponseEntity<String> rejectApprovalRequest(String email) {
        Skydiver skydiver = skydiverRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("No user with email: " + email));

        List<ApproveRequest> approveRequests = approvalRepository.findByRequesterId(skydiver.getId());

        approvalRepository.deleteAll(approveRequests);
        skydiverRepository.delete(skydiver);

        return ResponseEntity.ok("Deleted user with email: " + email);
    }
}
