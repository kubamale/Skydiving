package jakub.malewicz.skydiving.Repositories;

import jakub.malewicz.skydiving.Models.Departure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DepartureRepository extends JpaRepository<Departure, Long> {

    @Query(value = "SELECT d.id, d.date, d.time, d.allow_students, d.allowAFF, d.plane_id FROM departure d WHERE d.date = :date", nativeQuery = true)
    List<Departure> getDepartures(@Param("date") String date);
}
