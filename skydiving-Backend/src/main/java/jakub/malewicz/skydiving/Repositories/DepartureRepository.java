package jakub.malewicz.skydiving.Repositories;

import jakub.malewicz.skydiving.Models.Departure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DepartureRepository extends JpaRepository<Departure, Long> {

    @Query(value = "SELECT d.id, d.date, d.time, d.allow_students, d.allowAFF, d.plane_id FROM departure d WHERE d.date = :date ORDER BY d.time LIMIT 6 OFFSET :page*6" , nativeQuery = true)
    List<Departure> getDepartures(@Param("date") String date, @Param("page") int page);

    @Query(value = "SELECT d.date FROM departure d WHERE DATE(d.date) >= DATE(:startDate) AND DATE(d.date) <= DATE(:endDate) ", nativeQuery = true)
    List<String> getDates(@Param("startDate")String startDate, @Param("endDate")String endDate);
}
