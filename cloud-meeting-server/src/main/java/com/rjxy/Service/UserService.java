package com.rjxy.Service;

import com.rjxy.Entity.Notice;
import com.rjxy.Entity.User;
import com.rjxy.Model.UserVo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface UserService {
    //注册与注销
    Boolean registerUser(User user);//简易版 参数是可以带有空值

    void deleteUser(User user);

    void deleteUserById(Integer id);

    void deleteUserByName(String name);

    //密码
    void changePassword(Integer userId, String password);

    Boolean checkUserPassword(String name, String password);

    Boolean checkUserPassword(Integer id, String password);

    //信息更改
    Boolean modifyUserInfo(UserVo userVo);

    void changeUserPermission(Integer userId, Integer permission);

    //用户退出企业
    void quitCompany(Integer userId, Integer companyId);

    void quitCompany(User user);

    //用户查询
    User getUserById(Integer id);

    User getUserByIdAll(Integer id);

    User getUserByName(String name);

    User getUserInformationByNameAndCompany(String userName, Integer companyId);

    User getUserByNameAndNotDeleted(String name);

    Map<Integer, User> searchUserByNameOrTelephone(String telephone);

    List<User> getUserByCompany(Integer companyId);

    List<Notice> getNoticeListById(Integer id);

    void applyJoinCompany(Integer userId, Integer companyId);

    String dealInvitation(Integer noticeId, boolean result);

    void dealReadOnlyNotice(Integer noticeId);

    User getCompanyByUserId(Integer userId);

    List<User> getUsersByDepartment(Integer departmentId);

    User getUserByIdAndNotDeleted(Integer userId);

    Boolean joinDepartment(Integer userId, Integer departmentId);

    User getUserByIdAndCompany(Integer userId,Integer companyId);

    List<User> getUserByDepartmentIdAndName(Integer departmentId, String userName);

}
