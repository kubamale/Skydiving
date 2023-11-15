package jakub.malewicz.skydiving.Controllers;

import jakub.malewicz.skydiving.DTOs.UserInfoDTO;
import jakub.malewicz.skydiving.Services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/instructors")
    public ResponseEntity<List<UserInfoDTO>> getInstructors(){
        return userService.getInstructors();
    }

    @GetMapping("/tandempilots")
    public ResponseEntity<List<UserInfoDTO>> getTandemPilots(){
        return userService.getTandemPilots();
    }

    @GetMapping("/customers")
    public ResponseEntity<List<UserInfoDTO>> getCustomers(){
        return userService.getCustomers();
    }

    @GetMapping("/skydivers")
    public ResponseEntity<List<UserInfoDTO>> getSkydivers(){
        return userService.getSkydivers();
    }

    @GetMapping("/affSkydivers")
    public ResponseEntity<List<UserInfoDTO>> getAffSkydivers(){
        return userService.getAffSkydivers();
    }

    @GetMapping("/approvers")
    public ResponseEntity<List<UserInfoDTO>> getApprovers() {return userService.getApprovers();}

}
