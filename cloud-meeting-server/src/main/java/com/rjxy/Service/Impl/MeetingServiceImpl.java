package com.rjxy.Service.Impl;


import com.rjxy.Entity.Meeting;
import com.rjxy.Entity.MeetingResultUserRelation;
import com.rjxy.Entity.MeetingUserRelation;
import com.rjxy.Entity.User;
import com.rjxy.Enum.MeetingStatusEnum;
import com.rjxy.Exception.MeetingNotExistException;
import com.rjxy.Model.MeetingUser;
import com.rjxy.Repository.MeetingRepository;
import com.rjxy.Repository.MeetingUserRelationRepository;
import com.rjxy.Service.MeetingService;
import com.rjxy.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MeetingUserRelationRepository meetingUserRelationRepository;

    public Meeting getMeetingByMeetingNumber(Integer meetingNumber) {
        return meetingRepository.findByMeetingNumberAndStatusStartOrHolding(meetingNumber);
    }

    public Meeting createInstantMeeting(Integer hostId, String topic, boolean requiredPwd, Integer password) {

        Meeting meeting = new Meeting();
        meeting.setType(1); // 1-临时会议 2-预约会议
        meeting.setHostId(hostId);
        meeting.setTopic(topic);
        meeting.setStatus(MeetingStatusEnum.Holding.getStatus());
        meeting.setStartTime(new Timestamp(System.currentTimeMillis()));
        if (requiredPwd) {
            meeting.setRequirePassword(true);
            meeting.setPassword(password);
        } else
            meeting.setRequirePassword(false);
        Random seedRandom = new Random(System.currentTimeMillis());
        Integer random;
        do {
            random = (int) ((seedRandom.nextDouble() * 9 + 1) * (Math.pow(10, 8)));
        } while (meetingRepository.findById(random).isPresent());
        meeting.setMeetingNumber(random);

        return meetingRepository.save(meeting);
    }


    @Override
    public void modifyMeetingStatus(Integer meetingNumber, MeetingStatusEnum statusEnum) throws MeetingNotExistException {
        Meeting byMeetingNumber = meetingRepository.findByMeetingNumberAndStatusStartOrHolding(meetingNumber);
        if (byMeetingNumber == null || byMeetingNumber.getId() == 0) {
            throw new MeetingNotExistException();
        }
        byMeetingNumber.setStatus(statusEnum.getStatus());
        meetingRepository.save(byMeetingNumber);

    }

    @Override
    public void recordMeetingEndTime(Integer meetingNumber, Timestamp endTime) throws MeetingNotExistException {
        Meeting byMeetingNumber = meetingRepository.findByMeetingNumberAndStatusStartOrHolding(meetingNumber);
        if (byMeetingNumber == null || byMeetingNumber.getId() == 0) {
            throw new MeetingNotExistException();
        }
        byMeetingNumber.setEndTime(endTime);
        meetingRepository.save(byMeetingNumber);
    }

    @Override
    public void recordMeetingStartTime(Integer meetingNumber, Timestamp startTime) throws MeetingNotExistException {
        Meeting byMeetingNumber = meetingRepository.findByMeetingNumberAndStatusStartOrHolding(meetingNumber);
        if (byMeetingNumber == null || byMeetingNumber.getId() == 0) {
            throw new MeetingNotExistException();
        }
        byMeetingNumber.setStartTime(startTime);
        meetingRepository.save(byMeetingNumber);
    }

    //获得我的会议列表
    @Override
    public List<Meeting> getMeetingByUserId(Integer userId) {
        List<MeetingUserRelation> relations = meetingUserRelationRepository.findByUserId(userId);
        List<Meeting> meetings = new ArrayList<>();
        for (MeetingUserRelation relation : relations) {
            Meeting meeting = meetingRepository.findMeetingById(relation.getMeetingId());
            if (meeting != null) {
                meetings.add(meeting);
            }
        }
        meetings.sort(Comparator.comparing(Meeting::getScheduledStartTime).reversed());
        return meetings;
    }

    @Override
    // 获取会议的参会人记录
    public List<MeetingUser> getParticipantsByMeetingId(Integer meetingId) {
        List<MeetingUserRelation> userList = meetingUserRelationRepository.findByMeetingId(meetingId);
        List<MeetingUser> result = new ArrayList<>();
        for (MeetingUserRelation userRelation : userList) {
            User userById = userService.getUserById(userRelation.getUserId());
            result.add(new MeetingUser(userById.getId(), userById.getName(), ""));
        }
        return result;
    }

    @Override
    public void addMeetingUserRelation(Integer meetingId, Integer userId) {
        meetingUserRelationRepository.save(new MeetingUserRelation(0, meetingId, userId));
    }


    @Override
    public void deleteMeetingUserRelation(Integer id) {
        meetingUserRelationRepository.deleteByMeetingId(id);
    }

    @Override
    public MeetingUserRelation getMeetingUserRelationByMeetingIdAndUserId(Integer meetingId, Integer userId) {
        return meetingUserRelationRepository.findByMeetingIdAndUserId(meetingId, userId);
    }

    @Override
    public boolean checkPassword(Integer meetingNumber, Integer meetingPassword) {
        Meeting meetingByMeetingNumber = meetingRepository.findByMeetingNumberAndStatusStartOrHolding(meetingNumber);
        if (meetingByMeetingNumber == null || meetingByMeetingNumber.getId() == 0) {
            return false;
        }
        return meetingByMeetingNumber.getPassword().equals(meetingPassword);
    }

    @Override
    public Meeting createReservedMeeting(Integer hostId, String topic, Timestamp scheduledStartTime, Timestamp scheduledEndTime, Boolean requiredPassword, Integer password) {
        Meeting meeting = new Meeting();
        meeting.setType(2); // 1-临时会议 2-预约会议
        meeting.setHostId(hostId);
        meeting.setTopic(topic);
        meeting.setStatus(MeetingStatusEnum.NotStarted.getStatus());
        meeting.setScheduledStartTime(scheduledStartTime);
        meeting.setScheduledEndTime(scheduledEndTime);
        if (requiredPassword) {
            meeting.setRequirePassword(true);
            meeting.setPassword(password);
        } else
            meeting.setRequirePassword(false);
        Random seedRandom = new Random(System.currentTimeMillis());
        Integer random;
        do {
            random = (int) ((seedRandom.nextDouble() * 9 + 1) * (Math.pow(10, 8)));
        } while (meetingRepository.findByMeetingNumberAndStatusStartOrHolding(random) != null);
        meeting.setMeetingNumber(random);

        return meetingRepository.save(meeting);
    }

    @Override
    public boolean deleteReservedMeeting(Integer meetingId, Integer hostId) {
        Meeting meeting = meetingRepository.findMeetingById(meetingId);
        if (meeting.getHostId().equals(hostId)) {
            if (meeting.getStatus() == MeetingStatusEnum.Holding.getStatus())
                return false; // 会议进行中不能删除会议
            else {
                meetingRepository.delete(meeting);
                return true; // 删除成功
            }
        } else return false; // 非会议主持人不能删除会议
    }

    @Override
    public Meeting updateReservedMeeting(Integer id, String topic, Timestamp scheduledStartTime, Timestamp scheduledEndTime, Boolean requiredPassword, Integer password) {
        Meeting meeting = meetingRepository.findMeetingById(id);
        if (meeting.getStatus() == MeetingStatusEnum.NotStarted.getStatus()) {
            meeting.setTopic(topic);
            meeting.setScheduledStartTime(scheduledStartTime);
            meeting.setScheduledEndTime(scheduledEndTime);
            if (requiredPassword) {
                meeting.setRequirePassword(true);
                meeting.setPassword(password);
            }
        }

        return meetingRepository.save(meeting);
    }

    @Override
    public Meeting retrieveReservedMeeting(Integer id) {
        return meetingRepository.findMeetingById(id);
    }

    @Override
    public List<Meeting> getExpiredMeetingList() {
        return meetingRepository.findMeetingsByStatusNotStartedAndScheduledEndTimeBeforeNow(new Timestamp(System.currentTimeMillis()));
    }


}
