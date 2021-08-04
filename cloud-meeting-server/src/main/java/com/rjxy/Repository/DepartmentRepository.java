package com.rjxy.Repository;

import com.rjxy.Entity.Department;
import com.rjxy.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Department findDepartmentById(Integer departmentId);

    Department findDepartmentByName(String departmentName);

    void deleteDepartmentById(Integer departmentId);

    Department findDepartmentByIdAndCompanyId(Integer departmentId,Integer companyId);

    List<Department> findDepartmentsByCompanyId(Integer companyId);

    Department findDepartmentByNameAndCompanyId(String departmentName,Integer companyId);

}
