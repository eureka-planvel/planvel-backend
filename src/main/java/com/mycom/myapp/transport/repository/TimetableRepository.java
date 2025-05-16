package com.mycom.myapp.transport.repository;

import com.mycom.myapp.transport.entity.Timetable;
import com.mycom.myapp.transport.entity.type.TransportType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TimetableRepository extends JpaRepository<Timetable, Integer> {

  @Query("""
        SELECT t FROM Timetable t
        JOIN FETCH t.departureStation ds
        JOIN FETCH ds.region dr
        JOIN FETCH t.arrivalStation as_
        JOIN FETCH as_.region ar
        WHERE t.transportType = :transportType
          AND (
                (t.departureStation.id = :departureId AND t.arrivalStation.id = :arrivalId)
             OR (t.departureStation.id = :arrivalId AND t.arrivalStation.id = :departureId)
          )
        ORDER BY t.departureTime
    """)
  List<Timetable> findRoundTripTimetablesFiltered(
      @Param("departureId") int departureId,
      @Param("arrivalId") int arrivalId,
      @Param("transportType") TransportType transportType
  );
}