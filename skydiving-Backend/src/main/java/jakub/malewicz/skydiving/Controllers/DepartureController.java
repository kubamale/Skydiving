package jakub.malewicz.skydiving.Controllers;

import jakub.malewicz.skydiving.DTOs.*;
import jakub.malewicz.skydiving.Models.Departure;
import jakub.malewicz.skydiving.Models.Plane;
import jakub.malewicz.skydiving.Repositories.PlaneRepository;
import jakub.malewicz.skydiving.Services.DepartureService;
import jakub.malewicz.skydiving.Services.IDepartureService;
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

    private final IDepartureService departureService;
    private final PlaneRepository planeRepository;

    @GetMapping
    public ResponseEntity<List<DepartureDetailsDTO>> getDepartures(@RequestParam String date, @RequestParam int page) throws ParseException {
            return departureService.getDepartures(date, page);
    }

    @PostMapping
    public ResponseEntity<DepartureDetailsDTO> createDeparture(@RequestBody DepartureCreateDTO departure){
        return departureService.createDeparture(departure);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDeparture(@RequestParam long id){
        return departureService.deleteDeparture(id);
    }

    @PutMapping
    public  ResponseEntity<DepartureDetailsDTO> updateDeparture(@RequestBody DepartureUpdateDTO departure){
        return departureService.updateDeparture(departure);
    }

    @PostMapping("/deleteUserFromDeparture")
    public ResponseEntity<DepartureDetailsDTO> deleteUserFromDeparture(@RequestBody DeleteUsersDTO userId, @RequestParam long departureId){
        return departureService.deleteUserFromDeparture(userId, departureId);
    }

    @GetMapping("/dates")
    public ResponseEntity<List<String>> getDeparturesDates(@RequestParam String startDate, @RequestParam String endDate){
        return departureService.getDeparturesDates(startDate, endDate);
    }

    @PostMapping("/book")
    public ResponseEntity<DepartureDetailsDTO> bookDeparture(@RequestBody BookDepartureDTO departureDTO){
        return departureService.bookDeparture(departureDTO);
    }

}
