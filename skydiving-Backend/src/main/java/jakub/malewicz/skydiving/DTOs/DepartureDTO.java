package jakub.malewicz.skydiving.DTOs;
public record DepartureDTO(long id,String date, String time, boolean allowStudents, boolean allowAFF, PlaneDTO plane) {
}
