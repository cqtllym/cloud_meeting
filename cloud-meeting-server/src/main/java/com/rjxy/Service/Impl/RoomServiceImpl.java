package com.rjxy.Service.Impl;

import com.rjxy.Entity.Company;
import com.rjxy.Entity.Department;
import com.rjxy.Entity.Room;
import com.rjxy.Entity.RoomReservation;
import com.rjxy.Repository.*;
import com.rjxy.Service.RoomService;
import com.rjxy.Util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    RoomReservationRepository roomReservationRepository;


    @Override
    public Room setupRoom(Integer companyId, Integer departmentId, String roomName) {
        Room room = new Room();
        room.setCompanyId(companyId);
        room.setDepartmentId(departmentId);
        room.setName(roomName);
        roomRepository.save(room);
        roomRepository.flush();
        return room;
    }

    @Override
    public List<Room> getRoomList(Integer companyId, Integer departmentId) {
        Company company = companyRepository.findCompanyByIdAndDeletedFalse(companyId);
        if (company == null)
            return null;
        if (departmentId != null) {
            Department department = departmentRepository.findDepartmentById(departmentId);
            if (department.getCompanyId().equals(companyId))
                return getAllRoomByDepartment(departmentId);
            else
                return null;
        } else {
            return getAllRoomByCompany(companyId);
        }
    }

    @Override
    public List<Room> getAvailableRoomList(Integer companyId, Integer departmentId) {
        Company company = companyRepository.findCompanyByIdAndDeletedFalse(companyId);
        if (company == null)
            return null;
        if (departmentId != null) {
            Department department = departmentRepository.findDepartmentById(departmentId);
            if (department.getCompanyId().equals(companyId))
                return getAvailableRoomByDepartment(departmentId);
            else
                return null;
        } else {
            return getAvailableRoomByCompany(companyId);
        }
    }

    @Override
    public List<Room> getAllRoomByCompany(Integer companyId) {
        return roomRepository.findAllByCompanyIdAndDeletedFalse(companyId);
    }

    @Override
    public List<Room> getAllRoomByDepartment(Integer departmentId) {
        return roomRepository.findAllByDepartmentIdAndDeletedFalse(departmentId);
    }

    @Override
    public List<Room> getAvailableRoomByCompany(Integer companyId) {
        return roomRepository.findAllByCompanyIdAndIsActiveTrueAndStatusFalseAndDeletedFalse(companyId);
    }

    @Override
    public List<Room> getAvailableRoomByDepartment(Integer departmentId) {
        return roomRepository.findAllByDepartmentIdAndIsActiveTrueAndStatusFalseAndDeletedFalse(departmentId);
    }

    @Override
    public Boolean modifyRoom(Integer roomId, String roomName) {
        Room room = roomRepository.findRoomByIdAndDeletedFalse(roomId);
        if (room != null) {
            room.setName(roomName);
            roomRepository.saveAndFlush(room);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean setRoomActive(Integer roomId) {
        Room room = roomRepository.findRoomByIdAndDeletedFalse(roomId);
        if (room != null) {
            room.setIsActive(true);
            roomRepository.saveAndFlush(room);
            return true;
        } else return false;

    }

    @Override
    public Boolean setRoomInactive(Integer roomId) {
        Room room = roomRepository.findRoomByIdAndDeletedFalse(roomId);
        if (room != null) {
            room.setIsActive(false);
            roomRepository.saveAndFlush(room);
            return true;
        } else return false;
    }

    @Override
    public Boolean giveDepartmentRoom(Integer departmentId, Integer roomId) {
        return false;
    }

    @Override
    public Boolean removeDepartmentRoom(Integer departmentId, Integer roomId) {
        return false;
    }

    @Override
    public Boolean deleteRoom(Integer roomId) {
        Room room = roomRepository.findRoomByIdAndDeletedFalse(roomId);
        if (room != null && !room.getStatus()) {
            room.setDeleted(true);
            roomRepository.save(room);
            return true;
        } else return false;
    }

    @Override
    public RoomReservation reserveRoom(RoomReservation roomReservation) {
        RoomReservation rr = roomReservationRepository.findRoomReservationByRoomIdAndScheduledStartTimeAndScheduledEndTime(roomReservation.getRoomId(), roomReservation.getScheduledStartTime(), roomReservation.getScheduledEndTime());
        if (rr == null) {
            return roomReservationRepository.save(roomReservation);
        }
        return null;
    }

    @Override
    public RoomReservation updateRoomReservation(RoomReservation roomReservation) {
        RoomReservation rr = roomReservationRepository.findRoomReservationByRoomIdAndScheduledStartTimeAndScheduledEndTime(roomReservation.getRoomId(), roomReservation.getScheduledStartTime(), roomReservation.getScheduledEndTime());
        if (rr == null) {
            return roomReservationRepository.save(roomReservation);
        }
        return null;
    }


    @Override
    public RoomReservation retrieveRoomReservation(Integer id) {
        return roomReservationRepository.findRoomReservationById(id);
    }

    @Override
    public boolean cancelReservedRoom(Integer id) {
        roomReservationRepository.deleteById(id);
        return true;
    }

    @Override
    public List<RoomReservation> getRoomReservationListByRoomId(Integer roomId) {
        return roomReservationRepository.findRoomReservationsByRoomId(roomId);
    }

    @Override
    public List<RoomReservation> getRoomReservationListByUserId(Integer userId) {
        return roomReservationRepository.findRoomReservationsByHostId(userId);
    }

    @Override
    public Room retrieveRoomById(Integer roomId) {
        return roomRepository.findRoomById(roomId);
    }

    @Override
    public List<RoomReservation> getHistoryRoomReservationByUserId(Integer userId) {
        List<RoomReservation> all = roomReservationRepository.findAll();
        List<RoomReservation> result = new ArrayList<>();
        for (RoomReservation roomReservation : all) {
            String[] userIds = roomReservation.getParticipants().split(",");
            if (roomReservation.getHostId().equals(userId) || ToolUtil.InStringArray(userIds, userId.toString())) {
                if (roomReservation.getScheduledEndTime().getTime() < System.currentTimeMillis()) {
                    result.add(roomReservation);
                }
            }
        }
        return result;
    }

    @Override
    public List<RoomReservation> getHistoryRoomReservationByUserId(Integer userId, long startTime, long endTime) {
        List<RoomReservation> all = roomReservationRepository.findAll();
        List<RoomReservation> result = new ArrayList<>();
        for (RoomReservation roomReservation : all) {
            String[] userIds = roomReservation.getParticipants().split(",");
            if (roomReservation.getHostId().equals(userId) || ToolUtil.InStringArray(userIds, userId.toString())) {
                if (roomReservation.getScheduledEndTime().getTime() < System.currentTimeMillis() && roomReservation.getScheduledEndTime().getTime() > startTime && roomReservation.getScheduledEndTime().getTime() < endTime) {
                    result.add(roomReservation);
                }
            }
        }
        return result;
    }

    @Override
    public List<RoomReservation> getHoldingRoomReservationByUserId(Integer userId) {
        List<RoomReservation> all = roomReservationRepository.findAll();
        List<RoomReservation> result = new ArrayList<>();
        for (RoomReservation roomReservation : all) {
            String[] userIds = roomReservation.getParticipants().split(",");
            if (roomReservation.getHostId().equals(userId) || ToolUtil.InStringArray(userIds, userId.toString())) {
                if ((roomReservation.getScheduledStartTime().getTime() < System.currentTimeMillis()) && roomReservation.getScheduledEndTime().getTime() > System.currentTimeMillis()) {
                    result.add(roomReservation);
                }
            }
        }
        return result;
    }

    @Override
    public List<RoomReservation> getNotStartedRoomReservationByUserId(Integer userId) {
        List<RoomReservation> all = roomReservationRepository.findAll();
        List<RoomReservation> result = new ArrayList<>();
        for (RoomReservation roomReservation : all) {
            String[] userIds = roomReservation.getParticipants().split(",");
            if (roomReservation.getHostId().equals(userId) || ToolUtil.InStringArray(userIds, userId.toString())) {
                if (roomReservation.getScheduledStartTime().getTime() > System.currentTimeMillis()) {
                    result.add(roomReservation);
                }
            }
        }
        return result;
    }

    public List<Room> getAvailableRoomList(Integer companyId, Timestamp scheduledStartTime, Timestamp scheduledEndTime) {
        List<Room> lr = roomRepository.findAllByCompanyIdAndIsActiveTrueAndStatusFalseAndDeletedFalse(companyId);
        lr.removeIf(room -> roomReservationRepository.findRoomReservationByRoomIdAndScheduledStartTimeAndScheduledEndTime(room.getId(), scheduledStartTime, scheduledEndTime) != null);
        return lr;
    }
}