package jakub.malewicz.skydiving.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "Plane")
@NoArgsConstructor
@Getter
@Setter
public class Plane {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private double maxWeight;

    @OneToMany(mappedBy = "plane")
    private Set<Departure> departures;

}
