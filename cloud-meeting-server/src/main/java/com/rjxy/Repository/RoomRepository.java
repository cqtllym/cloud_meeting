package com.rjxy.Repository;

import com.rjxy.Entity.Room;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    Room findRoomById(Integer roomId);

    Room findRoomByIdAndDeletedFalse(Integer roomId);

    List<Room> findAllByCompanyIdAndDeletedFalse(Integer companyId);

    List<Room> findAllByDepartmentIdAndDeletedFalse(Integer departmentId);

    List<Room> findAllByCompanyIdAndIsActiveTrueAndStatusFalseAndDeletedFalse(Integer companyId);

    List<Room> findAllByDepartmentIdAndIsActiveTrueAndStatusFalseAndDeletedFalse(Integer departmentId);


}
