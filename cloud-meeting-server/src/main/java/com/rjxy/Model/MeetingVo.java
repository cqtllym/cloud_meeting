package com.rjxy.Model;

import com.rjxy.Entity.Meeting;
import com.rjxy.Entity.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MeetingVo {

    private Integer id;

    private Integer type;

    private Integer hostId;

    private String hostName;

    private Integer meetingNumber;

    private String topic;

    private long scheduledStartTime;

    private long scheduledEndTime;

    private Integer status;

    private long startTime;

    private long endTime;

    private Boolean requirePassword;

    private Integer password;

    public static MeetingVo setMeetingVo(Meeting meeting, User host) {

        MeetingVo mv = new MeetingVo();
        mv.setId(meeting.getId());
        mv.setType(meeting.getType());
        mv.setHostId(meeting.getHostId());
        mv.setHostName(host.getName());
        mv.setMeetingNumber(meeting.getMeetingNumber());
        mv.setTopic(meeting.getTopic());
        if (meeting.getScheduledStartTime() != null)
            mv.setScheduledStartTime(meeting.getScheduledStartTime().getTime());
        if (meeting.getScheduledEndTime() != null)
            mv.setScheduledEndTime(meeting.getScheduledEndTime().getTime());
        mv.setStatus(meeting.getStatus());
        if (meeting.getStartTime() != null)
            mv.setStartTime(meeting.getStartTime().getTime());
        if (meeting.getEndTime() != null)
            mv.setEndTime(meeting.getEndTime().getTime());
        mv.setRequirePassword(meeting.getRequirePassword());
        mv.setPassword(meeting.getPassword());

        return mv;

    }

}
