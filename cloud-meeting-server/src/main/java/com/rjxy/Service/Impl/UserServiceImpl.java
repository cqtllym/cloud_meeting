package com.rjxy.Service.Impl;

import com.rjxy.Entity.Company;
import com.rjxy.Entity.Department;
import com.rjxy.Entity.Notice;
import com.rjxy.Entity.User;
import com.rjxy.Model.UserVo;
import com.rjxy.Repository.CompanyRepository;
import com.rjxy.Repository.DepartmentRepository;
import com.rjxy.Repository.NoticeRepository;
import com.rjxy.Repository.UserRepository;
import com.rjxy.Service.CompanyService;
import com.rjxy.Service.UserService;
import com.rjxy.Util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DepartmentRepository departmentRepository;

    private User user = new User();


    @Override
    public Boolean registerUser(User user) {
        this.user = user;
        if (userRepository.findUserByNameAndDeletedFalse(this.user.getName()) != null) {
            return false;
        } else {
            this.user.setPassword(MD5Util.string2MD5(user.getPassword()));
            userRepository.saveAndFlush(user);
            return true;
        }
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void deleteUserById(Integer id) {
        User user = userRepository.findUserById(id);
        user.setDeleted(true);

        Company company = companyRepository.findCompanyByIdAndDeletedFalse(user.getCompanyId());
        user.setCompanyId(null);
        user.setDepartmentId(null);

        if (company != null) {//如果注销的用户有公司
            if (user.getPermission() == 4) {
                List<User> ul;
                ul = userRepository.findByCompanyId(company.getId());

                for (User u : ul) {
                    u.setCompanyId(null);
                    u.setPermission(1);
                }

                companyService.deleteCompany(company.getId());
                userRepository.saveAll(ul);
            } else {
                //发送注销通知
                Notice deleteNotice = new Notice();
                deleteNotice.setTheme("公司的用户 " + user.getName() + " 已注销 ，已自动退出企业 ");
                deleteNotice.setType(3);
                deleteNotice.setIsDealt(false);
                deleteNotice.setFromId(id);
                deleteNotice.setFromType(false);
                deleteNotice.setToId(company.getId());
                deleteNotice.setToType(true);
                noticeRepository.save(deleteNotice);
            }
        }

        user.setPermission(1);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUserByName(String name) {
        User user = userRepository.findUserByName(name);
        user.setDeleted(true);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void changePassword(Integer userId, String password) {
        User user = userRepository.findUserById(userId);
        user.setPassword(MD5Util.string2MD5(password));
        userRepository.saveAndFlush(user);
    }

    @Override
    public Boolean checkUserPassword(String name, String password) {
        //对于用户进行MD5加密
        this.user = userRepository.findUserByNameAndDeletedFalse(name);
        if (this.user == null) {
            return false;
        }
        return this.user.getPassword().equals(MD5Util.string2MD5(password));
    }

    @Override
    public Boolean checkUserPassword(Integer id, String password) {
        this.user = userRepository.findUserByIdAndDeletedFalse(id);
        if (this.user == null) {
            return false;
        }
        return this.user.getPassword().equals(MD5Util.string2MD5(password));
    }

    @Override
    public Boolean modifyUserInfo(UserVo userVo) {
        User user;
        user = userRepository.findUserByIdAndDeletedFalse(userVo.getUserId());

        if (user != null) {
            if (!user.getName().equals(userVo.getUsername()))//如果用户修改了名字
                if ((userRepository.findUserByNameAndDeletedFalse(userVo.getUsername())) != null)
                    return false;

            user.setTelephone(userVo.getTelephone());
            user.setEmail(userVo.getEmail());

            int sex = 1;
            if (userVo.getSex().equals("女性"))
                sex = 0;

            user.setSex(sex);
            userRepository.saveAndFlush(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void changeUserPermission(Integer userId, Integer permission) {
        User user = userRepository.findUserByIdAndDeletedFalse(userId);
        user.setPermission(permission);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void quitCompany(Integer userId, Integer companyId) {
        Notice quitNotice = new Notice();
    }

    @Override
    public void quitCompany(User user) {
        Notice quitNotice = new Notice();
        Integer companyId = user.getCompanyId();
        Company company = companyRepository.findCompanyById(companyId);
        user.setCompanyId(null);
        user.setPermission(1);


        quitNotice.setTheme("" + user.getName() + " 已从 " + company.getName() + " 退出 ");
        quitNotice.setType(3);
        quitNotice.setIsDealt(false);
        quitNotice.setFromId(user.getId());
        quitNotice.setFromType(false);
        quitNotice.setToId(companyId);
        quitNotice.setToType(true);

        userRepository.save(user);
        noticeRepository.save(quitNotice);

        userRepository.flush();
        noticeRepository.flush();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findUserByIdAndDeletedFalse(id);
    }

    @Override
    public User getUserByIdAll(Integer id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    @Override
    public User getUserInformationByNameAndCompany(String userName, Integer companyId) {

        return userRepository.findUserInformationByNameAndCompanyId(userName, companyId);
    }

    @Override
    public User getUserByNameAndNotDeleted(String name) {
        //用户不存在则返回假
        return userRepository.findUserByNameAndDeletedFalse(name);
    }

    @Override
    public Map<Integer, User> searchUserByNameOrTelephone(String keyword) {
        Map<Integer, User> userMap = new HashMap<>();
        for (User result : userRepository.findAllByNameAndDeletedFalse(keyword)) {
            userMap.put(result.getId(), result);
        }
        for (User result : userRepository.findAllByTelephoneAndDeletedFalse(keyword)) {
            userMap.put(result.getId(), result);
        }
        return userMap;
    }

    @Override
    public List<User> getUserByCompany(Integer companyId) {
        return userRepository.findByCompanyId(companyId);
    }

    @Override
    public List<Notice> getNoticeListById(Integer id) {
        return noticeRepository.findAllByToIdAndToTypeOrderByIdDesc(id, false);
    }

    @Override
    public void applyJoinCompany(Integer userId, Integer companyId) {

        Notice applyNotice = new Notice();
        User user = userRepository.findUserByIdAndDeletedFalse(userId);
        Company company = companyRepository.findCompanyById(companyId);

        applyNotice.setTheme("" + user.getName() + " 申请加入 " + company.getName());
        applyNotice.setType(1);
        applyNotice.setIsDealt(false);
        applyNotice.setFromId(userId);
        applyNotice.setFromType(false);
        applyNotice.setToId(companyId);
        applyNotice.setToType(true);

        noticeRepository.save(applyNotice);

    }

    @Override
    @Transactional
    public String dealInvitation(Integer noticeId, boolean result) {
        Notice notice = noticeRepository.getOne(noticeId);
        User user = userRepository.findUserByIdAndDeletedFalse(notice.getToId());
        Company company = companyRepository.findCompanyById(notice.getFromId());

        if (notice.getType() == 3) {

            notice.setResult(null);
            notice.setIsDealt(true);
            noticeRepository.save(notice);

        } else if (notice.getType() == 1) {

            if (!result) {

                notice.setResult(false);
                notice.setIsDealt(true);
                noticeRepository.save(notice);

            } else {
                if (user.getCompanyId() != null)
                    return "处理失败，用户已经在企业中";
                else {
                    notice.setResult(true);
                    notice.setIsDealt(true);
                    noticeRepository.save(notice);

                    user.setCompanyId(company.getId());
                    user.setPermission(2);
                    userRepository.save(user);
                }
            }
        }


        return "处理成功";
    }

    @Override
    public void dealReadOnlyNotice(Integer noticeId) {
        Notice notice = noticeRepository.getOne(noticeId);
        notice.setIsDealt(true);
        noticeRepository.saveAndFlush(notice);
    }

    @Override
    public User getCompanyByUserId(Integer userId) {
        return userRepository.findUserById(userId);
    }

    @Override
    public List<User> getUsersByDepartment(Integer departmentId) {
        return userRepository.findByDepartmentId(departmentId);
    }

    @Override
    public User getUserByIdAndNotDeleted(Integer userId) {
        //用户不存在则返回假
        return userRepository.findUserByIdAndDeletedFalse(userId);
    }

    @Override
    public Boolean joinDepartment(Integer userId, Integer departmentId) {
        User user = userRepository.findUserByIdAndDeletedFalse(userId);

        if (user.getPermission() == 4) {
            return false;
        }

        user.setDepartmentId(departmentId);
        userRepository.saveAndFlush(user);
        return true;
    }

    @Override
    public User getUserByIdAndCompany(Integer userId, Integer companyId) {
        return userRepository.findUserByCompanyIdAndId(companyId, userId);
    }

    @Override
    public List<User> getUserByDepartmentIdAndName(Integer departmentId, String userName) {
        return userRepository.findUsersByDepartmentIdAndName(departmentId,userName);
    }


}
