package jakub.malewicz.skydiving.Mappers;

import jakub.malewicz.skydiving.DTOs.PlaneDTO;
import jakub.malewicz.skydiving.Models.Plane;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaneMapper {

    PlaneDTO mapToPlaneDTO(Plane plane);

}
