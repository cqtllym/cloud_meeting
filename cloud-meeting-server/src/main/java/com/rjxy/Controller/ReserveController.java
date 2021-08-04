package com.rjxy.Controller;

import com.rjxy.Entity.*;
import com.rjxy.Enum.ErrorEnum;
import com.rjxy.Enum.MeetingStatusEnum;
import com.rjxy.Exception.MeetingNotExistException;
import com.rjxy.Model.MeetingStatus;
import com.rjxy.Model.MeetingVo;
import com.rjxy.Model.PasswordCheck;
import com.rjxy.Model.RoomReservationVo;
import com.rjxy.Results.Result;
import com.rjxy.Service.Impl.RoomServiceImpl;
import com.rjxy.Service.Impl.UserServiceImpl;
import com.rjxy.Service.MeetingService;
import com.rjxy.Util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import static com.rjxy.Enum.ErrorEnum.*;

@CrossOrigin
@RestController
@RequestMapping("/reserve")
public class ReserveController {

    @Autowired
    MeetingService meetingService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    RoomServiceImpl roomService;

    /**
     * 发起临时会议 返回会议信息
     *
     * @param hostId 会议发起人的id
     * @param topic  会议主题
     * @return 返回会议的信息
     */
    @PostMapping("/instant_meeting")
    public Result<Meeting> createInstantMeeting(@RequestParam Integer hostId,
                                                @RequestParam String topic,
                                                @RequestParam Integer requirePwd,
                                                @RequestParam(required = false) Integer password) {
        Meeting meeting = meetingService.createInstantMeeting(hostId, topic, requirePwd == 1, password);
        if (meeting != null)
            return Result.success(meeting);
        return Result.error(CreateMeetingError);

    }

    @PutMapping("/reserved_meeting")
    public Result<MeetingVo> createReservedMeeting(@RequestParam Integer hostId,
                                                   @RequestParam String topic,
                                                   @RequestParam long scheduledStartTime,
                                                   @RequestParam long scheduledEndTime,
                                                   @RequestParam Boolean requiredPassword,
                                                   @RequestParam(required = false) Integer password) {
        Meeting meeting = meetingService.createReservedMeeting(hostId, topic, new Timestamp(scheduledStartTime), new Timestamp(scheduledEndTime), requiredPassword, password);
        // 先查询是否已经插入
        MeetingUserRelation userRelation = meetingService.getMeetingUserRelationByMeetingIdAndUserId(meeting.getId(), meeting.getHostId());
        User host = userService.getUserById(meeting.getHostId());
        if (userRelation != null && userRelation.getId() != 0) {
            return Result.success(MeetingVo.setMeetingVo(meeting, host));
        }else {
            meetingService.addMeetingUserRelation(meeting.getId(), meeting.getHostId());
        }
        return Result.success(MeetingVo.setMeetingVo(meeting, host));
    }

    @DeleteMapping("/reserved_meeting")
    public Result<String> deleteReservedMeeting(@RequestParam Integer meetingId,
                                                @RequestParam Integer userId) {
        if (meetingService.deleteReservedMeeting(meetingId, userId))
            return Result.success("删除会议成功");
        return Result.error(DeleteMeetingError);
    }

    @PostMapping("/reserved_meeting")
    public Result<MeetingVo> updateReservedMeeting(@RequestParam Integer id,
                                                   @RequestParam String topic,
                                                   @RequestParam long scheduledStartTime,
                                                   @RequestParam long scheduledEndTime,
                                                   @RequestParam Boolean requiredPassword,
                                                   @RequestParam(required = false) Integer password) {
        Meeting meeting = meetingService.retrieveReservedMeeting(id);
        if (meeting.getStatus() == MeetingStatusEnum.NotStarted.getStatus()) {
            meeting = meetingService.updateReservedMeeting(id, topic, new Timestamp(scheduledStartTime), new Timestamp(scheduledEndTime), requiredPassword, password);
            User host = userService.getUserById(meeting.getHostId());
            return Result.success(MeetingVo.setMeetingVo(meeting, host));
        }
        return Result.error(UpdateMeetingError);
    }

    @GetMapping("/reserved_meeting")
    public Result<MeetingVo> retrieveReservedMeeting(@RequestParam Integer meetingId) {
        Meeting meeting = meetingService.retrieveReservedMeeting(meetingId);
        User host = userService.getUserById(meeting.getHostId());
        return Result.success(MeetingVo.setMeetingVo(meeting, host));
    }

