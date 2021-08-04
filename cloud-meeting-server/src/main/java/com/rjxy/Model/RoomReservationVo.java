package com.rjxy.Model;

import com.rjxy.Entity.RoomReservation;
import com.rjxy.Service.RoomService;
import com.rjxy.Service.UserService;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Data
@Component
public class RoomReservationVo {


    private Integer id;

    private Integer roomId;

    private String roomName;

    private Integer hostId;

    private String hostName;

    private String topic;

    private long scheduledStartTime;

    private long scheduledEndTime;

    private List<UserVo> participants;

    public void setRoomReservation(RoomReservation rr, UserService userService, RoomService roomService) {

        this.id = rr.getId();
        this.roomId = rr.getRoomId();
        this.roomName = roomService.retrieveRoomById(roomId).getName();
        this.hostId = rr.getHostId();
        this.hostName = userService.getUserById(hostId).getName();
        this.topic = rr.getTopic();
        if (rr.getScheduledStartTime() != null)
            this.scheduledStartTime = rr.getScheduledStartTime().getTime();
        if (rr.getScheduledEndTime() != null)
            this.scheduledEndTime = rr.getScheduledEndTime().getTime();
        String participantsList = rr.getParticipants();
        if (participantsList.equals(""))
            return;

        String[] temp = participantsList.split(",");

        participants = new LinkedList<>();

        for (String s : temp) {
            Integer userId = Integer.parseInt(s);
            UserVo uv = new UserVo();
            uv.setUserVo(userService.getUserById(userId));
            participants.add(uv);
        }

    }
}
