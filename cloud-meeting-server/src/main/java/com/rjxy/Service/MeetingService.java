package com.rjxy.Service;

import com.rjxy.Entity.Meeting;
import com.rjxy.Entity.MeetingUserRelation;
import com.rjxy.Enum.MeetingStatusEnum;
import com.rjxy.Exception.MeetingNotExistException;
import com.rjxy.Model.MeetingUser;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.List;

public interface MeetingService {
    // 根据会议号获取会议
    Meeting getMeetingByMeetingNumber(Integer meetingNumber);

    // 创建新的临时会议
    Meeting createInstantMeeting(Integer hostId, String topic, boolean requirePwd, Integer passowrd);

    // 修改会议的状态
    void modifyMeetingStatus(Integer meetingNumber, MeetingStatusEnum statusEnum) throws MeetingNotExistException;

    // 记录会议的结束时间
    void recordMeetingEndTime(Integer meetingNumber, Timestamp endTime) throws MeetingNotExistException;

    // 记录会议的开始时间
    void recordMeetingStartTime(Integer meetingNumber, Timestamp startTime) throws MeetingNotExistException;

    // 获取用户的预约会议列表
    List<Meeting> getMeetingByUserId(Integer userId);

    // 获取会议的预约参会人
    List<MeetingUser> getParticipantsByMeetingId(Integer id);

    // 新增会议用户relation记录，现在想到的途径有：1。预约会议的时候主持人要写一条relation 2。邀请参会人的时候写relation  3。根据密码加入预约会议的时候写relation
    void addMeetingUserRelation(Integer meetingId, Integer userId);

    // 删除会议用户relation表中的记录
    void deleteMeetingUserRelation(Integer id);

    // 根据会议号和用户的id获取relation记录
    MeetingUserRelation getMeetingUserRelationByMeetingIdAndUserId(Integer meetingNumber, Integer userId);

    boolean checkPassword(Integer meetingNumber, Integer meetingPassword);

    Meeting createReservedMeeting(Integer hostId, String topic, Timestamp scheduledStartTime, Timestamp scheduledEndTime, Boolean requiredPassword, Integer password);

    boolean deleteReservedMeeting(Integer meetingId, Integer hostId);

    Meeting updateReservedMeeting(Integer id, String topic, Timestamp scheduledStartTime, Timestamp scheduledEndTime, Boolean requiredPassword, Integer password);

    Meeting retrieveReservedMeeting(Integer id);

    List<Meeting> getExpiredMeetingList();
}
