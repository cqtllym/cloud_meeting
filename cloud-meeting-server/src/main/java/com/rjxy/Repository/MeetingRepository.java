package com.rjxy.Repository;

import com.rjxy.Entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;


public interface MeetingRepository extends JpaRepository<Meeting,Integer> {

    Meeting findMeetingById(Integer meetingId);

    @Query(value = "select * from meeting as m where m.meeting_number = ?1 and (m.status = 100 or m.status = 200)", nativeQuery = true)
    Meeting findByMeetingNumberAndStatusStartOrHolding(Integer meetingNumber);

    @Query(value = "select * from meeting as m left join meeting_result_user_relation as r on m.id = r.meeting_id where m.status=?1 and r.user_id=?2 and m.start_time > ?3 and m.start_time < ?4", nativeQuery = true)
    List<Meeting> findMeetingByStatusAndUserIdAndStartTimeAndEndTime(Integer Status, Integer userId, Timestamp startTime, Timestamp endTime);

    Meeting findMeetingByIdAndStatus(Integer meetingId, int status);

    @Query(value = "select * from meeting as m where m.status = 100 and m.scheduled_end_time < ?1", nativeQuery = true)
    List<Meeting> findMeetingsByStatusNotStartedAndScheduledEndTimeBeforeNow(Timestamp now);

    @Query(value = "select * from meeting as m left join meeting_result_user_relation as r on m.id = r.meeting_id where m.status=?1 and r.user_id=?2", nativeQuery = true)
    List<Meeting> findMeetingByStatusAndUserId(int status, int userId);
}
