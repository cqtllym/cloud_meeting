package com.rjxy.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "meeting_result")
public class MeetingResult {
    @Id
    @JsonIgnore
    private Integer id;

    @Column(name = "meeting_id")
    @JsonProperty
    private Integer meetingId;

    @Column(name = "result")
    private String result;
}
