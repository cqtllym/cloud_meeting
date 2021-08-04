package com.rjxy.Service;

import com.rjxy.Entity.Department;
import com.rjxy.Model.DepartmentVo;

import java.util.List;

public interface DepartmentService {

    //新建部门
    Department setupDepartment(Integer companyId,String departmentName);

    //查找部门
    Department getDepartmentByName(String departmentName);
    Department getDepartmentById(Integer departmentId);
    Department getDepartmentByIdAndCompany(Integer departmentId,Integer companyId);
    List<Department> getDepartmentByCompany(Integer companyId);
    Department getDepartment(Integer departmentId);
    Department getDepartmentByNameAndCompany(String departmentName,Integer companyId);

    //删除部门
    void deleteDepartment(Integer departmentId);

    //修改信息
    Department modifyDepartmentInfo(Integer departmentId,String departmentName);

}
