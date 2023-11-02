package jakub.malewicz.skydiving.Services;

import jakub.malewicz.skydiving.DTOs.CredentialsDTO;
import jakub.malewicz.skydiving.DTOs.LoginDTO;
import jakub.malewicz.skydiving.DTOs.RegisterDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthenticationService {
    ResponseEntity<CredentialsDTO> login(LoginDTO credentialsDTO);
    ResponseEntity<CredentialsDTO> register(RegisterDTO registerDTO);
}
