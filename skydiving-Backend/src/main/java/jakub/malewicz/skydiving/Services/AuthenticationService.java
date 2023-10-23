package jakub.malewicz.skydiving.Services;

import jakub.malewicz.skydiving.Configuration.JwtService;
import jakub.malewicz.skydiving.DTOs.LoginDTO;
import jakub.malewicz.skydiving.DTOs.LoginResponseDTO;
import jakub.malewicz.skydiving.DTOs.RegisterDTO;
import jakub.malewicz.skydiving.Models.Role;
import jakub.malewicz.skydiving.Models.Skydiver;
import jakub.malewicz.skydiving.Repositories.RoleRepository;
import jakub.malewicz.skydiving.Repositories.SkydiverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final SkydiverRepository skydiverRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public ResponseEntity<LoginResponseDTO> login(LoginDTO credentialsDTO) throws RuntimeException {
        Skydiver user = skydiverRepository.findByEmail(credentialsDTO.email())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "no user with that email"));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDTO.password()), user.getPassword())){
            return ResponseEntity.ok(new LoginResponseDTO(user.getRole().getName(), jwtService.generateToken(user)));

        }
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "wrong password");
    }

    public ResponseEntity<LoginResponseDTO> register(RegisterDTO registerDTO){
        Optional<Skydiver> oSkydiver = skydiverRepository.findByEmail(registerDTO.email());
        if(oSkydiver.isPresent()){
            throw  new HttpClientErrorException(HttpStatus.BAD_REQUEST, "user with that email already exists");
        }

        Optional<Role> role = roleRepository.findByName(registerDTO.role());

        if (role.isEmpty()){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "no role was found");
        }

        Skydiver skydiver = new Skydiver(
                registerDTO.firstName(),
                registerDTO.lastName(),
                registerDTO.email(),
                registerDTO.phone(),
                registerDTO.emergencyPhone(),
                registerDTO.weight(),
                passwordEncoder.encode(CharBuffer.wrap(registerDTO.password())),
                registerDTO.licence(),
                role.get()
        );

        skydiverRepository.save(skydiver);

        return ResponseEntity.ok(new LoginResponseDTO(skydiver.getRole().getName(), jwtService.generateToken(skydiver)));
    }
}