    @GetMapping("/get_meetingstatus")
    public Result<MeetingStatus> getMeetingStatus(@RequestParam Integer meetingNumber) throws MeetingNotExistException {
        Meeting meetingByMeetingNumber = meetingService.getMeetingByMeetingNumber(meetingNumber);
        if (meetingByMeetingNumber == null || meetingByMeetingNumber.getId() == 0) {
            return Result.error(ErrorEnum.MeetingNumberExpired);
        }
        if (meetingByMeetingNumber.getType() == 2 && meetingByMeetingNumber.getStatus() == MeetingStatusEnum.NotStarted.getStatus() && meetingByMeetingNumber.getScheduledEndTime().before(new Timestamp(System.currentTimeMillis()))){
            meetingService.modifyMeetingStatus(meetingNumber, MeetingStatusEnum.Expired);
            meetingByMeetingNumber = meetingService.getMeetingByMeetingNumber(meetingNumber);
        }
        MeetingStatus meetingStatus = new MeetingStatus();
        meetingStatus.setStatus(meetingByMeetingNumber.getStatus());
        if (meetingByMeetingNumber.getRequirePassword()) {
            meetingStatus.setNeedpassword(true);
        }
        meetingStatus.setHostId(meetingByMeetingNumber.getHostId());
        return Result.success(meetingStatus);
    }

    @GetMapping("/confirm_meetingpassword")
    public Result<PasswordCheck> confirmPassword(@RequestParam Integer meetingNumber, @RequestParam Integer meetingPassword) {
        return Result.success(new PasswordCheck(meetingService.checkPassword(meetingNumber, meetingPassword)));
    }

    @PutMapping("/add_meeting")
    public Result<PasswordCheck> addMeeting(HttpServletRequest request,
                                            @RequestParam Integer meetingNumber,
                                            @RequestParam boolean requirePassword,
                                            @RequestParam Integer meetingPassword) {
        User user = SessionUtil.GetUser(request);
        if (user == null || user.getId() == 0) {
            return Result.error(ErrorEnum.UserNotExistError);
        }
        Meeting meetingByMeetingNumber = meetingService.getMeetingByMeetingNumber(meetingNumber);
        if (meetingByMeetingNumber == null || meetingByMeetingNumber.getId() == 0 || meetingByMeetingNumber.getStatus() != MeetingStatusEnum.NotStarted.getStatus()) {
            return Result.error(MeetingNumberExpired);
        }

        // 先查询是否已经插入
        MeetingUserRelation userRelation = meetingService.getMeetingUserRelationByMeetingIdAndUserId(meetingByMeetingNumber.getId(), user.getId());
        if (userRelation != null && userRelation.getId() != 0) {
            return Result.success(new PasswordCheck(true));
        }
        if (!requirePassword) {
            meetingService.addMeetingUserRelation(meetingByMeetingNumber.getId(), user.getId());
            return Result.success(new PasswordCheck(true));
        }
        boolean right = meetingService.checkPassword(meetingNumber, meetingPassword);
        if (!right) {
            return Result.success(new PasswordCheck(false));
        }
        meetingService.addMeetingUserRelation(meetingByMeetingNumber.getId(), user.getId());
        return Result.success(new PasswordCheck(true));
    }

    @PutMapping("/room_reservation")
    public Result<RoomReservationVo> createRoomReservation(@RequestParam Integer roomId,
                                                           @RequestParam Integer hostId,
                                                           @RequestParam String topic,
                                                           @RequestParam long scheduledStartTime,
                                                           @RequestParam long scheduledEndTime,
                                                           @RequestParam String participants) {
        RoomReservation reservation = new RoomReservation();
        reservation.setRoomId(roomId);
        reservation.setHostId(hostId);
        reservation.setTopic(topic);
        reservation.setScheduledStartTime(new Timestamp(scheduledStartTime));
        reservation.setScheduledEndTime(new Timestamp(scheduledEndTime));
        reservation.setParticipants(participants);
        RoomReservation result = roomService.reserveRoom(reservation);
        if (result != null) {
            RoomReservationVo rrv = new RoomReservationVo();
            rrv.setRoomReservation(result, userService, roomService);
            return Result.success(rrv);
        }
        return Result.error(RoomOccupiedError);
    }

