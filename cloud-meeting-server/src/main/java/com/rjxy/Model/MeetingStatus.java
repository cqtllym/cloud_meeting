package com.rjxy.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rjxy.Enum.MeetingStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingStatus {

    private int status;
    private boolean needpassword;
    private int hostId;

}
