package com.rjxy.Service.Impl;

import com.rjxy.Entity.Department;
import com.rjxy.Entity.Notice;
import com.rjxy.Entity.Company;
import com.rjxy.Entity.User;
import com.rjxy.Repository.CompanyRepository;
import com.rjxy.Repository.DepartmentRepository;
import com.rjxy.Repository.NoticeRepository;
import com.rjxy.Repository.UserRepository;
import com.rjxy.Service.CompanyService;
import com.rjxy.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Joheey
 * Date: 2021/7/6
 * Time: 15:14
 * Description:
 */

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    UserService userService;

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public Company registerCompany(String companyName, Integer userId, String cAddress, String cType) {
        Company company = new Company();
        company.setName(companyName);
        company.setOwnerId(userId);
        company.setCType(cType);
        company.setCAddress(cAddress);
        companyRepository.save(company);
        User owner = userRepository.getOne(userId);
        owner.setCompanyId(company.getId());
        owner.setPermission(4);
        userRepository.save(owner);
        return company;
    }

    @Override
    @Transactional
    public void deleteCompany(Integer companyId) {
        //企业注销
        Company company = companyRepository.findCompanyById(companyId);

        //发送注销通知
        Notice deleteNotice = new Notice();
        deleteNotice.setTheme("您所在的公司 " + company.getName() + " 已注销 ，您已被移出企业 ");
        deleteNotice.setType(3);
        deleteNotice.setIsDealt(false);
        deleteNotice.setFromId(companyId);
        deleteNotice.setFromType(true);

        List<User> ul = userRepository.findByCompanyId(companyId);

        if (!ul.isEmpty()) {
            for (User user : ul) {
                user.setCompanyId(null);
                user.setPermission(1);
                user.setDepartmentId(null);

                Notice deleteNo;
                try {
                    deleteNo = deleteNotice.clone();
                    deleteNo.setToId(user.getId());
                    deleteNo.setToType(false);

                    noticeRepository.save(deleteNo);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                userRepository.save(user);
            }

        }

        List<Department> dl = departmentRepository.findDepartmentsByCompanyId(companyId);

        if (!dl.isEmpty()) {
            for (Department department : dl) {
                departmentRepository.deleteDepartmentById(department.getId());
            }
        }

        company.setDeleted(true);

        userRepository.flush();
        noticeRepository.flush();
        companyRepository.saveAndFlush(company);
    }

    @Override
    public Boolean setEmployee(Company company, User user) {
        return null;
    }

    @Override
    public Boolean setEmployees(Company company, User[] users) {
        return null;
    }

    @Override
    public Boolean fireEmployee(Integer companyId, Integer userId) {
        User u = userRepository.findUserById(userId);

        if (u.getCompanyId() == null || (!u.getCompanyId().equals(companyId)) || u.getPermission() == 4)
            return false;
        u.setPermission(1);
        u.setCompanyId(null);
        u.setDepartmentId(null);
        userRepository.save(u);
        return true;
    }

    @Override
    public Boolean fireEmployees(Integer companyId, Integer[] userIdList) {
        Notice fireNotice = new Notice();
        Company company = companyRepository.findCompanyByIdAndDeletedFalse(companyId);
        boolean fire = true;//用于判断是否开除了不属于本公司的人

        fireNotice.setTheme("您已被 " + company.getName() + " 开除 ");
        fireNotice.setType(3);
        fireNotice.setIsDealt(false);
        fireNotice.setFromId(companyId);
        fireNotice.setFromType(true);

        for (Integer userId : userIdList) {
            if (fireEmployee(companyId, userId)) {
                try {
                    Notice notice;
                    notice = fireNotice.clone();
                    notice.setToId(userId);
                    notice.setToType(false);
                    noticeRepository.save(notice);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            } else {
                fire = false;
            }
        }

        userRepository.flush();
        noticeRepository.flush();
        return fire;
    }

    @Override
    public Boolean setCompanyAdministrator(Integer userId, Integer companyId) {
        User user = userRepository.findUserByIdAndDeletedFalse(userId);
        Company company = companyRepository.findCompanyById(companyId);
        Notice notice = new Notice();

        System.out.println("permission" + user.getPermission() + "  " + (user.getPermission() >= 3));
        if (user.getPermission() >= 3)
            return false;

        notice.setTheme("" + user.getName() + " 已设置为 " + company.getName() + " 的企业管理员 ");
        notice.setType(3);
        notice.setIsDealt(false);
        notice.setFromId(companyId);
        notice.setFromType(true);
        notice.setToId(userId);
        notice.setToType(false);

        if (user.getCompanyId() != null && user.getCompanyId().equals(companyId)) {
            userService.changeUserPermission(userId, 3);
            noticeRepository.saveAndFlush(notice);
            return true;
        }

        return false;
    }

    @Override
    public Boolean removeCompanyAdministrator(Integer userId, Integer companyId) {
        User user = userRepository.findUserByIdAndDeletedFalse(userId);
        Company company = companyRepository.findCompanyById(companyId);
        Notice notice = new Notice();
        if (user.getPermission() <= 2)
            return false;

        notice.setTheme("已移除" + user.getName() + " 在 " + company.getName() + " 的企业管理员身份 ");
        notice.setType(3);
        notice.setIsDealt(false);
        notice.setFromId(companyId);
        notice.setFromType(true);
        notice.setToId(userId);
        notice.setToType(false);

        if (user.getCompanyId().equals(companyId)) {
            userService.changeUserPermission(userId, 2);
            noticeRepository.saveAndFlush(notice);
            return true;
        }
        return false;
    }

    @Override
    public List<Company> getCompanyByWhetherDeleted(Boolean deleted) {
        return null;
    }

    //根据id查找未注销企业
    @Override
    public Company getCompanyByDeletedAndId(Integer companyId) {
        return companyRepository.findCompanyByIdAndDeletedFalse(companyId);
    }

    @Override
    public List<User> getCompanyEmployeeList(Integer companyId) {

        return userRepository.findByCompanyId(companyId);

    }

    @Override
    public List<User> getCompanyCommonEmployeeList(Integer companyId) {
        return userRepository.findUsersByCompanyIdAndPermission(companyId, 2);
    }

    @Override
    public List<User> getCompanyAdminList(Integer companyId) {
        return userRepository.findUsersByCompanyIdAndPermission(companyId, 3);
    }

    @Override
    public void inviteUser(Integer companyId, Integer userId) {

        Notice inviteNotice = new Notice();
        Company company = companyRepository.findCompanyById(companyId);

        inviteNotice.setTheme("" + company.getName() + " 邀请您加入");
        inviteNotice.setType(1);
        inviteNotice.setIsDealt(false);
        inviteNotice.setFromId(companyId);
        inviteNotice.setFromType(true);
        inviteNotice.setToId(userId);
        inviteNotice.setToType(false);

        noticeRepository.save(inviteNotice);

    }

    @Override
    public void inviteUser(Integer companyId, String userName) {
        Integer userId = userRepository.findUserByName(userName).getId();
        inviteUser(companyId, userId);
    }

    @Override
    public List<Company> searchCompanyByName(String name) {
        return companyRepository.searchByName(name);
    }

    @Override
    public Company getCompany(Integer companyId) {
        return companyRepository.findCompanyById(companyId);
    }

    @Override
    public List<Notice> getNoticeListById(Integer id) {
        return noticeRepository.findAllByToIdAndToTypeOrderByIdDesc(id, true);
    }

    @Override
    @Transactional
    public String dealApplication(Integer noticeId, Boolean result) {
        Notice notice = noticeRepository.getOne(noticeId);
        User user = userRepository.findUserByIdAndDeletedFalse(notice.getFromId());
        Company company = companyRepository.findCompanyById(notice.getToId());


        if (notice.getType() == 3) {

            notice.setResult(null);
            notice.setIsDealt(true);
            noticeRepository.save(notice);

        } else if (notice.getType() == 1) {

            Notice returnNotice = new Notice();
            returnNotice.setIsDealt(false);
            returnNotice.setType(3);
            returnNotice.setToId(notice.getFromId());
            returnNotice.setFromId(notice.getToId());
            returnNotice.setToType(notice.getFromType());
            returnNotice.setFromType(notice.getToType());
            returnNotice.setResult(result);

            if (!result) {
                returnNotice.setTheme("很遗憾 " + company.getName() + " 拒绝了您的申请! ");
                notice.setResult(false);
                notice.setIsDealt(true);

                noticeRepository.save(notice);
                noticeRepository.save(returnNotice);

            } else {
                //如果result为真 通过用户的加入企业申请
                if (user.getCompanyId() != null)
                    return "处理失败，用户已经在企业中";
                else {
                    notice.setResult(true);
                    notice.setIsDealt(true);
                    returnNotice.setTheme(" 恭喜您 " + company.getName() + " 同意了您的申请! ");
                    noticeRepository.save(notice);
                    noticeRepository.save(returnNotice);

                    user.setCompanyId(company.getId());
                    user.setPermission(2);
                    userRepository.save(user);
                }
            }
        }

        return "处理成功";
    }

    @Override
    public List<Company> getCompanyById(Integer companyId) {
        return companyRepository.findCompanyInformationById(companyId);
    }

    @Override
    public List<User> getUserByCompanyIdAndName(Integer companyId, String userName) {
        return userRepository.findUsersByCompanyIdAndName(companyId, userName);
    }

    @Override
    public Company modifyCompany(Integer companyId, String companyAddress) {
        Company company;
        company = companyRepository.findCompanyByIdAndDeletedFalse(companyId);
        company.setId(companyId);
        company.setCAddress(companyAddress);
        companyRepository.saveAndFlush(company);
        return company;
    }


}
