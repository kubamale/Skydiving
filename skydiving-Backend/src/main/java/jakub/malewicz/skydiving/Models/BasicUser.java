package jakub.malewicz.skydiving.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "basic_user")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
public class BasicUser {

    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String phone;
    private String emergencyPhone;
    private double weight;

    public BasicUser(String firstName, String lastName, String email, String phone, String emergencyPhone, double weight) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.emergencyPhone = emergencyPhone;
        this.weight = weight;
    }
}
