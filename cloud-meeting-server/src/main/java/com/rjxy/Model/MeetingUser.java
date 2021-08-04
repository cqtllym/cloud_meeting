package com.rjxy.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MeetingUser {
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("username")
    private String userName;
    @JsonProperty("avatar")
    private String avatar;
}

