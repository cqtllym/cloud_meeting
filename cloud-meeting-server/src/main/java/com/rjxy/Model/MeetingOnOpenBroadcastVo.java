package com.rjxy.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingOnOpenBroadcastVo {
    private List<MeetingUser> userList;
    private final int type = 2;
}
