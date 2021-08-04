package com.rjxy.Model;

import com.rjxy.Entity.Company;
import com.rjxy.Entity.Department;
import com.rjxy.Entity.User;
import com.rjxy.Repository.CompanyRepository;
import com.rjxy.Service.CompanyService;
import com.rjxy.Service.Impl.CompanyServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
public class UserVo {

    private ArrayList<Map<String, String>> roles;

    private Integer userId;

    private String username;

    private String telephone;

    private String email;

    private String sex;//男性或女性

    private Integer companyId;

    private String companyName;

    private Integer departmentId;

    private String departmentName;

    public UserVo setUserVo(User user) {
        setUserId(user.getId());

        setUsername(user.getName());

        setTelephone(user.getTelephone());

        setEmail(user.getEmail());

        //设置身份
        String[] strings = new String[]{"user", "employee", "admin", "founder"};
        Integer permission = user.getPermission();
        if (permission <= 0 || permission > 4) {
            permission = 1;
        }
        Map<String, String> map = new HashMap<>();
        map.put("id", strings[permission - 1]);
        ArrayList<Map<String, String>> list = new ArrayList<>();
        list.add(map);
        setRoles(list);

        if (user.getSex() != null) {
            if (user.getSex() == 1) {
                setSex("男性");
            } else {
                setSex("女性");
            }
        }

        return this;
    }

    public UserVo setUserVo(User user, Company company) {
        setUserVo(user);
        if (company != null) {
            setCompanyId(company.getId());
            setCompanyName(company.getName());
        }

        return this;
    }

    public UserVo setUserVo(User user, Company company,Department department) {
        setUserVo(user,company);

        if (department != null) {
            setDepartmentId(department.getId());
            setDepartmentName(department.getName());
        }

        return this;
    }


    @Override
    public String toString() {
        return "UserVo{" +
                "id=" + userId +
                ", name='" + username + '\'' +
                ", roles=" + roles +
                ", telephone=" + telephone +
                ", sex=" + sex +
                '}';
    }
}
