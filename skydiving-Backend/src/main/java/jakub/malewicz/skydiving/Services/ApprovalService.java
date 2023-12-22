package jakub.malewicz.skydiving.Services;

import jakub.malewicz.skydiving.DTOs.ApprovalDTO;
import jakub.malewicz.skydiving.DTOs.ApprovedDTO;
import jakub.malewicz.skydiving.Exceptions.BadRequestException;
import jakub.malewicz.skydiving.Models.ApproveRequest;
import jakub.malewicz.skydiving.Models.Role;
import jakub.malewicz.skydiving.Models.Skydiver;
import jakub.malewicz.skydiving.Repositories.ApprovalRepository;
import jakub.malewicz.skydiving.Repositories.RoleRepository;
import jakub.malewicz.skydiving.Repositories.SkydiverRepository;
import jakub.malewicz.skydiving.enums.Privilege;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApprovalService implements IApprovalService{

    private final ApprovalRepository approvalRepository;
    private final SkydiverRepository skydiverRepository;
    private final RoleRepository roleRepository;

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
    public ResponseEntity<ApprovalDTO> rejectApprovalRequest(String email) {
        Skydiver skydiver = skydiverRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("No user with email: " + email));

        ApproveRequest approveRequests = approvalRepository.findByRequesterId(skydiver.getId())
                .orElseThrow(() -> new BadRequestException("No request for that user"));

        approvalRepository.delete(approveRequests);
        skydiverRepository.delete(skydiver);

        return ResponseEntity.ok(
                new ApprovalDTO(
                        approveRequests.getRequester().getFirstName(),
                        approveRequests.getRequester().getLastName(),
                        approveRequests.getRequester().getEmail(),
                        approveRequests.getRequester().getLicence()
                )
        );
    }

    @Override
    public ResponseEntity<ApprovalDTO> approveRequest(ApprovedDTO approvedDTO) {
        Skydiver userToApprove = skydiverRepository.findByEmail(approvedDTO.email())
                .orElseThrow(() -> new BadRequestException("No user with email " + approvedDTO.email()));
        ApproveRequest request = approvalRepository.findByRequesterId(userToApprove.getId())
                .orElseThrow(() -> new BadRequestException("No request for that user"));
        userToApprove.setLicence(approvedDTO.licence());

        Role role = roleRepository.findByName(approvedDTO.role())
                        .orElseThrow(() -> new BadRequestException("No role "+ approvedDTO.role()));
        userToApprove.setRole(role);
        if (approvedDTO.privileges().isPresent()){
            userToApprove.setPrivileges(approvedDTO.privileges().get().stream().map(Privilege::valueOf).collect(Collectors.toSet()));
        }

        skydiverRepository.save(userToApprove);
        approvalRepository.delete(request);

        return ResponseEntity.ok(new ApprovalDTO(
                request.getRequester().getFirstName(),
                request.getRequester().getLastName(),
                request.getRequester().getEmail(),
                request.getRequester().getLicence()
        ));
    }
}
