package com.rjxy.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingOutMessageVo {
    private int userId;
    private String userName;
    private String time;
    private String msg;
    private final int type = 1;
}
