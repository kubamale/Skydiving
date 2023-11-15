package jakub.malewicz.skydiving.Services;

import jakub.malewicz.skydiving.DTOs.*;
import jakub.malewicz.skydiving.Exceptions.BadRequestException;
import jakub.malewicz.skydiving.Exceptions.ResourceNotFoundException;
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
public class DepartureService implements IDepartureService{

    private final DepartureRepository departureRepository;
    private final PlaneRepository planeRepository;
    private final DepartureUserRepository departureUserRepository;
    private final SkydiverRepository skydiverRepository;
    private final CustomerRepository customerRepository;

    public ResponseEntity<List<DepartureDetailsDTO>> getDepartures(String date, int page){

        List<Departure> departures = departureRepository.getDepartures(date, page);

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
    public ResponseEntity<DepartureDetailsDTO> createDeparture(DepartureCreateDTO departure) {
        Optional<Plane> plane = planeRepository.findById(departure.planeId());

        if (plane.isEmpty()){
            throw new BadRequestException("No plane with id " + departure.planeId());
        }

        Departure createdDeparture = departureRepository.save(new Departure(
                departure.date(),
                departure.time(),
                departure.allowStudents(),
                departure.allowAFF(),
                plane.get()
        ));


        return ResponseEntity.ok(Mappers.mapToDTO(createdDeparture, departureUserRepository.getByDepartureId(createdDeparture.getId())));
    }

    public ResponseEntity<String> deleteDeparture(long id) {

        Optional<Departure> departure = departureRepository.findById(id);

        if (departure.isEmpty()){
            throw new ResourceNotFoundException("No departure with id: " + id);
        }

        departureRepository.delete(departure.get());

        return ResponseEntity.ok().build();

    }

    public ResponseEntity<DepartureDetailsDTO> updateDeparture(DepartureUpdateDTO departure) {
        Optional<Departure> savedDeparture = departureRepository.findById(departure.id());

        if (savedDeparture.isEmpty()){
            throw new BadRequestException("No departure with that id");
        }

        if (departure.allowAFF() && !departure.allowStudents()){
            throw new BadRequestException("To make AFF jumps available you need to also make students jumps available");
        }

        if (departure.date() != null){
            savedDeparture.get().setDate(departure.date());
        }

        if (departure.time() != null){
            savedDeparture.get().setTime(departure.time());
        }

        if (departure.allowStudents() != savedDeparture.get().isAllowStudents()){
            savedDeparture.get().setAllowStudents(departure.allowStudents());
            if (!savedDeparture.get().isAllowStudents()){
                deleteAllStudentsFromDeparture(savedDeparture.get().getId());
                deleteAllAffSkydiversFromDeparture(savedDeparture.get().getId());
                savedDeparture.get().setAllowAFF(false);
            }
        }

        if (departure.allowAFF() != savedDeparture.get().isAllowAFF()){
            savedDeparture.get().setAllowAFF(departure.allowAFF());
            if (!savedDeparture.get().isAllowAFF() || !savedDeparture.get().isAllowStudents()){
                deleteAllAffSkydiversFromDeparture(savedDeparture.get().getId());
            }
        }

        ///TODO: Don't allow to change planes if its already used on that time
        if (departure.planeId() != savedDeparture.get().getPlane().getId()){
            Optional<Plane> plane = planeRepository.findById(departure.planeId());
            if (plane.isEmpty()){
                throw new BadRequestException("No plane with id: " + departure.planeId());
            }

            savedDeparture.get().setPlane(plane.get());
        }

        Departure updatedDeparture = departureRepository.save(savedDeparture.get());

        List<DepartureUser> departureUser = departureUserRepository.findByDepartureIdWhereInSkydiverId(departure.id(),  departure.usersId());
        departureUserRepository.deleteAll(departureUser);
        departureUser = departureUserRepository.getByDepartureId(departure.id());
        return ResponseEntity.ok(Mappers.mapToDTO(updatedDeparture, departureUser));

    }

    ///TODO: Check if request was not sent less then 1 hour before flight if its sent by USER
    public ResponseEntity<DepartureDetailsDTO> deleteUserFromDeparture(DeleteUsersDTO userId, long departureId) {

        List<DepartureUser> departureUser = departureUserRepository.findByDepartureIdWhereInSkydiverId(departureId, userId.ids());

        if (departureUser.isEmpty()){
            throw new BadRequestException("This departure with that user does not exist!");
        }

        departureUserRepository.deleteAll(departureUser);
        List<DepartureUser> departureUserList = departureUserRepository.getByDepartureId(departureId);
        Optional<Departure> myDeparture = departureRepository.findById(departureId);

        return myDeparture.map(departure -> ResponseEntity.ok(Mappers.mapToDTO(departure, departureUserList))).orElseThrow(() -> new BadRequestException("No departure"));

    }

    public ResponseEntity<List<String>> getDeparturesDates(String startDate, String endDate) {
        return ResponseEntity.ok(departureRepository.getDates(startDate, endDate));
    }

    @Override
    public ResponseEntity<DepartureDetailsDTO> bookDeparture(BookDepartureDTO departureDTO) {
        double currentWeight;
        Optional<Skydiver> oUser = skydiverRepository.findByEmail(departureDTO.skydiverEmail());


        if (oUser.isEmpty()){
            throw new BadRequestException("No user with email " + departureDTO.skydiverEmail() + "was found");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Skydiver> caller = skydiverRepository.findByEmail(authentication.getName());

        if (caller.isEmpty()){
            throw new BadRequestException("Please login");
        }
        Optional<Departure> oDeparture = departureRepository.findById(departureDTO.departureId());

        if (oDeparture.isEmpty()){
            throw new BadRequestException("No departure with that id");
        }



            if (oUser.get().getLicence().equals("AFF")){
                throw new BadRequestException("You have to be at least student to book your own fights");
            }

            if (oUser.get().getLicence().equals("Student") && !departureDTO.jumpType().equals(JumpType.STUDENT.name())){
                throw new BadRequestException("You can only book Student flights");
            }
        

        if ((departureDTO.jumpType().equals(JumpType.AFF.name()) && !oDeparture.get().isAllowAFF()) || (departureDTO.jumpType().equals(JumpType.STUDENT.name()) && !oDeparture.get().isAllowStudents())){
            throw new BadRequestException("Cant add to this departure because AFF or Student is not allowed");
        }





        List<DepartureUser> existingDepUser = departureUserRepository.findByDepartureIdWhereInSkydiverId(departureDTO.departureId(), Arrays.asList(oUser.get().getId()));

        if (!existingDepUser.isEmpty()){
            throw new BadRequestException("User is already assigned for that departure");
        }

        currentWeight = calculateCurrentWeight(oDeparture.get());

        if (oDeparture.get().getPlane().getMaxWeight() < currentWeight+ oUser.get().getWeight()){
            throw new BadRequestException("Can't book this departure weight is too high" +oDeparture.get().getPlane().getMaxWeight() + oUser.get().getWeight() );
        }


        switch (JumpType.valueOf(departureDTO.jumpType())){
            case AFF -> {
                departureDTO.secondJumperEmail().orElseThrow(() ->new BadRequestException("To book aff jump you need to specify aff student"));
                Optional<Skydiver> aff = skydiverRepository.findByEmail(departureDTO.secondJumperEmail().get());
                if (aff.isEmpty()){
                    throw new BadRequestException("No skydiver with that email");
                }
                existingDepUser = departureUserRepository.findByDepartureIdWhereInSkydiverId(departureDTO.departureId(), Arrays.asList(aff.get().getId()));
                if (!existingDepUser.isEmpty()){
                    throw new BadRequestException("User is already assigned for that departure");
                }
                if (oDeparture.get().getPlane().getMaxWeight() < currentWeight + oUser.get().getWeight() + aff.get().getWeight()){
                    throw new BadRequestException("Can't book this departure weight is too high");
                }
                departureUserRepository.save(new DepartureUser(JumpType.AFF, oUser.get(), oDeparture.get()));
                departureUserRepository.save(new DepartureUser(JumpType.AFF, aff.get(), oDeparture.get()));

            }
            case TANDEM -> {
                departureDTO.secondJumperEmail().orElseThrow(() ->new BadRequestException("To book tandem jump you need to specify customer"));
                Optional<Customer> customer = customerRepository.findByEmail(departureDTO.secondJumperEmail().get());

                if (customer.isEmpty()){
                    throw new BadRequestException("No customer with that email");
                }
                if (oDeparture.get().getPlane().getMaxWeight() < currentWeight + oUser.get().getWeight() + customer.get().getWeight()){
                    throw new BadRequestException("Can't book this departure weight is too high");
                }

                departureUserRepository.save(new DepartureUser(JumpType.TANDEM, customer.get(), oUser.get(), oDeparture.get()));

            }
            default ->
                departureUserRepository.save(new DepartureUser(JumpType.valueOf(departureDTO.jumpType()), oUser.get(), oDeparture.get()));


        }
        return ResponseEntity.ok(Mappers.mapToDTO(oDeparture.get(), departureUserRepository.getByDepartureId(oDeparture.get().getId())));
    }

    private void deleteAllStudentsFromDeparture(long departureId){
        List<DepartureUser> students = departureUserRepository.findByDepartureIdAndJumpType( departureId, JumpType.STUDENT);
        departureUserRepository.deleteAll(students);
    }

    private void deleteAllAffSkydiversFromDeparture(long departureId){
        List<DepartureUser> AFF = departureUserRepository.findByDepartureIdAndJumpType( departureId, JumpType.AFF);
        departureUserRepository.deleteAll(AFF);
    }

    private double calculateCurrentWeight(Departure departure){
        return departure.getDepartureUsers().stream().mapToDouble(dep -> {
                if (dep.getCustomer() != null){
                    return dep.getSkydiver().getWeight() + dep.getCustomer().getWeight();
                }
                else {
                    return dep.getSkydiver().getWeight();
                }
            }).sum();
    }
}
