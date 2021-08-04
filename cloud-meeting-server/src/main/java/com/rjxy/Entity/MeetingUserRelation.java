package com.rjxy.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "meeting_user_relation")
public class MeetingUserRelation {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "meeting_id")
    private Integer meetingId;

    @Column(name = "user_id")
    private Integer userId;


}
