package jakub.malewicz.skydiving.DTOs;

import java.util.List;

public record DepartureDetailsDTO(long id, String date, String time, int skydiversAmount, int studentsAmount, int affAmount, boolean allowStudents, boolean allowAFF, PlaneDTO plane, List<SkydiverDTO> skydivers){
}
