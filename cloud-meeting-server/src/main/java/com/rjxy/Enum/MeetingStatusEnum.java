package com.rjxy.Enum;

import com.rjxy.Model.MeetingStatus;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MeetingStatusEnum {
    NotStarted(100), Holding(200), Finished(300), Expired(500),

    UnKnown(400);

    private final int status;

    public int getStatus() {
        return status;
    }

}

