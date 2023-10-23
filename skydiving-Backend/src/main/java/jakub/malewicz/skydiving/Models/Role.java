package jakub.malewicz.skydiving.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "_role")
@NoArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<Skydiver> skydivers;

    public Role(String name) {
        this.name = name;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name));
    }
}
