package jakub.malewicz.skydiving.Services;
import jakub.malewicz.skydiving.DTOs.SkydiverDTO;
import jakub.malewicz.skydiving.Models.Skydiver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class Mappers {

    public static SkydiverDTO mapToDTO(Skydiver skydiver, String jumpType){
        return new SkydiverDTO(skydiver.getId(), skydiver.getFirstName(), skydiver.getLastName(), skydiver.getWeight(), skydiver.getLicence(), jumpType, skydiver.getEmail());
    }
}
