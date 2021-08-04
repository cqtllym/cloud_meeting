package com.rjxy.Service;

import com.rjxy.Entity.Room;
import com.rjxy.Entity.RoomReservation;

import java.sql.Timestamp;
import java.util.List;

public interface RoomService {

    Room setupRoom(Integer companyId, Integer departmentId, String roomName);

    //查询会议室
    List<Room> getRoomList(Integer companyId, Integer departmentId);//整合方法

    List<Room> getAvailableRoomList(Integer companyId, Integer departmentId);//整合方法

    List<Room> getAllRoomByCompany(Integer companyId);

    List<Room> getAllRoomByDepartment(Integer departmentId);

    List<Room> getAvailableRoomByCompany(Integer companyId);

    List<Room> getAvailableRoomByDepartment(Integer departmentId);


    //会议室信息更改
    Boolean modifyRoom(Integer roomId, String roomName);

    //停启用会议室

    Boolean setRoomActive(Integer roomId);

    Boolean setRoomInactive(Integer roomId);

    //会议室归属权转移

    Boolean giveDepartmentRoom(Integer departmentId, Integer roomId);

    Boolean removeDepartmentRoom(Integer departmentId, Integer roomId);

    //删除会议室

    Boolean deleteRoom(Integer roomId);

    RoomReservation reserveRoom(RoomReservation roomReservation);

    RoomReservation updateRoomReservation(RoomReservation roomReservation);

    RoomReservation retrieveRoomReservation(Integer id);

    boolean cancelReservedRoom(Integer id);

    List<RoomReservation> getRoomReservationListByRoomId(Integer roomId);

    List<RoomReservation> getRoomReservationListByUserId(Integer userId);

    Room retrieveRoomById(Integer roomId);

    List<RoomReservation> getHistoryRoomReservationByUserId(Integer userId);

    List<RoomReservation> getHistoryRoomReservationByUserId(Integer userId, long startTime, long endTime);

    List<RoomReservation> getHoldingRoomReservationByUserId(Integer userId);

    List<RoomReservation> getNotStartedRoomReservationByUserId(Integer userId);
}