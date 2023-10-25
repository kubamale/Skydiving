package jakub.malewicz.skydiving.Models;

import jakarta.persistence.*;
import jakub.malewicz.skydiving.DTOs.SkydiverDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "skydiver")
@NoArgsConstructor
@Getter
@Setter
public class Skydiver extends BasicUser implements UserDetails {

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

    @OneToMany(mappedBy = "approver")
    private Set<ApproveRequest> receivedApprovedRequests;
    @OneToMany(mappedBy = "requester")
    private Set<ApproveRequest> sentRequestsToApprove;
    public Skydiver(String firstName, String lastName, String email, String phone, String emergencyPhone, double weight, String password, String licence, Role role) {
        super(firstName, lastName, email, phone, emergencyPhone, weight);
        this.password = password;
        this.licence = licence;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
