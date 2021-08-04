package com.rjxy.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.websocket.Session;

@AllArgsConstructor
@NoArgsConstructor
@Data
public
class MeetingUserInfo {

    private int userId;

    private String username;

    private Session session;

}
