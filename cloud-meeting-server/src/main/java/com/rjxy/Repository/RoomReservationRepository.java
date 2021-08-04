package com.rjxy.Repository;

import com.rjxy.Entity.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Integer> {

    @Query(value = "SELECT * FROM room_reservation WHERE room_id = ?1 AND ((scheduled_end_time >= ?2 AND scheduled_end_time <= ?3) OR (scheduled_start_time >= ?2 AND scheduled_start_time <= ?3 ))", nativeQuery = true)
    RoomReservation findRoomReservationByRoomIdAndScheduledStartTimeAndScheduledEndTime(Integer roomId, Timestamp scheduledStartTime, Timestamp scheduledEndTime);

    List<RoomReservation> findRoomReservationsByRoomId(Integer roomId);

    List<RoomReservation> findRoomReservationsByHostId(Integer hostId);

    RoomReservation findRoomReservationById(Integer id);
}
