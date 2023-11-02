package jakub.malewicz.skydiving.Configuration;

import jakub.malewicz.skydiving.DTOs.RegisterDTO;
import jakub.malewicz.skydiving.Models.*;
import jakub.malewicz.skydiving.Models.enums.JumpType;
import jakub.malewicz.skydiving.Repositories.*;
import jakub.malewicz.skydiving.Services.AuthenticationService;
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
    private final AuthenticationService authenticationService;

    @Override
    public void run(String... args) {
        createCustomer("John", "Doe", "johndoe@ex.com", "000000000", "111111111", 76.0, true);
        Role adminRole = createRole("ADMIN");
        Role manifest =createRole("MANIFEST");
        Role instructor = createRole("INSTRUCTOR");
        Role tandem = createRole("TANDEM_PILOT");
        Role userRole = createRole("USER");
        Skydiver skydiver = createSkydiver("John", "Doe", "admin@ex.com", "000000000", "111111111", 76.5, "password", "Student",adminRole );
        Skydiver skydiver2 = createSkydiver("John", "Doe", "manifest@ex.com", "000000000", "111111111", 76.5, "password", "Student",manifest );
        Skydiver skydiver3 =createSkydiver("John", "Doe", "instructor@ex.com", "000000000", "111111111", 76.5, "password", "Student",instructor );
        Skydiver skydiver4 =createSkydiver("John", "Doe", "tandem@ex.com", "000000000", "111111111", 76.5, "password", "Student",tandem );
        Skydiver skydiver5 =createSkydiver("John", "Doe", "user@ex.com", "000000000", "111111111", 76.5, "password", "Student",userRole );

        Plane SPWAW = createPlane("SP-WAW", 1000);
        createPlane("D-FIDI", 800);
        Departure departure = createDeparture( true, true, SPWAW);
        createDeparture( true, true, SPWAW);
        createDeparture( true, true, SPWAW);
        addUserToDeparture(JumpType.STUDENT, null, skydiver2, departure);
        addUserToDeparture(JumpType.STUDENT, null, skydiver3, departure);
        addUserToDeparture(JumpType.STUDENT, null, skydiver4, departure);
        addUserToDeparture(JumpType.STUDENT, null, skydiver, departure);

    }

    private DepartureUser addUserToDeparture(JumpType jumpType, Customer customer, Skydiver skydiver, Departure departure){
        return departureUserRepository.save(new DepartureUser(jumpType, customer, skydiver, departure));
    }

    private Departure createDeparture( boolean allowStudents, boolean allowAFF, Plane plane) {
        return departureRepository.save(new Departure("23-10-2023", "14:30", allowStudents,allowAFF, plane));
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
        authenticationService.register(new RegisterDTO(
                firstName,
                lastName,
                email,
                phone,
                emergencyPhone,
                weight,
                password,
                licence,
                role.getName()
        ));

        return skydiverRepository.findByEmail(email).get();
    }

    private Plane createPlane(String name, double maxWeight){
        return planeRepository.save(new Plane(name, maxWeight));
    }
}
