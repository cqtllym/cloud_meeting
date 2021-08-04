package com.rjxy.Controller;

import com.rjxy.Entity.*;
import com.rjxy.Enum.ErrorEnum;
import com.rjxy.Enum.MeetingStatusEnum;
import com.rjxy.Model.MeetingUser;
import com.rjxy.Model.MyHistoryMeetingResultVo;
import com.rjxy.Model.MyMeetingElement;
import com.rjxy.Model.MyMeetingVo;
import com.rjxy.Results.Result;
import com.rjxy.Service.MeetingResultService;
import com.rjxy.Service.MeetingService;
import com.rjxy.Service.RoomService;
import com.rjxy.Service.UserService;
import com.rjxy.Util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
public class ManageMeetingController {

    @Autowired
    private MeetingResultService meetingResultService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String meetingUserIntimeSetPrefix = "meeting_user_intime_set_";

    @GetMapping("/my_meeting_list")
    public Result<MyMeetingVo> GetMyMeetingList(HttpServletRequest request) {
        User user = SessionUtil.GetUser(request);
        if (user == null || user.getId() == 0) {
            // 返回用户未登录状态
            return Result.error(ErrorEnum.SessionNotExistError);
        }
        MyMeetingVo result = new MyMeetingVo(new ArrayList<>(), new ArrayList<>());
        List<Meeting> meetingList = meetingService.getMeetingByUserId(user.getId());
        for (Meeting meeting : meetingList) {
            if (meeting != null) {
                User host = userService.getUserById(meeting.getHostId());
                List<MeetingUser> participants = new ArrayList<>();
                //会议未开始，从mysql中获取参会人，参会人的概念这里指的是邀请的参会人
                if (meeting.getStatus() == MeetingStatusEnum.NotStarted.getStatus()) {
                    participants = meetingService.getParticipantsByMeetingId(meeting.getId());
                    result.getReservedMeetingList().add(new MyMeetingElement(meeting.getId(), meeting.getMeetingNumber(), meeting.getType(), 0, 0, "", host.getId(), host.getName(), meeting.getTopic(), meeting.getScheduledStartTime().getTime(), meeting.getScheduledEndTime().getTime(), 0, participants));
                }
                //会议进行中，从redis中获取参会人，参会人的概念这里指的是正在参会的人
                else if (meeting.getStatus() == MeetingStatusEnum.Holding.getStatus()) {
                    Set<String> members = stringRedisTemplate.boundSetOps(meetingUserIntimeSetPrefix + meeting.getMeetingNumber()).members();
                    if (members != null) {
                        for (String member : members) {
                            User participant = userService.getUserById(Integer.parseInt(member));
                            participants.add(new MeetingUser(participant.getId(), participant.getName(), ""));
                        }
                    }
                    result.getHoldingMeetingList().add(new MyMeetingElement(meeting.getId(), meeting.getMeetingNumber(), meeting.getType(), 0, 0, "", host.getId(), host.getName(), meeting.getTopic(), meeting.getScheduledStartTime().getTime(), meeting.getScheduledEndTime().getTime(), meeting.getStartTime().getTime(), participants));
                }
            }
        }

        List<RoomReservation> holdingRoomReservationByUserId = roomService.getHoldingRoomReservationByUserId(user.getId());
        for (RoomReservation roomReservation : holdingRoomReservationByUserId) {
            User host = userService.getUserById(roomReservation.getHostId());
            Room room = roomService.retrieveRoomById(roomReservation.getRoomId());
            List<MeetingUser> participants = new ArrayList<>();
            String[] userIds = roomReservation.getParticipants().split(",");
            for (String userId : userIds) {
                User participant = userService.getUserById(Integer.parseInt(userId));
                participants.add(new MeetingUser(participant.getId(), participant.getName(), ""));
            }
            result.getHoldingMeetingList().add(new MyMeetingElement(0, 0, 3, roomReservation.getId(), roomReservation.getRoomId(), room.getName(), host.getId(), host.getName(), roomReservation.getTopic(), roomReservation.getScheduledStartTime().getTime(), roomReservation.getScheduledEndTime().getTime(), 0, participants));
        }

        List<RoomReservation> notStartedRoomReservations = roomService.getNotStartedRoomReservationByUserId(user.getId());
        for (RoomReservation roomReservation : notStartedRoomReservations) {
            User host = userService.getUserById(roomReservation.getHostId());
            Room room = roomService.retrieveRoomById(roomReservation.getRoomId());
            List<MeetingUser> participants = new ArrayList<>();
            String[] userIds = roomReservation.getParticipants().split(",");
            for (String userId : userIds) {
                User participant = userService.getUserById(Integer.parseInt(userId));
                participants.add(new MeetingUser(participant.getId(), participant.getName(), ""));
            }
            result.getReservedMeetingList().add(new MyMeetingElement(0, 0, 3, roomReservation.getId(), roomReservation.getRoomId(), room.getName(), host.getId(), host.getName(), roomReservation.getTopic(), roomReservation.getScheduledStartTime().getTime(), roomReservation.getScheduledEndTime().getTime(), 0, participants));
        }

        return Result.success(result);
    }


