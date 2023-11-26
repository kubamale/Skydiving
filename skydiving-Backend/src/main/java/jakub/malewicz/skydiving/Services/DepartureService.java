package jakub.malewicz.skydiving.Services;

import jakub.malewicz.skydiving.DTOs.*;
import jakub.malewicz.skydiving.Exceptions.BadRequestException;
import jakub.malewicz.skydiving.Exceptions.ResourceNotFoundException;
import jakub.malewicz.skydiving.Mappers.DepartureMapper;
import jakub.malewicz.skydiving.Models.*;
import jakub.malewicz.skydiving.enums.JumpType;
import jakub.malewicz.skydiving.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DepartureService implements IDepartureService {

    private final DepartureRepository departureRepository;
    private final PlaneRepository planeRepository;
    private final DepartureUserRepository departureUserRepository;
    private final SkydiverRepository skydiverRepository;
    private final CustomerRepository customerRepository;
    private final DepartureMapper departureMapper;

    public ResponseEntity<List<DepartureDetailsDTO>> getDepartures(String date, int page) {

        List<Departure> departures = departureRepository.getDepartures(date, page);

        return ResponseEntity.ok(departures.stream().map(dep -> {
                    dep.fillData();
                    return departureMapper.mapToDepartureDetailsDTO(dep);
                }
        ).toList());
    }

    ///TODO: Create logic to block adding new departure with the same plane earlier the 30 min after previous flight
    public ResponseEntity<DepartureDetailsDTO> createDeparture(DepartureCreateDTO departure) {
        Optional<Plane> plane = planeRepository.findById(departure.planeId());

        if (plane.isEmpty()) {
            throw new BadRequestException("No plane with id " + departure.planeId());
        }

        Departure createdDeparture = departureRepository.save(new Departure(
                departure.date(),
                departure.time(),
                departure.allowStudents(),
                departure.allowAFF(),
                plane.get()
        ));


        return ResponseEntity.ok(departureMapper.mapToDepartureDetailsDTO(createdDeparture));
    }

    public ResponseEntity<String> deleteDeparture(long id) {

        Optional<Departure> departure = departureRepository.findById(id);

        if (departure.isEmpty()) {
            throw new ResourceNotFoundException("No departure with id: " + id);
        }

        departureRepository.delete(departure.get());

        return ResponseEntity.ok().build();

    }

    public ResponseEntity<DepartureDetailsDTO> updateDeparture(DepartureUpdateDTO departure) {
        Departure savedDeparture = departureRepository.findById(departure.id())
                .orElseThrow(() -> new BadRequestException("No departure with that id"));


        if (departure.allowAFF() && !departure.allowStudents()) {
            throw new BadRequestException("To make AFF jumps available you need to also make students jumps available");
        }

        if (departure.date() != null) {
            savedDeparture.setDate(departure.date());
        }

        if (departure.time() != null) {
            savedDeparture.setTime(departure.time());
        }

        if (departure.allowStudents() != savedDeparture.isAllowStudents()) {
            savedDeparture.setAllowStudents(departure.allowStudents());
            if (!savedDeparture.isAllowStudents()) {
                deleteAllStudentsFromDeparture(savedDeparture.getId());
                deleteAllAffSkydiversFromDeparture(savedDeparture.getId());
                savedDeparture.setAllowAFF(false);
            }
        }

        if (departure.allowAFF() != savedDeparture.isAllowAFF()) {
            savedDeparture.setAllowAFF(departure.allowAFF());
            if (!savedDeparture.isAllowAFF() || !savedDeparture.isAllowStudents()) {
                deleteAllAffSkydiversFromDeparture(savedDeparture.getId());
            }
        }

        ///TODO: Don't allow to change planes if its already used on that time
        if (departure.planeId() != savedDeparture.getPlane().getId()) {
            Plane plane = planeRepository.findById(departure.planeId())
                    .orElseThrow(() -> new BadRequestException("No plane with id: " + departure.planeId()));

            savedDeparture.setPlane(plane);
        }

        List<DepartureUser> departureUser = departureUserRepository.findByDepartureIdWhereInSkydiverId(departure.id(), departure.usersId());
        departureUserRepository.deleteAll(departureUser);
        Departure updatedDeparture = departureRepository.save(savedDeparture);

        updatedDeparture.fillData();
        return ResponseEntity.ok(departureMapper.mapToDepartureDetailsDTO(updatedDeparture));

    }

    ///TODO: Check if request was not sent less then 1 hour before flight if its sent by USER
    public ResponseEntity<DepartureDetailsDTO> deleteUserFromDeparture(String userEmail, long departureId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getName().equals(userEmail)){
            throw new BadRequestException("You can only cancel flights for yourself");
        }

        Skydiver skydiver = skydiverRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BadRequestException("No account with email " + userEmail));


        Departure departure = departureRepository.findById(departureId)
                .orElseThrow(() -> new BadRequestException("No departure with that Id"));

        DepartureUser departureUser = departureUserRepository.findByDepartureIdAndSkydiverId(departureId, skydiver.getId())
                .orElseThrow(() -> new BadRequestException("User is not assigned to that departure") );

        departure.getDepartureUsers().remove(departureUser);
        departureUserRepository.delete(departureUser);

        return ResponseEntity.ok(departureMapper.mapToDepartureDetailsDTO(departure));
    }

    public ResponseEntity<List<String>> getDeparturesDates(String startDate, String endDate) {
        return ResponseEntity.ok(departureRepository.getDates(startDate, endDate));
    }

    @Override
    public ResponseEntity<DepartureDetailsDTO> bookDeparture(BookDepartureDTO departureDTO) {
        double currentWeight;
        Skydiver user = skydiverRepository.findByEmail(departureDTO.skydiverEmail())
                .orElseThrow(() -> new BadRequestException("No user with email " + departureDTO.skydiverEmail() + "was found"));


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Skydiver caller = skydiverRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadRequestException("Please login"));

        if ((caller.getRole().getName().equals("USER") || caller.getRole().getName().equals("AWAITING_APPROVAL")) && (departureDTO.jumpType().equals("AFF") || departureDTO.jumpType().equals("TANDEM"))){
            throw new BadRequestException("You are not authorized to perform this action");
        }


        Departure departure = departureRepository.findById(departureDTO.departureId())
                .orElseThrow(() -> new BadRequestException("No departure with that id"));


        if (user.getLicence().equals("AFF") && caller.getRole().getName().equals("USER")) {
            throw new BadRequestException("You have to be at least student to book your own fights");
        }

        if (user.getLicence().equals("Student") && !departureDTO.jumpType().equals(JumpType.STUDENT.name()) && caller.getRole().getName().equals("USER")) {
            throw new BadRequestException("You can only book Student flights");
        }


        if ((departureDTO.jumpType().equals(JumpType.AFF.name()) && (!departure.isAllowAFF() || !departure.isAllowStudents())) || (departureDTO.jumpType().equals(JumpType.STUDENT.name()) && !departure.isAllowStudents())) {
            throw new BadRequestException("Cant add to this departure because AFF or Student is not allowed");
        }


        List<DepartureUser> existingDepUser = departureUserRepository.findByDepartureIdWhereInSkydiverId(departureDTO.departureId(), Arrays.asList(user.getId()));

        if (!existingDepUser.isEmpty()) {
            throw new BadRequestException("User is already assigned for that departure");
        }

        currentWeight = calculateCurrentWeight(departure);

        if (departure.getPlane().getMaxWeight() < currentWeight + user.getWeight()) {
            throw new BadRequestException("Can't book this departure weight is too high" + departure.getPlane().getMaxWeight() + user.getWeight());
        }


        switch (JumpType.valueOf(departureDTO.jumpType())) {
            case AFF -> {
                departureDTO.secondJumperEmail().orElseThrow(() -> new BadRequestException("To book aff jump you need to specify aff student"));
                Optional<Skydiver> aff = skydiverRepository.findByEmail(departureDTO.secondJumperEmail().get());
                if (aff.isEmpty()) {
                    throw new BadRequestException("No skydiver with that email");
                }
                existingDepUser = departureUserRepository.findByDepartureIdWhereInSkydiverId(departureDTO.departureId(), Arrays.asList(aff.get().getId()));
                if (!existingDepUser.isEmpty()) {
                    throw new BadRequestException("User is already assigned for that departure");
                }
                if (departure.getPlane().getMaxWeight() < currentWeight + user.getWeight() + aff.get().getWeight()) {
                    throw new BadRequestException("Can't book this departure weight is too high");
                }
                departure.getDepartureUsers().add(departureUserRepository.save(DepartureUser.createAFFJump(user,aff.get(), departure )));
            }
            case TANDEM -> {
                departureDTO.secondJumperEmail().orElseThrow(() -> new BadRequestException("To book tandem jump you need to specify customer"));
                Optional<Customer> customer = customerRepository.findByEmail(departureDTO.secondJumperEmail().get());

                if (customer.isEmpty()) {
                    throw new BadRequestException("No customer with that email");
                }
                if (departure.getPlane().getMaxWeight() < currentWeight + user.getWeight() + customer.get().getWeight()) {
                    throw new BadRequestException("Can't book this departure weight is too high");
                }

                departure.getDepartureUsers().add(departureUserRepository.save(DepartureUser.createTandemJump(user, customer.get(),  departure)));
            }
            default ->
                    departure.getDepartureUsers().add(departureUserRepository.save(DepartureUser.createNormalJump(user, departure, JumpType.valueOf(departureDTO.jumpType()))));
        }

        departure.fillData();
        return ResponseEntity.ok(departureMapper.mapToDepartureDetailsDTO(departure));
    }

    private void deleteAllStudentsFromDeparture(long departureId) {
        List<DepartureUser> students = departureUserRepository.findByDepartureIdAndJumpType(departureId, JumpType.STUDENT);
        departureUserRepository.deleteAll(students);
    }

    private void deleteAllAffSkydiversFromDeparture(long departureId) {
        List<DepartureUser> AFF = departureUserRepository.findByDepartureIdAndJumpType(departureId, JumpType.AFF);
        departureUserRepository.deleteAll(AFF);
    }

    private double calculateCurrentWeight(Departure departure) {
        return departure.getDepartureUsers().stream().mapToDouble(dep -> {
            if (dep.getCustomer() != null) {
                return dep.getSkydiver().getWeight() + dep.getCustomer().getWeight();
            } else {
                return dep.getSkydiver().getWeight();
            }
        }).sum();
    }
}
