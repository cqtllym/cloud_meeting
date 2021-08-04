package com.rjxy.Model;

import com.rjxy.Entity.Company;
import com.rjxy.Entity.Department;
import com.rjxy.Entity.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
public class DepartmentVo {

    private Integer id;

    private String name;

    private String founderName;

    private Integer companyId;

    private String companyName;

    public static DepartmentVo setDepartment(Department department) {
        DepartmentVo dv = new DepartmentVo();
        dv.setId(department.getId());
        dv.setName(department.getName());
        return dv;
    }

    public static DepartmentVo getDepartment(Department department, Company founder) {
        DepartmentVo dv = new DepartmentVo();
        dv.setId(department.getId());
        dv.setName(department.getName());
        dv.setFounderName(founder.getName());
        return dv;
    }

    public DepartmentVo setDepartmentVo(Department department) {
        setId(department.getId());

        setName(department.getName());

        return this;
    }

    public DepartmentVo setDepartmentVo(Department department,Company company) {
        setDepartmentVo(department);
        if (company != null) {
            setCompanyId(company.getId());
            setCompanyName(company.getName());
        }

        return this;
    }

}
