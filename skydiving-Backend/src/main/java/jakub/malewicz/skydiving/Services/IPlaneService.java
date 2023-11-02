package jakub.malewicz.skydiving.Services;

import jakub.malewicz.skydiving.DTOs.PlaneDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPlaneService {
    ResponseEntity<PlaneDTO> getPlane(String name);
    ResponseEntity<List<PlaneDTO>> getAllPlanes();
    ResponseEntity<PlaneDTO> createPlane(PlaneDTO planeDTO);
}
