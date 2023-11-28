package jakub.malewicz.skydiving.Controllers;

import jakub.malewicz.skydiving.DTOs.*;
import jakub.malewicz.skydiving.Services.IDepartureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/departures")
@RequiredArgsConstructor
public class DepartureController {

    private final IDepartureService departureService;

    @GetMapping
    public ResponseEntity<List<DepartureDetailsDTO>> getDepartures(@RequestParam String date, @RequestParam int page){
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
    public ResponseEntity<DepartureDetailsDTO> deleteUserFromDeparture(@RequestParam String email, @RequestParam long departureId){
        return departureService.deleteUserFromDeparture(email, departureId);
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
