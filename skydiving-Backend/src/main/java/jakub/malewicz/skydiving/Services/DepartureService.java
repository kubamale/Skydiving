package jakub.malewicz.skydiving.Services;

import jakub.malewicz.skydiving.DTOs.DepartureCreateDTO;
import jakub.malewicz.skydiving.DTOs.DepartureDTO;
import jakub.malewicz.skydiving.DTOs.PlaneDTO;
import jakub.malewicz.skydiving.Models.Departure;
import jakub.malewicz.skydiving.Models.Plane;
import jakub.malewicz.skydiving.Repositories.DepartureRepository;
import jakub.malewicz.skydiving.Repositories.PlaneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartureService {

    private final DepartureRepository departureRepository;
    private final PlaneRepository planeRepository;

    public ResponseEntity<List<DepartureDTO>> getDepartures(String date) throws ParseException {


        return ResponseEntity.ok(departureRepository.getDepartures(date).stream().map(d -> new DepartureDTO(d.getId(),d.getDate(), d.getTime(), d.isAllowStudents(), d.isAllowAFF(),
                new PlaneDTO(d.getPlane().getName(), d.getPlane().getMaxWeight()))).toList());
    }

    ///TODO: Create logic to block adding new departure with the same plane earlier the 30 min after previous flight
    public ResponseEntity<DepartureDTO> createDeparture(DepartureCreateDTO departure) {
        Optional<Plane> plane = planeRepository.findById(departure.planeId());

        if (plane.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Departure createdDeparture = departureRepository.save(new Departure(
                departure.date(),
                departure.time(),
                departure.allowStudents(),
                departure.allowAFF(),
                plane.get()
        ));

        return ResponseEntity.ok(new DepartureDTO(
                createdDeparture.getId(),
                createdDeparture.getDate(),
                createdDeparture.getTime(),
                createdDeparture.isAllowStudents(),
                createdDeparture.isAllowAFF(),
                new PlaneDTO(
                        plane.get().getName(),
                        plane.get().getMaxWeight()
                )
        ));
    }

    public ResponseEntity<String> deleteDeparture(long id) {

        Optional<Departure> departure = departureRepository.findById(id);

        if (departure.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        departureRepository.delete(departure.get());

        return ResponseEntity.ok("deleted");

    }

    public ResponseEntity<DepartureDTO> updateDeparture(DepartureCreateDTO departure, long id) {
        Optional<Departure> savedDeparture = departureRepository.findById(id);

        if (savedDeparture.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        if (departure.date() != null){
            savedDeparture.get().setDate(departure.date());
        }

        if (departure.time() != null){
            savedDeparture.get().setTime(departure.time());
        }

        if (departure.allowStudents() != savedDeparture.get().isAllowStudents()){
            savedDeparture.get().setAllowStudents(departure.allowStudents());
        }

        if (departure.allowAFF() != savedDeparture.get().isAllowAFF()){
            savedDeparture.get().setAllowAFF(departure.allowAFF());
        }

        ///TODO: Don't allow to change planes if its already used on that time
        if (departure.planeId() != savedDeparture.get().getPlane().getId()){
            Optional<Plane> plane = planeRepository.findById(departure.planeId());
            if (plane.isEmpty()){
                return ResponseEntity.badRequest().build();
            }

            savedDeparture.get().setPlane(plane.get());
        }

        Departure updatedDeparture = departureRepository.save(savedDeparture.get());

        return ResponseEntity.ok(new DepartureDTO(
                updatedDeparture.getId(),
                updatedDeparture.getDate(),
                updatedDeparture.getTime(),
                updatedDeparture.isAllowStudents(),
                updatedDeparture.isAllowAFF(),
                new PlaneDTO(
                        savedDeparture.get().getPlane().getName(),
                        savedDeparture.get().getPlane().getMaxWeight()
                )
        ));

    }
}
