package intern.freedesk.reservationservice.repository;

import intern.freedesk.reservationservice.model.entity.Desk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeskRepository extends JpaRepository<Desk,String> {

    @Query("SELECT d FROM Desk d WHERE d.deskId = ?1")
    Desk findByDeskId(String deskId);

    @Query("SELECT d FROM Desk d WHERE d.deskNo = ?1")
    Desk findByDeskNo(int deskId);

    @Query("SELECT d FROM Desk d WHERE d.active = 1 OR d.active = 0 OR d.active = 2")
    List<Desk> findAllActiveAndDeActivateAndReservedDesks();

}
