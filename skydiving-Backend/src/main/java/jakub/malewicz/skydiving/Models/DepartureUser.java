package jakub.malewicz.skydiving.Models;

import jakarta.persistence.*;
import jakub.malewicz.skydiving.enums.JumpType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "departure_users")
@NoArgsConstructor
@Getter
@Setter
public class DepartureUser {

    @Id
    @GeneratedValue
    private long id;
    @Enumerated(EnumType.STRING)
    private JumpType jumpType;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "skydiver_id", nullable = false)
    private Skydiver skydiver;

    @ManyToOne
    @JoinColumn(name = "departure_id", nullable = false)
    private Departure departure;

    public DepartureUser(JumpType jumpType, Customer customer, Skydiver skydiver, Departure departure) {
        this.jumpType = jumpType;
        this.customer = customer;
        this.skydiver = skydiver;
        this.departure = departure;
    }

    public DepartureUser(JumpType jumpType, Skydiver skydiver, Departure departure) {
        this.jumpType = jumpType;
        this.skydiver = skydiver;
        this.departure = departure;
    }
}
