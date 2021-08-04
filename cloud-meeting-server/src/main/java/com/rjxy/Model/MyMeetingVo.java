package com.rjxy.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rjxy.Entity.Meeting;
import com.rjxy.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyMeetingVo {

    @JsonProperty("reserved_meeting_list")
    private List<MyMeetingElement> reservedMeetingList;

    @JsonProperty("holding_meeting_list")
    private List<MyMeetingElement> holdingMeetingList;
}


