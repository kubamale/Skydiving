package jakub.malewicz.skydiving.Models;

import jakarta.persistence.*;
import jakub.malewicz.skydiving.DTOs.SkydiverDTO;
import jakub.malewicz.skydiving.Services.Mappers;
import jakub.malewicz.skydiving.enums.JumpType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

import java.util.List;
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

    @Transient
    private int skydiversAmount;
    @Transient
    private double totalWeight;
    @Transient
    private int affAmount;
    @Transient
    private int studentsAmount;
    @Transient
    List<SkydiverDTO> skydivers;

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

    public void fillData(){
        affAmount = (int )departureUsers.stream().filter(departureUser -> departureUser.getJumpType().equals(JumpType.AFF)).count();
        studentsAmount = (int)departureUsers.stream().filter(departureUser -> departureUser.getJumpType().equals(JumpType.STUDENT)).count();
        totalWeight = departureUsers.stream().mapToDouble(dep -> {
            if (dep.getJumpType().equals(JumpType.TANDEM)){
                return dep.getSkydiver().getWeight() + dep.getCustomer().getWeight();
            }
            else if(dep.getJumpType().equals(JumpType.AFF)){
                return dep.getSkydiver().getWeight() + dep.getAffStudent().getWeight();
            }
            else {
                return  dep.getSkydiver().getWeight();
            }
        }).sum();
        skydiversAmount = departureUsers.stream().mapToInt(dep -> {
            if (dep.getJumpType().equals(JumpType.AFF) || dep.getJumpType().equals(JumpType.TANDEM)){
                return 2;
            }else{
                return 1;
            }
        }).sum();
        skydivers = new ArrayList<>();
        departureUsers.forEach(dep -> {
            if (dep.getJumpType().equals(JumpType.AFF)){
                skydivers.add(Mappers.mapToDTO(dep.getSkydiver(), JumpType.AFF.name()));
                skydivers.add(Mappers.mapToDTO(dep.getAffStudent(), JumpType.AFF.name()));
            } else{
                skydivers.add(Mappers.mapToDTO(dep.getSkydiver(), dep.getJumpType().name()));
            }
        });


    }
}
