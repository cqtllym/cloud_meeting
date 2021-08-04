package com.rjxy.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@AllArgsConstructor
@Data
public class MyMeetingElement {
    @JsonProperty("meeting_id")
    private Integer meetingId;

    @JsonProperty("meeting_number")
    private Integer meetingNumber;

    @JsonProperty("type")
    private Integer type;

    @JsonProperty("room_meeting_id")
    private Integer roomMeetingId;

    @JsonProperty("room_id")
    private Integer roomId;

    @JsonProperty("room_name")
    private String roomName;

    @JsonProperty("host_id")
    private Integer hostId;

    @JsonProperty("hostname")
    private String hostName;

    @JsonProperty("topic")
    private String topic;

    @JsonProperty("scheduled_start_time")
    private long scheduledStartTime;

    @JsonProperty("scheduled_end_time")
    private long scheduledEndTime;

    @JsonProperty("start_time")
    private long startTime;

    @JsonProperty("participants")
    private List<MeetingUser> participants;

}
