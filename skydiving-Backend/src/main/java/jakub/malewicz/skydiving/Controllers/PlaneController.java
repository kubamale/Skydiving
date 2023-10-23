package jakub.malewicz.skydiving.Controllers;

import jakub.malewicz.skydiving.DTOs.PlaneDTO;
import jakub.malewicz.skydiving.Models.Plane;
import jakub.malewicz.skydiving.Repositories.PlaneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plane")
public class PlaneController {

    private final PlaneRepository planeRepository;

}
