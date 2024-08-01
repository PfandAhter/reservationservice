package intern.freedesk.reservationservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "desk_reservation")
@Getter
@Setter

public class DeskReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reservation_id")
    private String reservationId;

    @Column(name = "desk_id")
    private String deskId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "active")
    private int active;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "update_date")
    private Timestamp updateDate;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

}
