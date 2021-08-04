package com.rjxy.Entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "room_reservation")
public class RoomReservation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "host_id")
    private Integer hostId;

    @Column(name = "topic")
    private String topic;

    @Column(name = "scheduled_start_time")
    private Timestamp scheduledStartTime;

    @Column(name = "scheduled_end_time")
    private Timestamp scheduledEndTime;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "modify_time")
    @UpdateTimestamp
    private Timestamp modifyTime;

    @Column(name = "participants")
    private String participants;

}