    @PostMapping("/room_reservation")
    public Result<RoomReservationVo> updateRoomReservation(@RequestParam Integer id,
                                                           @RequestParam String topic,
                                                           @RequestParam long scheduledStartTime,
                                                           @RequestParam long scheduledEndTime,
                                                           @RequestParam String participants) {
        RoomReservation reservation = roomService.retrieveRoomReservation(id);
        if (reservation == null) {
            return Result.error(RoomReservationNotExistError);
        }
        if (reservation.getScheduledStartTime().before(new Timestamp(System.currentTimeMillis()))) {
            return Result.error(RoomReservationHasStartedError);
        }
        reservation.setTopic(topic);
        reservation.setScheduledStartTime(new Timestamp(scheduledStartTime));
        reservation.setScheduledEndTime(new Timestamp(scheduledEndTime));
        reservation.setParticipants(participants);
        RoomReservation result = roomService.updateRoomReservation(reservation);
        if (result != null) {
            RoomReservationVo rrv = new RoomReservationVo();
            rrv.setRoomReservation(result, userService, roomService);
            return Result.success(rrv);
        }
        return Result.error(RoomOccupiedError);
    }

    @DeleteMapping("/room_reservation")
    public Result<String> deleteRoomReservation(@RequestParam Integer id) {
        RoomReservation reservation = roomService.retrieveRoomReservation(id);
        try {
            if (reservation.getScheduledStartTime().before(new Timestamp(System.currentTimeMillis()))) {
                return Result.error(RoomReservationHasStartedError);
            }
            if (roomService.cancelReservedRoom(id))
                return Result.success("取消会议室预约成功");
            return Result.error(RoomReservationCancelFailedError);
        } catch (Exception e) {
            return Result.error(RoomReservationNotExistError);
        }
    }

    @GetMapping("/room_reservation")
    public Result<RoomReservationVo> retrieveRoomReservation(@RequestParam Integer id) {
        try {
            RoomReservation reservation = roomService.retrieveRoomReservation(id);
            RoomReservationVo rrv = new RoomReservationVo();
            rrv.setRoomReservation(reservation, userService, roomService);
            return Result.success(rrv);
        } catch (Exception e) {
            return Result.error(RoomReservationNotExistError);
        }
    }

    @GetMapping("/room_reservation_list")
    public Result<List<RoomReservationVo>> retrieveRoomReservationByRoom(@RequestParam Integer roomId) {
        List<RoomReservation> lrr = roomService.getRoomReservationListByRoomId(roomId);
        List<RoomReservationVo> lrrv = new LinkedList<>();
        for (RoomReservation reservation : lrr) {
            RoomReservationVo rrv = new RoomReservationVo();
            rrv.setRoomReservation(reservation, userService, roomService);
            lrrv.add(rrv);
        }
        return Result.success(lrrv);
    }

    @GetMapping("/my_reserved_room_list")
    public Result<List<RoomReservationVo>> retrieveRoomReservationByUser(HttpServletRequest request) {
        User user = SessionUtil.GetUser(request);
        if (user == null || user.getId() == 0) {
            return Result.error(UserNotExistError);
        }
        List<RoomReservation> lrr = roomService.getRoomReservationListByUserId(user.getId());
        List<RoomReservationVo> lrrv = new LinkedList<>();
        for (RoomReservation reservation : lrr) {
            RoomReservationVo rrv = new RoomReservationVo();
            rrv.setRoomReservation(reservation, userService, roomService);
            lrrv.add(rrv);
        }
        return Result.success(lrrv);
    }

    @GetMapping("/available_room_list")
    public Result<List<Room>> getAvailableRoomList(@RequestParam Integer companyId,
                                                   @RequestParam long scheduledStartTime,
                                                   @RequestParam long scheduledEndTime) {
        return Result.success(roomService.getAvailableRoomList(companyId, new Timestamp(scheduledStartTime), new Timestamp(scheduledEndTime)));
    }

    @GetMapping("/confirm_room_reservation")
    public Result<PasswordCheck> confirmRoomReservation(HttpServletRequest request, @RequestParam Integer reservationId) {
        User user = SessionUtil.GetUser(request);
        if (user == null || user.getId() == 0) {
            return Result.error(UserNotExistError);
        }
        RoomReservation reservation = roomService.retrieveRoomReservation(reservationId);
        if (new Timestamp(System.currentTimeMillis()).before(reservation.getScheduledStartTime()) || new Timestamp(System.currentTimeMillis()).after(reservation.getScheduledEndTime()))
            return Result.error(InvalidEnterTimeError);
        String[] temp = reservation.getParticipants().split(",");
        for (String s : temp) {
            if (Integer.parseInt(s) == user.getId())
                return Result.success(new PasswordCheck(true));
        }
        return Result.error(InvalidParticipantError);
    }

}
