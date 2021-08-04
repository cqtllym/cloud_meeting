package com.rjxy.Repository;

import com.rjxy.Entity.MeetingResultUserRelation;
import com.rjxy.Entity.MeetingUserRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingResultUserRelationRepository extends JpaRepository<MeetingResultUserRelation, Integer> {
    List<MeetingResultUserRelation> findByMeetingId(Integer meetingId);
}
