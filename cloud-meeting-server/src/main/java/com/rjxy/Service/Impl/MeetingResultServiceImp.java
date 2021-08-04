package com.rjxy.Service.Impl;

import com.rjxy.Entity.*;
import com.rjxy.Enum.MeetingStatusEnum;
import com.rjxy.Model.MeetingStatus;
import com.rjxy.Model.MeetingUser;
import com.rjxy.Repository.MeetingRepository;
import com.rjxy.Repository.MeetingResultRepository;
import com.rjxy.Repository.MeetingResultUserRelationRepository;
import com.rjxy.Repository.MeetingUserRelationRepository;
import com.rjxy.Service.MeetingResultService;
import com.rjxy.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingResultServiceImp implements MeetingResultService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingResultRepository meetingResultRepository;

    @Autowired
    private MeetingResultUserRelationRepository meetingResultUserRelationRepository;

    @Autowired
    private MeetingUserRelationRepository meetingUserRelationRepository;

    @Autowired
    private UserService userService;

    // 设置会议的结果
    @Override
    public void setMeetingResult(MeetingResult result) {
        meetingResultRepository.save(result);
    }

    @Override
    public void setMeetingResultUserRelation(MeetingResultUserRelation meetingResultUserRelation) {
        meetingResultUserRelationRepository.save(meetingResultUserRelation);
    }

    // 获取会议的结果
    @Override
    public MeetingResult getMeetingResult(Integer meetingId) {
        return meetingResultRepository.findMeetingResultByMeetingId(meetingId);
    }


    @Override
    public List<Meeting> getHistoryMeetingByUserId(Integer userId, Timestamp startTime, Timestamp endTime) {
        return meetingRepository.findMeetingByStatusAndUserIdAndStartTimeAndEndTime(MeetingStatusEnum.Finished.getStatus(), userId, startTime, endTime);
    }

    @Override
    public List<Meeting> getHistoryMeetingByUserId(Integer userId) {
        return meetingRepository.findMeetingByStatusAndUserId(MeetingStatusEnum.Finished.getStatus(), userId);
    }

    @Override
    public List<MeetingUser> getParticipantsByMeetingId(Integer meetingId) {
        List<MeetingResultUserRelation> userList = meetingResultUserRelationRepository.findByMeetingId(meetingId);
        List<MeetingUser> result = new ArrayList<>();
        for (MeetingResultUserRelation userRelation : userList) {
            User userById = userService.getUserById(userRelation.getUserId());
            result.add(new MeetingUser(userById.getId(), userById.getName(), ""));
        }
        return result;
    }

}
