package jakub.malewicz.skydiving.Services;

import jakub.malewicz.skydiving.DTOs.UserInfoDTO;
import jakub.malewicz.skydiving.Repositories.CustomerRepository;
import jakub.malewicz.skydiving.Repositories.SkydiverRepository;
import jakub.malewicz.skydiving.enums.Privilege;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final SkydiverRepository skydiverRepository;
    private final CustomerRepository customerRepository;


    @Override
    public ResponseEntity<List<UserInfoDTO>> getInstructors() {
        return ResponseEntity.ok(skydiverRepository.findAll().stream().filter(skydiver -> skydiver.getPrivileges().contains(Privilege.AFF_INSTRUCTOR))
                .map(skydiver -> new UserInfoDTO(
                        skydiver.getEmail(),
                        skydiver.getFirstName(),
                        skydiver.getLastName()
                )).toList());
    }

    @Override
    public ResponseEntity<List<UserInfoDTO>> getTandemPilots() {
        return ResponseEntity.ok(skydiverRepository.findAll().stream().filter(skydiver -> skydiver.getPrivileges().contains(Privilege.TANDEM_PILOT))
                .map(skydiver -> new UserInfoDTO(
                        skydiver.getEmail(),
                        skydiver.getFirstName(),
                        skydiver.getLastName()
                )).toList());
    }

    @Override
    public ResponseEntity<List<UserInfoDTO>> getCustomers() {
        return ResponseEntity.ok(customerRepository.findAll().stream().map(c -> new UserInfoDTO(c.getEmail(), c.getFirstName(), c.getLastName())).toList());
    }

    @Override
    public ResponseEntity<List<UserInfoDTO>> getSkydivers() {
        return ResponseEntity.ok(skydiverRepository.findAll().stream().map(s -> new UserInfoDTO(s.getEmail(), s.getFirstName(), s.getLastName())).toList());
    }

    @Override
    public ResponseEntity<List<UserInfoDTO>> getAffSkydivers() {
        return ResponseEntity.ok(skydiverRepository.findAll().stream().filter(skydiver -> skydiver.getLicence().equals("AFF"))
                .map(skydiver -> new UserInfoDTO(
                        skydiver.getEmail(),
                        skydiver.getFirstName(),
                        skydiver.getLastName()
                )).toList());
    }

    @Override
    public ResponseEntity<List<UserInfoDTO>> getApprovers() {
            return ResponseEntity.ok(skydiverRepository.findApprovers().stream()
                    .map(skydiver -> new UserInfoDTO(

                                    skydiver.getEmail(),
                                    skydiver.getFirstName(),
                                    skydiver.getLastName()
                            )
                    ).toList());
    }
}
