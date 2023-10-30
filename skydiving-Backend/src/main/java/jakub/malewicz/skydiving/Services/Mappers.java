package jakub.malewicz.skydiving.Services;

import jakub.malewicz.skydiving.DTOs.DepartureDTO;
import jakub.malewicz.skydiving.DTOs.DepartureDetailsDTO;
import jakub.malewicz.skydiving.DTOs.PlaneDTO;
import jakub.malewicz.skydiving.DTOs.SkydiverDTO;
import jakub.malewicz.skydiving.Models.Departure;
import jakub.malewicz.skydiving.Models.DepartureUser;
import jakub.malewicz.skydiving.Models.Plane;
import jakub.malewicz.skydiving.Models.Skydiver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Mappers {

    public static SkydiverDTO mapToDTO(Skydiver skydiver){
        return new SkydiverDTO(skydiver.getId(), skydiver.getFirstName(), skydiver.getLastName(), skydiver.getWeight(), skydiver.getLicence());
    }

    public static DepartureDetailsDTO mapToDTO(Departure departure, List<DepartureUser> departureUserList){
        return new DepartureDetailsDTO(
                departure.getId(),
                departure.getDate(),
                departure.getTime(),
                departureUserList.size(),
                (int) departureUserList.stream().filter(d -> d.getSkydiver().getLicence().equals("Student")).count(),
                (int) departureUserList.stream().filter(d -> d.getSkydiver().getLicence().equals("AFF")).count(),
                departure.isAllowStudents(),
                departure.isAllowAFF(),
                new PlaneDTO(
                        departure.getPlane().getId(),
                        departure.getPlane().getName(),
                        departure.getPlane().getMaxWeight()
                ),
                departureUserList.stream().map(DepartureUser::getSkydiver).mapToDouble(Skydiver::getWeight).sum(),
                departureUserList.stream().map(DepartureUser::getSkydiver).map(Mappers::mapToDTO).toList()
        );
    }

    public static DepartureDTO mapToDTO(Departure departure){
        return new DepartureDTO(
                departure.getId(),
                departure.getDate(),
                departure.getTime(),
                departure.isAllowStudents(),
                departure.isAllowAFF(),
                Mappers.mapToDTO(departure.getPlane())
        );
    }

    public static PlaneDTO mapToDTO(Plane plane){
        return new PlaneDTO(plane.getId(), plane.getName(), plane.getMaxWeight());
    }

}
