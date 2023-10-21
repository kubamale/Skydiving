package jakub.malewicz.skydiving.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "customer")
@NoArgsConstructor
@Getter
@Setter
public class Customer extends BasicUser{
    @Id
    @GeneratedValue
    private long id;
    private boolean signedForm;
    @OneToMany(mappedBy = "customer")
    private Set<DepartureUser> departures;

    public Customer(boolean signedForm) {
        super("test", "test", "test", "test" , "test", 5.34);
        this.signedForm = signedForm;
    }
}
