package jakub.malewicz.skydiving.Configuration;

import jakub.malewicz.skydiving.Models.*;
import jakub.malewicz.skydiving.enums.JumpType;
import jakub.malewicz.skydiving.Repositories.*;
import jakub.malewicz.skydiving.enums.Privilege;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.CharBuffer;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final SkydiverRepository skydiverRepository;
    private final PlaneRepository planeRepository;
    private final DepartureRepository departureRepository;
    private final DepartureUserRepository departureUserRepository;

    @Override
    public void run(String... args) {
        createCustomer("John", "Doe", "johndoe@ex.com", "000000000", "111111111", 76.0, true);
        Role adminRole = createRole("ADMIN");
        Role manifest =createRole("MANIFEST");
        Role instructor = createRole("INSTRUCTOR");
        Role userRole = createRole("USER");
        createRole("AWAITING_APPROVAL");
        Skydiver skydiver = createSkydiver("Jakub", "Malewicz", "admin@ex.com", "000000000", "111111111", 76.5, "password", "C",adminRole, Set.of(Privilege.COACH));
        Skydiver skydiver2 = createSkydiver("Antoni", "Siek", "manifest@ex.com", "000000000", "111111111", 76.5, "password", "C",manifest, Set.of(Privilege.AFF_INSTRUCTOR, Privilege.TANDEM_PILOT) );
        Skydiver skydiver3 = createSkydiver("Mikołaj", "Kowaszewicz", "instructor@ex.com", "000000000", "111111111", 76.5, "password", "C", instructor,  Set.of(Privilege.TANDEM_PILOT) );
        createSkydiver("Bartosz", "Darłak", "aff@ex.com", "000000000", "111111111", 76.5, "password", "AFF",userRole, Set.of() );
        createSkydiver("Dominik", "Kwiecień", "student@ex.com", "000000000", "111111111", 76.5, "password", "Student",userRole, Set.of() );
        createSkydiver("Michał", "Żurawski", "LicenceB@ex.com", "000000000", "111111111", 76.5, "password", "B",userRole,  Set.of(Privilege.COACH) );
        Plane SPWAW = createPlane("SP-WAW", 1000);
        createPlane("D-FIDI", 800);
        Departure departure = createDeparture( true, true, SPWAW);
        for (int i = 0; i < 20; i++) {
            createDeparture( true, true, SPWAW);
        }
        createDeparture( true, true, SPWAW);
        createDeparture( true, true, SPWAW);
        addUserToDeparture(JumpType.STUDENT, null, skydiver2, departure);
        addUserToDeparture(JumpType.STUDENT, null, skydiver3, departure);
        addUserToDeparture(JumpType.STUDENT, null, skydiver, departure);

    }

    private DepartureUser addUserToDeparture(JumpType jumpType, Customer customer, Skydiver skydiver, Departure departure){
        return departureUserRepository.save(new DepartureUser(jumpType, customer, skydiver, departure));
    }

    private Departure createDeparture( boolean allowStudents, boolean allowAFF, Plane plane) {
        return departureRepository.save(new Departure("23-11-2023", "14:30", allowStudents,allowAFF, plane));
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

    private Skydiver createSkydiver(String firstName, String lastName, String email, String phone, String emergencyPhone, double weight, String password, String licence, Role role, Set<Privilege> privileges){

        skydiverRepository.save(new Skydiver(
                firstName,
                lastName,
                email,
                phone,
                emergencyPhone,
                weight,
                passwordEncoder.encode(CharBuffer.wrap(password)),
                licence,
                role,
                privileges
        ));
        return skydiverRepository.findByEmail(email).get();
    }

    private Plane createPlane(String name, double maxWeight){
        return planeRepository.save(new Plane(name, maxWeight));
    }
}
