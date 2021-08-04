package com.rjxy.Repository;

import com.rjxy.Entity.MeetingResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingResultRepository extends JpaRepository<MeetingResult,Integer> {

    MeetingResult findMeetingResultByMeetingId(Integer meetingId);

}
