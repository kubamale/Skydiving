package jakub.malewicz.skydiving.Controllers;

import jakub.malewicz.skydiving.DTOs.LoginDTO;
import jakub.malewicz.skydiving.DTOs.CredentialsDTO;
import jakub.malewicz.skydiving.DTOs.RegisterDTO;
import jakub.malewicz.skydiving.Services.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<CredentialsDTO> login(@RequestBody LoginDTO loginData){
        return authenticationService.login(loginData);
    }

    @PostMapping("/register")
    public ResponseEntity<CredentialsDTO> register(@RequestBody RegisterDTO registerDTO){
        return authenticationService.register(registerDTO);
    }
}
