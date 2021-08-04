package com.rjxy.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingUserTimeRecord {
    private Timestamp enterTime;
    private Timestamp leftTime;
}
