package jakub.malewicz.skydiving.Services;

import jakub.malewicz.skydiving.DTOs.UserInfoDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {

    ResponseEntity<List<UserInfoDTO>> getInstructors();
    ResponseEntity<List<UserInfoDTO>> getTandemPilots();
    ResponseEntity<List<UserInfoDTO>> getCustomers();
    ResponseEntity<List<UserInfoDTO>> getSkydivers();

    ResponseEntity<List<UserInfoDTO>> getAffSkydivers();
}
