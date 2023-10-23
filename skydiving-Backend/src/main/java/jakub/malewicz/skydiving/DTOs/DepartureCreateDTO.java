package jakub.malewicz.skydiving.DTOs;

public record DepartureCreateDTO(String date, String time, boolean allowStudents, boolean allowAFF, long planeId) {

}
