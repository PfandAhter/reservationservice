package intern.freedesk.reservationservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "desk")
@Getter
@Setter
//@SqlRestriction

public class Desk {

    @Id
    //@SqeuenceGenerator
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "desk_id")
    private String deskId;

    @Column(name = "desk_no")
    private Integer deskNo;

    @Column(name = "description")
    private String description;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "update_date")
    private Timestamp updateDate;

    @Column(name = "active")
    private Integer active;
}

//ust componentten alt componente props ile geciriyoruz
//react return mantigi jsx mantigi props mantigi arastir.

//
