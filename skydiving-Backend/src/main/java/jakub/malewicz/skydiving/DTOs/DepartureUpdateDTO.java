package jakub.malewicz.skydiving.DTOs;

import java.util.List;

public record DepartureUpdateDTO(
        long id,
        String date,
        String time,
        boolean allowStudents,
        boolean allowAFF,
        long planeId,
        List<Long> usersId
){}
