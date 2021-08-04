package com.rjxy.Util;

import com.rjxy.Entity.User;
import com.rjxy.Exception.UserNotExistException;
import com.rjxy.Repository.UserRepository;
import com.rjxy.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Component
public class SessionUtil {


    private static UserService userService;

    private static UserRepository userRepository;

    @Autowired
    private void setUserSerService(UserService userService) {
        SessionUtil.userService = userService;
    }

    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        SessionUtil.userRepository = userRepository;
    }

    public static User GetUser(HttpServletRequest request) {
//        return userService.getUserById(910);
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        Object userId = session.getAttribute("user_id");
        if (userId != null) {
            return userService.getUserById((Integer) userId);
        }
        return null;
    }

    public static void setUserId(HttpServletRequest request, HttpServletResponse response, Integer userId) throws UserNotExistException {
        if (userId == null) {
            return;
        }
        User user = userService.getUserById(userId);
        if (user == null || user.getId() == 0) {
            throw new UserNotExistException();
        }
        HttpSession session = request.getSession(true);

        session.setAttribute("user_id", userId);
        session.setMaxInactiveInterval(60 * 60 * 12 * 2); // 过期时间为2天
        // 去掉cookie的httpOnly 让js可以获取cookie
        String header = response.getHeader("Set-Cookie");
        int index;
        if (header != null && (index = header.indexOf("HttpOnly")) > 0) {
            header = header.substring(0, index - 1);
            response.setHeader("Set-Cookie", header);
        }
    }
}
