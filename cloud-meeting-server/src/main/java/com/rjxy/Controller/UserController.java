package com.rjxy.Controller;

import com.rjxy.Entity.Company;
import com.rjxy.Entity.Notice;
import com.rjxy.Entity.User;
import com.rjxy.Enum.ErrorEnum;
import com.rjxy.Exception.UserNotExistException;
import com.rjxy.Model.NoticeVo;
import com.rjxy.Model.UserVo;
import com.rjxy.Results.Result;
import com.rjxy.Service.DepartmentService;
import com.rjxy.Service.Impl.CompanyServiceImpl;
import com.rjxy.Service.Impl.NoticeServiceImpl;
import com.rjxy.Service.Impl.UserServiceImpl;
import com.rjxy.Util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * @author: Joheey
 * Date: 2021/7/6
 * Time: 10:27
 * Description：用户操作
 */

@RestController
@RequestMapping(value = "/user")
public class UserController {


    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private NoticeServiceImpl noticeService;

    @Autowired
    private DepartmentService departmentService;

    //用户注册
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result<UserVo> register(@RequestParam(name = "name") String name,
                                   @RequestParam(name = "password") String password,
                                   @RequestParam(name = "telephone", required = false) String telephone,
                                   @RequestParam(name = "sex", required = false) String sex,
                                   @RequestParam(name = "email", required = false) String email) {
        User user;
        //注册了一个用户
        if (sex.equals("男性")) {
            user = new User(name, password, telephone, 1);
        } else {
            user = new User(name, password, telephone, 0);
        }
        user.setEmail(email);

        if (userService.registerUser(user)) {
            return Result.success(null);
        } else {
            return Result.error(ErrorEnum.UserNameExistError);
        }
    }

    @PostMapping("/delete")
    public Result<String> delete(@RequestParam Integer userId) {
        if (userService.getUserById(userId) == null) {
            return Result.error(ErrorEnum.UserNotExistError);
        }
        userService.deleteUserById(userId);
        return Result.success("注销成功");
    }

    @PutMapping("/modify")
    public Result<UserVo> modify(@RequestParam Integer id,
                                 @RequestParam String name,
                                 @RequestParam String telephone,
                                 @RequestParam String sex,
                                 @RequestParam String email) {
        UserVo uv = new UserVo();
        uv.setUserId(id);
        uv.setUsername(name);
        uv.setTelephone(telephone);
        uv.setSex(sex);
        uv.setEmail(email);
        Boolean status = userService.modifyUserInfo(uv);
        if (status) {
            return Result.success(null);
        } else {
            return Result.error(ErrorEnum.UserModifyError);
        }
    }

    // 用户登录，成功之后向服务端发送生成的sessionId
    @PostMapping("/login")
    public Result<UserVo> login(@RequestParam(name = "name") String name,
                                @RequestParam(name = "password") String password,
                                HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse) {
        User user = userService.getUserByNameAndNotDeleted(name);
        if (user == null)
            return Result.error(ErrorEnum.UserNotExistError);

        if (userService.checkUserPassword(name, password)) {
            UserVo userVo = new UserVo();
            userVo.setUserVo(user, companyService.getCompany(user.getCompanyId()));
            System.out.println(userVo);
            try {
                SessionUtil.setUserId(httpServletRequest, httpServletResponse, user.getId());
            } catch (UserNotExistException e) {
                e.printStackTrace();
            }
            return Result.success(userVo);
        } else {
            return Result.error(ErrorEnum.PasswordWrongError);
        }
    }

    @PutMapping("/password")
    public Result<UserVo> password(@RequestParam Integer userId,
                                   @RequestParam String oldPassword,
                                   @RequestParam String newPassword) {

        if (userService.getUserById(userId) == null)
            return Result.error(ErrorEnum.UserNotExistError);

        if (userService.checkUserPassword(userId, oldPassword)) {
            userService.changePassword(userId, newPassword);
            return Result.success(null);
        } else {
            return Result.error(ErrorEnum.PasswordWrongError);
        }

    }

