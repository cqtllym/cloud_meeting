package com.rjxy.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "meeting_result_user_relation")
@AllArgsConstructor
@NoArgsConstructor
public class MeetingResultUserRelation {
    @Id
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "meeting_id")
    private Integer meetingId;
    @Column(name = "attend_time_list")
    private String attendTimeList;
}
