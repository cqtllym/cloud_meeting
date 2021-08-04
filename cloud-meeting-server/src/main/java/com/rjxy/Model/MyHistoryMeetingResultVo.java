package com.rjxy.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyHistoryMeetingResultVo {

    @JsonProperty("meeting_id")
    private int meetingId;

    @JsonProperty("meeting_number")
    private int meetingNumber;

    @JsonProperty("room_meeting_id")
    private int roomMeetingId;

    @JsonProperty("room_id")
    private int roomId;

    @JsonProperty("room_name")
    private String roomName;

    @JsonProperty("topic")
    private String topic;

    @JsonProperty("start_time")
    private Long startTime;

    @JsonProperty("end_time")
    private Long endTime;

    @JsonProperty("participants")
    private List<MeetingUser> participants;

    @JsonProperty("type")
    private int type;

}