    @PutMapping("/quit")
    public Result<String> quit(@RequestParam Integer userId,
                               @RequestParam Integer companyId) {
        User user = userService.getUserById(userId);
        if (user == null)
            return Result.error(ErrorEnum.UserNotExistError);

        if (!Objects.equals(user.getCompanyId(), companyId))
            return Result.error(ErrorEnum.UserQuitCompanyError);

        userService.quitCompany(user);

        return Result.success("退出企业成功");

    }

    @GetMapping("/search")
    public Result<List<UserVo>> search(@RequestParam String keyword) {
        List<UserVo> result = new LinkedList<>();
        Map<Integer, User> userMap = userService.searchUserByNameOrTelephone(keyword);
        UserVo uv;
        for (Integer userId : userMap.keySet()) {
            uv = new UserVo();
            User user = userMap.get(userId);
            uv.setUserVo(user, companyService.getCompany(user.getCompanyId()));
            result.add(uv);
        }
        return Result.success(result);
    }

    @GetMapping("/notice_list")
    public Result<List<NoticeVo>> getNoticeList(@RequestParam Integer userId) {
        List<Notice> ln = userService.getNoticeListById(userId);
        List<NoticeVo> lno = new LinkedList<>();
        NoticeVo nv;
        String fromName, toName;
        for (Notice notice : ln) {
            if (notice.getFromType())
                fromName = companyService.getCompany(notice.getFromId()).getName();
            else
                fromName = userService.getUserById(notice.getFromId()).getName();
            if (notice.getToType())
                toName = companyService.getCompany(notice.getToId()).getName();
            else
                toName = userService.getUserById(notice.getToId()).getName();
            nv = NoticeVo.setNoticeVo(notice, fromName, toName);
            lno.add(nv);
        }
        return Result.success(lno);
    }

    @GetMapping("/application")
    public Result<String> inviteUser(@RequestParam Integer user_id, @RequestParam Integer company_id) {
        userService.applyJoinCompany(user_id, company_id);
        return Result.success("申请成功");
    }

    @PostMapping("/deal_invitation")
    public Result<String> dealInvitation(@RequestParam Integer noticeId, @RequestParam Boolean result) {
        return Result.success(userService.dealInvitation(noticeId, result));
    }

    @PutMapping("/deal_read_only")
    public Result<String> dealReadOnlyNotice(@RequestParam Integer noticeId, @RequestParam Boolean result) {
        if (result) {
            userService.dealReadOnlyNotice(noticeId);
            return Result.success("只读消息已处理");
        } else {
            return Result.success("只读消息被挂起");
        }
    }

    @GetMapping("/notice_sum")
    public Result<Integer> getNoticeSum(@RequestParam Integer userId) {
        Integer sum = null;
        User user = userService.getUserById(userId);
        Integer permission = user.getPermission();
        Integer companyId = (userService.getCompanyByUserId(userId)).getCompanyId();
        if (permission == 1|| permission == 2){
            List<Notice> ln = noticeService.getUserNoticeListByToId(userId,false);
            sum = ln.size();
        }
        if (permission == 3 || permission ==4){
            List<Notice> ln = noticeService.getUserNoticeListByToId(userId, false);
            System.out.println(ln);
            sum = ln.size();
            ln = noticeService.getUserNoticeListByToId(companyId, true);
            sum = sum + ln.size();
        }
        return Result.success(sum);
    }

    @PostMapping("/join_department")
    public Result<String> joinDepartment(@RequestParam Integer userId,@RequestParam Integer departmentId,@RequestParam Integer companyId) {
        if (userService.getUserByIdAndCompany(userId,companyId) == null ) {
            return Result.error(ErrorEnum.UserNotExistError);
        } else {
            if (departmentService.getDepartmentByCompany(companyId) == null) {
                return Result.error(ErrorEnum.DepartmentNotExistError);
            } else {
                if (userService.joinDepartment(userId, departmentId))
                    return Result.success("加入部门成功");
                else
                    return Result.error(ErrorEnum.JoinDepartmentError);
            }
        }
    }


}
