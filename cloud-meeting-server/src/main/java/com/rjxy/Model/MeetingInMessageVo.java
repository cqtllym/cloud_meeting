package com.rjxy.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MeetingInMessageVo {
    private int type;
    private int userId;
    private String message;
}
