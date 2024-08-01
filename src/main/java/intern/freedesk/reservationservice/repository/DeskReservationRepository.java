package intern.freedesk.reservationservice.repository;

import intern.freedesk.reservationservice.model.entity.DeskReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeskReservationRepository extends JpaRepository<DeskReservation,String> {

    @Query("SELECT d FROM DeskReservation d WHERE d.deskId = ?1")
    DeskReservation findReservationByDeskId(String deskId);


    @Query("SELECT d FROM DeskReservation d WHERE d.userId = ?1")
    List<DeskReservation> findReservationByEmail (String email);

    @Query("SELECT d FROM DeskReservation d WHERE d.userId = ?1 and d.active = 0")
    List<DeskReservation> findReservationByUserId (String userId);

    @Query("SELECT d FROM DeskReservation d WHERE " +
            "(d.startDate BETWEEN ?1 AND ?2) OR " +
            "(d.endDate BETWEEN ?1 AND ?2) OR " +
            "(?1 BETWEEN d.startDate AND d.endDate) OR" +
            "(?2 BETWEEN d.startDate AND d.endDate)")
    List<DeskReservation> findReservationByDate (String startDate, String endDate);


    @Query("SELECT d FROM DeskReservation d WHERE d.userId = ?1 AND " +
            "(d.startDate BETWEEN ?2 AND ?3) OR " +
            "(d.endDate BETWEEN ?2 AND ?3) OR " +
            "(?2 BETWEEN d.startDate AND d.endDate) OR" +
            "(?3 BETWEEN d.startDate AND d.endDate)")
    List<DeskReservation> findDeskReservationsByUserIdAndDates (String userId, String startDate, String endDate);

}
