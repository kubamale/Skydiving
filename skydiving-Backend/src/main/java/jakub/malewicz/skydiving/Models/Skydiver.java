package jakub.malewicz.skydiving.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "skydiver")
@NoArgsConstructor
@Getter
@Setter
public class Skydiver extends BasicUser{

    @Id
    @GeneratedValue
    private long id;
    private String password;
    private String licence;
    private boolean active;
    @OneToMany(mappedBy = "instructor")
    private List<Skydiver> pupils;
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Skydiver instructor;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "skydiver")
    private Set<DepartureUser> departures;
}
