package com.rjxy.Service;

import com.rjxy.Entity.Meeting;
import com.rjxy.Entity.MeetingResult;
import com.rjxy.Entity.MeetingResultUserRelation;
import com.rjxy.Model.MeetingUser;

import java.sql.Timestamp;
import java.util.List;


public interface MeetingResultService {

    void setMeetingResult(MeetingResult result);

    void setMeetingResultUserRelation(MeetingResultUserRelation meetingResultUserRelation);


    MeetingResult getMeetingResult(Integer meetingId);

    // 获取用户的历史会议
    List<Meeting> getHistoryMeetingByUserId(Integer userId, Timestamp startTime, Timestamp endTime);

    // 获取用户的历史会议
    List<Meeting> getHistoryMeetingByUserId(Integer userId);

    List<MeetingUser> getParticipantsByMeetingId(Integer id);

}
