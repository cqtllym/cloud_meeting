package com.rjxy.Model;

import com.rjxy.Entity.Company;
import com.rjxy.Entity.User;
import com.rjxy.Service.UserService;
import lombok.Data;



@Data
public class CompanyVo {

    private Integer id;

    private String name;

    private String address;

    private String type;

    private String founderName;

    private String founderPhone;

    private String founderEmail;

    public static CompanyVo setCompany(Company company) {
        CompanyVo cv = new CompanyVo();
        cv.setId(company.getId());
        cv.setName(company.getName());
        return cv;
    }
    public static CompanyVo getCompany(Company company, User founder) {
        CompanyVo cv = new CompanyVo();
        cv.setId(company.getId());
        cv.setName(company.getName());
        cv.setAddress(company.getCAddress());
        cv.setType(company.getCType());
        cv.setFounderName(founder.getName());
        cv.setFounderPhone(founder.getTelephone());
        cv.setFounderEmail(founder.getEmail());
        return cv;
    }
}
