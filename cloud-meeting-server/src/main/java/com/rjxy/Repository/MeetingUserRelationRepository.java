package com.rjxy.Repository;

import com.rjxy.Entity.MeetingUserRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingUserRelationRepository extends JpaRepository<MeetingUserRelation,Integer> {
    List<MeetingUserRelation> findByUserId(Integer userId);

    List<MeetingUserRelation> findByMeetingId(Integer meetingId);

    void deleteByMeetingId(Integer id);

    MeetingUserRelation findByMeetingIdAndUserId(Integer meetingId, Integer userId);
}
