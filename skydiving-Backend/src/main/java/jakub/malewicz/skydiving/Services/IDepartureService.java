package jakub.malewicz.skydiving.Services;

import jakub.malewicz.skydiving.DTOs.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDepartureService {
    ResponseEntity<List<DepartureDetailsDTO>> getDepartures(String date, int page);
    ResponseEntity<DepartureDetailsDTO> createDeparture(DepartureCreateDTO departure);
    ResponseEntity<String> deleteDeparture(long id);
    ResponseEntity<DepartureDetailsDTO> updateDeparture(DepartureUpdateDTO departure);
    ResponseEntity<DepartureDetailsDTO> deleteUserFromDeparture(DeleteUsersDTO userId, long departureId);
    ResponseEntity<List<String>> getDeparturesDates(String startDate, String endDate);
    ResponseEntity<DepartureDetailsDTO> bookDeparture(BookDepartureDTO departureDTO);
}
