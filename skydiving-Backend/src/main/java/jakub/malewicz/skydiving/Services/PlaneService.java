package jakub.malewicz.skydiving.Services;

import jakub.malewicz.skydiving.DTOs.PlaneDTO;
import jakub.malewicz.skydiving.Exceptions.BadRequestException;
import jakub.malewicz.skydiving.Exceptions.ResourceNotFoundException;
import jakub.malewicz.skydiving.Models.Plane;
import jakub.malewicz.skydiving.Repositories.PlaneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaneService implements IPlaneService{

    private final PlaneRepository planeRepository;

    public ResponseEntity<PlaneDTO> getPlane(String name) {
        Optional<Plane> plane = planeRepository.findByName(name);

        return plane.map(value -> ResponseEntity.ok(Mappers.mapToDTO(value))).orElseThrow(() -> new ResourceNotFoundException("No plane with tha name"));

    }

    public ResponseEntity<List<PlaneDTO>> getAllPlanes() {

        return ResponseEntity.ok(planeRepository.findAll().stream().map(Mappers::mapToDTO).toList());

    }

    public ResponseEntity<PlaneDTO> createPlane(PlaneDTO planeDTO) {
        Optional<Plane> oPlane = planeRepository.findByName(planeDTO.name());
        if (oPlane.isPresent()){
            throw new BadRequestException("Plane with that name already exists");
        }

        Plane plane = planeRepository.save(new Plane(planeDTO.name(), planeDTO.maxWeight()));
        return ResponseEntity.ok(Mappers.mapToDTO(plane));

    }
}
