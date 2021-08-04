package com.rjxy.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    private Integer type;

    @Column(name = "host_id")
    private Integer hostId;

    @Column(name = "meeting_number")
    private Integer meetingNumber;

    @Column(name = "topic")
    private String topic;

    @Column(name = "scheduled_start_time")
    private Timestamp scheduledStartTime;

    @Column(name = "scheduled_end_time")
    private Timestamp scheduledEndTime;

    @Column(name = "status")
    private Integer status;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "modify_time")
    @UpdateTimestamp
    private Timestamp modifyTime;

    @Column(name = "require_password")
    private Boolean requirePassword;

    @Column(name = "password")
    private Integer password;

}
