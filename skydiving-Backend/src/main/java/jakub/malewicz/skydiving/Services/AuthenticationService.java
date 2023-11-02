package jakub.malewicz.skydiving.Services;

import jakub.malewicz.skydiving.Configuration.JwtService;
import jakub.malewicz.skydiving.DTOs.LoginDTO;
import jakub.malewicz.skydiving.DTOs.CredentialsDTO;
import jakub.malewicz.skydiving.DTOs.RegisterDTO;
import jakub.malewicz.skydiving.Exceptions.BadRequestException;
import jakub.malewicz.skydiving.Exceptions.ResourceNotFoundException;
import jakub.malewicz.skydiving.Models.Role;
import jakub.malewicz.skydiving.Models.Skydiver;
import jakub.malewicz.skydiving.Repositories.RoleRepository;
import jakub.malewicz.skydiving.Repositories.SkydiverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService{

    private final SkydiverRepository skydiverRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public ResponseEntity<CredentialsDTO> login(LoginDTO credentialsDTO) throws RuntimeException {
        Skydiver user = skydiverRepository.findByEmail(credentialsDTO.email())
                .orElseThrow(() -> new ResourceNotFoundException("No user with that username!"));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDTO.password()), user.getPassword())){
            return ResponseEntity.ok(new CredentialsDTO(user.getRole().getName(), jwtService.generateToken(user), user.getEmail()));

        }
        throw new BadRequestException("Wrong password!");
    }

    public ResponseEntity<CredentialsDTO> register(RegisterDTO registerDTO){
        Optional<Skydiver> oSkydiver = skydiverRepository.findByEmail(registerDTO.email());
        if(oSkydiver.isPresent()){
            throw  new BadRequestException("User with that email already exists");
        }

        Optional<Role> role = roleRepository.findByName(registerDTO.role());

        if (role.isEmpty()){
            throw new BadRequestException("No role was found");
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

        return ResponseEntity.ok(new CredentialsDTO(skydiver.getRole().getName(), jwtService.generateToken(skydiver), skydiver.getEmail()));
    }
}