    @GetMapping("/historical_meeting_list")
    public Result<List<MyHistoryMeetingResultVo>> GetHistoricalMeetingList(HttpServletRequest request,
                                                                           @RequestParam("start_time") Long startTime,
                                                                           @RequestParam("end_time") Long endTime) {

        User user = SessionUtil.GetUser(request);
        if (user == null || user.getId() == 0) {
            return Result.error(ErrorEnum.UserNotExistError);
        }
        // 普通历史会议
        List<Meeting> userHistoryMeetingList = null;
        if (startTime != 0 && endTime != 0) {
            userHistoryMeetingList = meetingResultService.getHistoryMeetingByUserId(user.getId(), new Timestamp(startTime), new Timestamp(endTime));
        } else {
            userHistoryMeetingList = meetingResultService.getHistoryMeetingByUserId(user.getId());
        }
        List<MyHistoryMeetingResultVo> result = new ArrayList<>();
        for (Meeting meeting : userHistoryMeetingList) {
            result.add(new MyHistoryMeetingResultVo(meeting.getId(), meeting.getMeetingNumber(), 0, 0, "", meeting.getTopic(), meeting.getStartTime().getTime(), meeting.getEndTime().getTime(), meetingResultService.getParticipantsByMeetingId(meeting.getId()), meeting.getType()));
        }
        // 会议室会议
        List<RoomReservation> userReservationMeetingList = null;
        if (startTime != 0 && endTime != 0) {
            userReservationMeetingList = roomService.getHistoryRoomReservationByUserId(user.getId(), startTime, endTime);
        } else {
            userReservationMeetingList = roomService.getHistoryRoomReservationByUserId(user.getId());
        }

        for (RoomReservation roomReservation : userReservationMeetingList) {
            List<MeetingUser> participants = new ArrayList<>();
            Room room = roomService.retrieveRoomById(roomReservation.getRoomId());
            String[] userIds = roomReservation.getParticipants().split(",");
            for (String userId : userIds) {
                User participant = userService.getUserById(Integer.parseInt(userId));
                participants.add(new MeetingUser(participant.getId(), participant.getName(), ""));
            }
            result.add(new MyHistoryMeetingResultVo(0, 0, roomReservation.getId(), room.getId(), room.getName(), roomReservation.getTopic(), roomReservation.getScheduledStartTime().getTime(), roomReservation.getScheduledEndTime().getTime(), participants, 3));
        }
        return Result.success(result);
    }

    @GetMapping("/meeting_record")
    public Result<MeetingResult> GetMeetingResult(HttpServletRequest request,
                                                  @RequestParam("meeting_id") Integer meetingId) {

        MeetingResult meetingResult = meetingResultService.getMeetingResult(meetingId);
        return Result.success(meetingResult);
    }


}
