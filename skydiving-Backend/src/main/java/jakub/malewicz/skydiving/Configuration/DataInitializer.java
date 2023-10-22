package jakub.malewicz.skydiving.Configuration;

import jakub.malewicz.skydiving.Models.*;
import jakub.malewicz.skydiving.Models.enums.JumpType;
import jakub.malewicz.skydiving.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final SkydiverRepository skydiverRepository;
    private final PlaneRepository planeRepository;
    private final DepartureRepository departureRepository;
    private final DepartureUserRepository departureUserRepository;

    @Override
    public void run(String... args) {
        createCustomer("John", "Doe", "johndoe@ex.com", "000000000", "111111111", 76.0, true);
        createRole("ADMIN");
        createRole("MANIFEST");
        createRole("INSTRUCTOR");
        createRole("TANDEM_PILOT");
        Role userRole = createRole("USER");
        Skydiver skydiver = createSkydiver("John", "Doe", "johndoe@ex.com", "000000000", "111111111", 76.0, "password", "Student", userRole);
        Plane SPWAW = createPlane("SP-WAW", 1000);
        Departure departure = createDeparture(new Date(), true, true, SPWAW);
        addUserToDeparture(JumpType.STUDENT, null, skydiver, departure);

    }

    private DepartureUser addUserToDeparture(JumpType jumpType, Customer customer, Skydiver skydiver, Departure departure){
        return departureUserRepository.save(new DepartureUser(jumpType, customer, skydiver, departure));
    }

    private Departure createDeparture(Date date, boolean allowStudents, boolean allowAFF, Plane plane) {
        return departureRepository.save(new Departure(date, allowStudents,allowAFF, plane));
    }

    private Customer createCustomer(
            String firstName,
            String lastName,
            String email,
            String phone,
            String emergencyPhone,
            double weight,
            boolean signedForm
    ){
        return customerRepository.save(new Customer(
                firstName,
                lastName,
                email,
                phone,
                emergencyPhone,
                weight,
                signedForm

        ));
    }

    private Role createRole(String name){
        return roleRepository.save(new Role(name));
    }

    private Skydiver createSkydiver(String firstName, String lastName, String email, String phone, String emergencyPhone, double weight, String password, String licence, Role role){
        return skydiverRepository.save(new Skydiver(
                firstName,
                lastName,
                email,
                phone,
                emergencyPhone,
                weight,
                password,
                licence,
                role
        ));
    }

    private Plane createPlane(String name, double maxWeight){
        return planeRepository.save(new Plane(name, maxWeight));
    }
}
