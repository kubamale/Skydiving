package jakub.malewicz.skydiving.Configuration;

import jakub.malewicz.skydiving.Models.Customer;
import jakub.malewicz.skydiving.Repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) {
        customerRepository.save(new Customer(true));
    }
}
