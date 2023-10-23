package jakub.malewicz.skydiving.DTOs;

public record DepartureDetailsDTO(long id,String date, String time, int skydiversAmount,  int studentsAmount, int affAmount, boolean allowStudents, boolean allowAFF, PlaneDTO plane){
}
