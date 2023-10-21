package jakub.malewicz.skydiving.Repositories;

import jakub.malewicz.skydiving.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
