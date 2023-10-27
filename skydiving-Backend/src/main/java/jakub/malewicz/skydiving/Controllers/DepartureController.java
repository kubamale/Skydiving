package jakub.malewicz.skydiving.Controllers;

import jakub.malewicz.skydiving.DTOs.DepartureCreateDTO;
import jakub.malewicz.skydiving.DTOs.DepartureDTO;
import jakub.malewicz.skydiving.DTOs.DepartureDetailsDTO;
import jakub.malewicz.skydiving.Models.Departure;
import jakub.malewicz.skydiving.Models.Plane;
import jakub.malewicz.skydiving.Repositories.PlaneRepository;
import jakub.malewicz.skydiving.Services.DepartureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/departures")
@RequiredArgsConstructor
public class DepartureController {

    private final DepartureService departureService;
    private final PlaneRepository planeRepository;

    @GetMapping
    public ResponseEntity<List<DepartureDetailsDTO>> getDepartures(@RequestParam String date) throws ParseException {
            return departureService.getDepartures(date);
    }

    @PostMapping
    public ResponseEntity<DepartureDTO> createDeparture(@RequestBody DepartureCreateDTO departure){
        return departureService.createDeparture(departure);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDeparture(@RequestParam long id){
        return departureService.deleteDeparture(id);
    }

    @PutMapping
    public  ResponseEntity<DepartureDTO> updateDeparture(@RequestBody DepartureCreateDTO departure, @RequestParam long id){
        return departureService.updateDeparture(departure, id);
    }

    @DeleteMapping("/deleteUserFromDeparture")
    public ResponseEntity<DepartureDetailsDTO> deleteUserFromDeparture(@RequestParam long userId, @RequestParam long departureId){
        return departureService.deleteUserFromDeparture(userId, departureId);
    }

    @GetMapping("/dates")
    public ResponseEntity<List<String>> getDeparturesDates(@RequestParam String startDate, @RequestParam String endDate){
        return departureService.getDeparturesDates(startDate, endDate);
    }

}
