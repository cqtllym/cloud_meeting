package com.rjxy.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rjxy.Entity.Meeting;
import com.rjxy.Entity.RoomReservation;
import com.rjxy.Enum.MeetingStatusEnum;
import com.rjxy.Model.MeetingOnOpenBroadcastVo;
import com.rjxy.Model.MeetingOutMessageVo;
import com.rjxy.Model.MeetingUser;
import com.rjxy.Model.MeetingUserInfo;
import com.rjxy.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@ServerEndpoint("/room/{reservation_id}/{user_id}/{user_name}")
public class RoomMeetingController {

    private static RoomService roomService;

    @Autowired
    public void setRoomService(RoomService roomService) {
        RoomMeetingController.roomService = roomService;
    }

    private static final Map<Integer, Map<Integer, MeetingUserInfo>> ReservationIdUserSessionMap = new HashMap<>();

    @OnOpen
    public void OnOpen(Session session,
                       @PathParam("reservation_id") Integer reservationId,
                       @PathParam("user_id") Integer userId,
                       @PathParam("user_name") String userName) throws IOException {

        RoomReservation reservation = roomService.retrieveRoomReservation(reservationId);
        if (reservation == null || reservation.getId() == 0 || new Timestamp(System.currentTimeMillis()).before(reservation.getScheduledStartTime()) || new Timestamp(System.currentTimeMillis()).after(reservation.getScheduledEndTime()))
            return;
        Map<Integer, MeetingUserInfo> userSessionMap = ReservationIdUserSessionMap.get(reservationId);

        if (userSessionMap == null) {
            // 当前会议号不存在用户
            userSessionMap = new HashMap<>();
            userSessionMap.put(userId, new MeetingUserInfo(userId, userName, session));
            ReservationIdUserSessionMap.put(reservationId, userSessionMap);
        } else {
            // 直接在会议号对应的map中存放用户map
            userSessionMap.put(userId, new MeetingUserInfo(userId, userName, session));
        }

        // 广播返回会议室的成员列表
        MeetingOnOpenBroadcastVo meetingOnOpenBroadcastVo = new MeetingOnOpenBroadcastVo(new ArrayList<>());
        for (Integer integer : userSessionMap.keySet()) {
            MeetingUserInfo meetingUserInfo = userSessionMap.get(integer);
            meetingOnOpenBroadcastVo.getUserList().add(new MeetingUser(meetingUserInfo.getUserId(), meetingUserInfo.getUsername(), ""));
        }
        for (Integer integer : userSessionMap.keySet()) {
            userSessionMap.get(integer).getSession().getBasicRemote().sendText(JSONObject.toJSONString(meetingOnOpenBroadcastVo));
        }

    }

    @OnClose
    public void OnClose(Session session,
                        @PathParam("reservation_id") Integer reservationId,
                        @PathParam("user_id") Integer userId) throws IOException {
        RoomReservation reservation = roomService.retrieveRoomReservation(reservationId);
        if (reservation == null || reservation.getId() == 0 || new Timestamp(System.currentTimeMillis()).before(reservation.getScheduledStartTime()) || new Timestamp(System.currentTimeMillis()).after(reservation.getScheduledEndTime()))
            return;
        Map<Integer, MeetingUserInfo> userSessionMap = ReservationIdUserSessionMap.get(reservationId);
        if (userSessionMap == null || userSessionMap.size() == 0) {
            return;
        }
        userSessionMap.remove(userId);
        MeetingOnOpenBroadcastVo meetingOnOpenBroadcastVo = new MeetingOnOpenBroadcastVo(new ArrayList<>());
        for (Integer integer : userSessionMap.keySet()) {
            MeetingUserInfo meetingUserInfo = userSessionMap.get(integer);
            meetingOnOpenBroadcastVo.getUserList().add(new MeetingUser(meetingUserInfo.getUserId(), meetingUserInfo.getUsername(), ""));
        }
        for (Integer integer : userSessionMap.keySet()) {
            userSessionMap.get(integer).getSession().getBasicRemote().sendText(JSONObject.toJSONString(meetingOnOpenBroadcastVo));
        }

        if (isLastLeft(reservation)) {
            // 从map中删除会议室对应的map
            ReservationIdUserSessionMap.remove(reservationId);
        }

    }

    @OnMessage
    public void OnMessage(Session session, String message,
                          @PathParam("reservation_id") Integer reservationId,
                          @PathParam("user_id") Integer userId,
                          @PathParam("user_name") String username) throws IOException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        // 转发message
        Map<Integer, MeetingUserInfo> userInfoMap = ReservationIdUserSessionMap.get(reservationId);
        MeetingOutMessageVo newMessage = new MeetingOutMessageVo(userId, username, simpleDateFormat.format(new Date()), message);
        for (Integer uid : userInfoMap.keySet()) {
            if (userInfoMap.get(uid).getSession().isOpen())
                userInfoMap.get(uid).getSession().getBasicRemote().sendText(JSONObject.toJSONString(newMessage));
        }
    }

    @OnError
    public void OnError(Throwable e) {
        e.printStackTrace();
    }

    private boolean isLastLeft(RoomReservation reservation) {
        Map<Integer, MeetingUserInfo> userInfoMap = ReservationIdUserSessionMap.get(reservation.getId());
        return userInfoMap == null || userInfoMap.size() == 0;
    }
}
