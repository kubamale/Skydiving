package jakub.malewicz.skydiving.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "Departure")
@Getter
@Setter
public class Departure {

    @Id
    @GeneratedValue
    private long id;
    private Date date;
    private boolean allowStudents;
    private boolean allowAFF;

    @ManyToOne
    @JoinColumn(name = "plane_id", nullable = false)
    private Plane plane;

    @OneToMany(mappedBy = "departure")
    private Set<DepartureUser> departureUsers;
}
