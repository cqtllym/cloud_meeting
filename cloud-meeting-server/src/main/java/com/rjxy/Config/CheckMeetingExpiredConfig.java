package com.rjxy.Config;

import com.rjxy.Controller.MeetingController;
import com.rjxy.Entity.Meeting;
import com.rjxy.Enum.MeetingStatusEnum;
import com.rjxy.Exception.MeetingNotExistException;
import com.rjxy.Service.Impl.MeetingServiceImpl;
import com.rjxy.Service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CheckMeetingExpiredConfig {

    @Autowired
    private MeetingServiceImpl meetingService;

    @Scheduled(cron = "0 0 0 * * ? ")
    public void checkMeetingExpired() throws MeetingNotExistException {
        for (Meeting meeting : meetingService.getExpiredMeetingList()) {
            if (meeting.getStartTime() != null)
                meetingService.modifyMeetingStatus(meeting.getMeetingNumber(), MeetingStatusEnum.Finished);
            else
                meetingService.modifyMeetingStatus(meeting.getMeetingNumber(), MeetingStatusEnum.Expired);
        }
    }

}
