package com.rjxy.Controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rjxy.Entity.Meeting;
import com.rjxy.Entity.MeetingResult;
import com.rjxy.Entity.MeetingResultUserRelation;
import com.rjxy.Entity.MeetingUserRelation;
import com.rjxy.Enum.MeetingStatusEnum;
import com.rjxy.Exception.MeetingNotExistException;
import com.rjxy.Model.MeetingOnOpenBroadcastVo;
import com.rjxy.Model.MeetingOutMessageVo;
import com.rjxy.Model.MeetingUser;
import com.rjxy.Model.MeetingUserInfo;
import com.rjxy.Service.MeetingResultService;
import com.rjxy.Service.MeetingService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@ServerEndpoint("/meeting/{meeting_number}/{user_id}/{user_name}")
public class MeetingController {

    private static StringRedisTemplate stringRedisTemplate;

    private static MeetingService meetingService;

    private static MeetingResultService meetingResultService;

    @Autowired
    public void setMeetingService(MeetingService meetingService) {
        MeetingController.meetingService = meetingService;
    }

    @Autowired
    public void setMeetingResultService(MeetingResultService meetingResultService) {
        MeetingController.meetingResultService = meetingResultService;
    }

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        MeetingController.stringRedisTemplate = stringRedisTemplate;
    }

    // map<会议号，map<user,userSession>>
    private static final Map<Integer, Map<Integer, MeetingUserInfo>> MeetingNumberUserSessionMap = new HashMap<>();

    // redisKey前缀
    private static final String MeetingRecordPrefix = "meeting_record_";
    private static final String MeetingUserSetPrefix = "meeting_user_set_";
    private static final String MeetingUserTimeRecordPrefix = "meeting_user_time_record_";
    private static final String MeetingUserIntimeSetPrefix = "meeting_user_intime_set_";

    @OnOpen
    public void OnOpen(Session session,
                       @PathParam("meeting_number") Integer meetingNumber,
                       @PathParam("user_id") Integer userId,
                       @PathParam("user_name") String username) throws IOException, MeetingNotExistException {

        System.out.println("连接ing");
        Meeting meetingByMeetingNumber = meetingService.getMeetingByMeetingNumber(meetingNumber);
        if (meetingByMeetingNumber == null || meetingByMeetingNumber.getId() == 0 || (meetingByMeetingNumber.getStatus() != MeetingStatusEnum.Holding.getStatus() && meetingByMeetingNumber.getStatus() != MeetingStatusEnum.NotStarted.getStatus())) {
            return;
        }
        Map<Integer, MeetingUserInfo> userSessionMap = MeetingNumberUserSessionMap.get(meetingNumber);
        if (userSessionMap == null) {
            // 当前会议号不存在用户
            // 如果会议类型为预约会议 则更改会议状态为进行中
            if (meetingByMeetingNumber.getType() == 2) {
                meetingService.modifyMeetingStatus(meetingByMeetingNumber.getMeetingNumber(), MeetingStatusEnum.Holding);
                meetingService.recordMeetingStartTime(meetingNumber, new Timestamp(System.currentTimeMillis()));
            }
            userSessionMap = new HashMap<>();
            userSessionMap.put(userId, new MeetingUserInfo(userId, username, session));
            MeetingNumberUserSessionMap.put(meetingNumber, userSessionMap);
        } else {
            // 直接在会议号对应的map中存放用户map
            userSessionMap.put(userId, new MeetingUserInfo(userId, username, session));
        }
        //  redis中记录进入会议的时间
        stringRedisTemplate.boundListOps(MeetingUserTimeRecordPrefix + meetingByMeetingNumber.getMeetingNumber() + "_" + userId).rightPush(String.valueOf(System.currentTimeMillis()));
        // redis中记录参会人员set
        stringRedisTemplate.boundSetOps(MeetingUserSetPrefix + meetingByMeetingNumber.getMeetingNumber()).add(userId.toString());
        // redis中记录实时参会人set
        stringRedisTemplate.boundSetOps(MeetingUserIntimeSetPrefix + meetingByMeetingNumber.getMeetingNumber()).add(userId.toString());

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
    public void OnClose(Session session, @PathParam("user_id") Integer userId, @PathParam("meeting_number") Integer meetingNumber) throws IOException, MeetingNotExistException {
        System.out.println("断开连接中");

        Meeting meeting = meetingService.getMeetingByMeetingNumber(meetingNumber);
        if (meeting == null || meeting.getId() == 0 || meeting.getStatus() != MeetingStatusEnum.Holding.getStatus()) {
            return;
        }

        Map<Integer, MeetingUserInfo> userMap = MeetingController.MeetingNumberUserSessionMap.get(meetingNumber);
        if (userMap == null || userMap.size() == 0) {
            return;
        }
        // 先从usermap中删除用户对应的session
        userMap.remove(userId);
        // 广播返回会议室的成员列表
        MeetingOnOpenBroadcastVo meetingOnOpenBroadcastVo = new MeetingOnOpenBroadcastVo(new ArrayList<>());
        for (Integer integer : userMap.keySet()) {
            MeetingUserInfo meetingUserInfo = userMap.get(integer);
            meetingOnOpenBroadcastVo.getUserList().add(new MeetingUser(meetingUserInfo.getUserId(), meetingUserInfo.getUsername(), ""));
        }
        for (Integer integer : userMap.keySet()) {
            userMap.get(integer).getSession().getBasicRemote().sendText(JSONObject.toJSONString(meetingOnOpenBroadcastVo));
        }
        // redis中记录离开时间
        stringRedisTemplate.boundListOps(MeetingUserTimeRecordPrefix + meeting.getMeetingNumber() + "_" + userId).rightPush(String.valueOf(System.currentTimeMillis()));
        // redis修改实时参会人列表
        stringRedisTemplate.boundSetOps(MeetingUserIntimeSetPrefix + meeting.getMeetingNumber()).remove(userId.toString());
        // 判断是否是最后一个退出会议室的用户,相当于是会议结束
        if (isLastLeft(meeting)) {
            // 从map中删除会议室对应的map
            MeetingController.MeetingNumberUserSessionMap.remove(meetingNumber);
            // 设置结束时间
            meetingService.recordMeetingEndTime(meetingNumber, new Timestamp(System.currentTimeMillis()));

            if (meeting.getType() == 1) {
                // 立即会议
                // 设置会议状态为已结束
                meetingService.modifyMeetingStatus(meetingNumber, MeetingStatusEnum.Finished);
                // 删除会议用户表中的记录
                meetingService.deleteMeetingUserRelation(meeting.getId());
            } else {
                // 预约会议
                if (meeting.getScheduledEndTime().before(new Timestamp(System.currentTimeMillis()))) {
                    // 未到预约结束的时间
                    // 设置会议状态未开始
                    meetingService.modifyMeetingStatus(meetingNumber, MeetingStatusEnum.NotStarted);
                } else {
                    // 已到预约结束的时间
                    // 设置会议状态已结束
                    meetingService.modifyMeetingStatus(meetingNumber, MeetingStatusEnum.Finished);
                    // 删除会议用户表中的记录
                    meetingService.deleteMeetingUserRelation(meeting.getId());
                }
            }
            // 保存会议记录
            saveMeetingRecord(meeting);
        }

    }

    @OnMessage
    public void OnMessage(Session session, String message,
                          @PathParam("meeting_number") Integer meetingNumber,
                          @PathParam("user_id") Integer userId,
                          @PathParam("user_name") String username) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        // 转发message
        Map<Integer, MeetingUserInfo> userInfoMap = MeetingController.MeetingNumberUserSessionMap.get(meetingNumber);
        MeetingOutMessageVo newMessage = new MeetingOutMessageVo(userId, username, simpleDateFormat.format(new Date()), message);
        for (Integer uid : userInfoMap.keySet()) {
            if (userInfoMap.get(uid).getSession().isOpen())
                userInfoMap.get(uid).getSession().getBasicRemote().sendText(JSONObject.toJSONString(newMessage));
        }
        // 保存信息到redis，用于导出会议记录
        stringRedisTemplate.boundListOps(MeetingRecordPrefix + meetingNumber).rightPush(JSON.toJSONString(newMessage));

    }

    @OnError
    public void OnError(Throwable e) {
        e.printStackTrace();
    }

    /**
     * 判断当前会议室是否是最后一个人离开
     *
     * @return true if isLastLeft,else false
     */
    private boolean isLastLeft(Meeting meeting) {
        Map<Integer, MeetingUserInfo> userInfoMap = MeetingNumberUserSessionMap.get(meeting.getMeetingNumber());
        return userInfoMap == null || userInfoMap.size() == 0;
    }

    /**
     * 会议室结束后保存会议的记录
     *
     * @param meeting 会议entity
     */
    private void saveMeetingRecord(Meeting meeting) {
        // 获取会议聊天记录
        List<String> range = stringRedisTemplate.boundListOps(MeetingRecordPrefix + meeting.getMeetingNumber()).range(0, -1);
        String s = JSON.toJSONString(range);
        MeetingResult meetingResult = meetingResultService.getMeetingResult(meeting.getId());
        if (meetingResult == null) {
            meetingResult = new MeetingResult(0, meeting.getId(), s);
        }
        meetingResult.setResult(s);
        meetingResultService.setMeetingResult(meetingResult);
        // 获取参会人set
        Set<String> members = stringRedisTemplate.boundSetOps(MeetingUserSetPrefix + meeting.getMeetingNumber()).members();
        if (members == null || members.size() == 0) {
            return;
        }
        for (String member : members) {
            int uid = Integer.parseInt(member);
            // 获取参会人进出会议的时间记录
            List<String> userTimeRecord = stringRedisTemplate.boundListOps(MeetingUserTimeRecordPrefix + meeting.getMeetingNumber() + "_" + uid).range(0, -1);
            meetingResultService.setMeetingResultUserRelation(new MeetingResultUserRelation(0, uid, meeting.getId(), JSON.toJSONString(userTimeRecord)));
        }

    }

}



