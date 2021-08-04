package com.rjxy.Service;

import com.rjxy.Entity.Company;
import com.rjxy.Entity.Notice;
import com.rjxy.Entity.User;

import java.util.List;


public interface CompanyService {

    //注册与注销企业
    Company registerCompany(String companyName, Integer userId, String cAddress, String cType);
    void deleteCompany(Integer companyId);

    //设置某人为公司员工（无需对方接受）
    Boolean setEmployee(Company company,User user);
    Boolean setEmployees(Company company,User[] users);

    //开除用户
    Boolean fireEmployee(Integer companyId, Integer userId);

    Boolean fireEmployees(Integer companyId, Integer[] userIdList);

    //设置企业管理员
    Boolean setCompanyAdministrator(Integer companyId, Integer userId);

    Boolean removeCompanyAdministrator(Integer companyId, Integer userId);

    //获取某一状态的所有企业 false为注销企业
    List<Company> getCompanyByWhetherDeleted(Boolean deleted);

    //通过企业ID获取已注销的企业
    Company getCompanyByDeletedAndId(Integer companyId);

    List<User> getCompanyEmployeeList(Integer companyId);

    List<User> getCompanyCommonEmployeeList(Integer companyId);

    List<User> getCompanyAdminList(Integer companyId);

    void inviteUser(Integer companyId, Integer userId);

    void inviteUser(Integer companyId, String userName);

    List<Company> searchCompanyByName(String name);

    Company getCompany(Integer companyId);

    List<Notice> getNoticeListById(Integer id);

    String dealApplication(Integer noticeId, Boolean result);

    List<Company> getCompanyById(Integer companyId);

    List<User> getUserByCompanyIdAndName(Integer companyId, String userName);

    Company modifyCompany(Integer companyId,String companyAddress);

}
