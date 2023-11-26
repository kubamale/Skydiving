package jakub.malewicz.skydiving.Mappers;

import jakub.malewicz.skydiving.DTOs.DepartureDetailsDTO;
import jakub.malewicz.skydiving.DTOs.PlaneDTO;
import jakub.malewicz.skydiving.Models.Departure;
import jakub.malewicz.skydiving.Models.Plane;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartureMapper {
    DepartureDetailsDTO mapToDepartureDetailsDTO(Departure departure);
    PlaneDTO planeToPlaneDTO(Plane plane);
}
