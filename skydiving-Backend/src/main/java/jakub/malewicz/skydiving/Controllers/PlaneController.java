package jakub.malewicz.skydiving.Controllers;

import jakub.malewicz.skydiving.DTOs.PlaneDTO;
import jakub.malewicz.skydiving.Models.Plane;
import jakub.malewicz.skydiving.Repositories.PlaneRepository;
import jakub.malewicz.skydiving.Services.PlaneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plane")
public class PlaneController {

    private final PlaneService planeService;

    @GetMapping
    public ResponseEntity<PlaneDTO> getPlane(@RequestParam String name){
        return planeService.getPlane(name);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlaneDTO>> getAllPlanes(){
        return planeService.getAllPlanes();
    }

    @PostMapping
    public ResponseEntity<PlaneDTO> createPlane(@RequestBody PlaneDTO planeDTO){
        return planeService.createPlane(planeDTO);
    }
}
