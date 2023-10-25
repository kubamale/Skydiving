package jakub.malewicz.skydiving.Services;

import jakub.malewicz.skydiving.DTOs.DepartureCreateDTO;
import jakub.malewicz.skydiving.DTOs.DepartureDTO;
import jakub.malewicz.skydiving.DTOs.DepartureDetailsDTO;
import jakub.malewicz.skydiving.Models.Departure;
import jakub.malewicz.skydiving.Models.DepartureUser;
import jakub.malewicz.skydiving.Models.Plane;
import jakub.malewicz.skydiving.Repositories.DepartureRepository;
import jakub.malewicz.skydiving.Repositories.DepartureUserRepository;
import jakub.malewicz.skydiving.Repositories.PlaneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DepartureService {

    private final DepartureRepository departureRepository;
    private final PlaneRepository planeRepository;
    private final DepartureUserRepository departureUserRepository;

    public ResponseEntity<List<DepartureDetailsDTO>> getDepartures(String date) throws ParseException {

        List<Departure> departures = departureRepository.getDepartures(date);

        Map<Departure,List<DepartureUser>> departureSkydivers = new HashMap<>();

        for (Departure departure : departures){
            if (!departureSkydivers.containsKey(departure)){
                departureSkydivers.put(departure,new ArrayList<>());
            }

            List<DepartureUser> departureUser = departureUserRepository.getByDepartureId(departure.getId());

            departureSkydivers.put(departure,departureUser);
        }

        List<DepartureDetailsDTO> result = new ArrayList<>();
        departureSkydivers.forEach((departure, departureUserList) -> result.add(Mappers.mapToDTO(departure, departureUserList)));

        return ResponseEntity.ok(result);
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

        return ResponseEntity.ok(Mappers.mapToDTO(createdDeparture));
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

        return ResponseEntity.ok(Mappers.mapToDTO(updatedDeparture));

    }

    ///TODO: Check if request was not sent less then 1 hour before flight if its sent by USER
    public ResponseEntity<DepartureDetailsDTO> deleteUserFromDeparture(long userId, long departureId) {

        Optional<DepartureUser> departureUser = departureUserRepository.findByDepartureIdAndSkydiverId(userId, departureId);

        if (departureUser.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        departureUserRepository.delete(departureUser.get());
        List<DepartureUser> departureUserList = departureUserRepository.getByDepartureId(departureId);
        Optional<Departure> myDeparture = departureRepository.findById(departureId);

        return myDeparture.map(departure -> ResponseEntity.ok(Mappers.mapToDTO(departure, departureUserList))).orElseGet(() -> ResponseEntity.badRequest().build());

    }
}
