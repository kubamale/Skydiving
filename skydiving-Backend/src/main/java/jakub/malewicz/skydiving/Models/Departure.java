package jakub.malewicz.skydiving.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "departure")
@Getter
@Setter
public class Departure {

    @Id
    @GeneratedValue
    private long id;
    private String date;
    private String time;
    private boolean allowStudents;
    private boolean allowAFF;

    @ManyToOne
    @JoinColumn(name = "plane_id", nullable = false)
    private Plane plane;

    @OneToMany(mappedBy = "departure", cascade = CascadeType.ALL)
    private Set<DepartureUser> departureUsers;

    public Departure(String date, String time, boolean allowStudents, boolean allowAFF, Plane plane) {
        this.date = date;
        this.time = time;
        this.allowStudents = allowStudents;
        this.allowAFF = allowAFF;
        this.plane = plane;
    }
}
