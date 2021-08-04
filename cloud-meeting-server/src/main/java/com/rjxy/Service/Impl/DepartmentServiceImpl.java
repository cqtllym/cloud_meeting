package com.rjxy.Service.Impl;

import com.rjxy.Entity.Company;
import com.rjxy.Entity.Department;
import com.rjxy.Entity.Notice;
import com.rjxy.Entity.User;
import com.rjxy.Model.DepartmentVo;
import com.rjxy.Repository.CompanyRepository;
import com.rjxy.Repository.DepartmentRepository;
import com.rjxy.Repository.NoticeRepository;
import com.rjxy.Repository.UserRepository;
import com.rjxy.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NoticeRepository noticeRepository;

    @Override
    public Department setupDepartment(Integer companyId,String departmentName) {

        Department department = new Department();
        department.setCompanyId(companyId);
        department.setName(departmentName);
        departmentRepository.save(department);
        return department;

    }

    @Override
    public Department getDepartmentByName(String departmentName) {
        return departmentRepository.findDepartmentByName(departmentName);
    }

    @Override
    public Department getDepartmentById(Integer departmentId) {
        return departmentRepository.findDepartmentById(departmentId);
    }

    @Override
    public Department getDepartmentByIdAndCompany(Integer departmentId, Integer companyId) {
        return departmentRepository.findDepartmentByIdAndCompanyId(departmentId,companyId);
    }

    @Override
    public List<Department> getDepartmentByCompany(Integer companyId) {
        return departmentRepository.findDepartmentsByCompanyId(companyId);
    }

    @Override
    public Department getDepartment(Integer departmentId) {
        return departmentRepository.findDepartmentById(departmentId);
    }

    @Override
    public Department getDepartmentByNameAndCompany(String departmentName, Integer companyId) {
        return departmentRepository.findDepartmentByNameAndCompanyId(departmentName,companyId);
    }

    @Transactional
    @Override
    public void deleteDepartment(Integer departmentId) {

        Department department ;
        department = departmentRepository.findDepartmentById(departmentId);

        //发送注销通知
        Notice deleteNotice = new Notice();
        deleteNotice.setTheme("您所在的部门 " + department.getName() + " 已注销，您已被移出部门 ");
        deleteNotice.setType(3);
        deleteNotice.setIsDealt(false);
        deleteNotice.setFromId(department.getCompanyId());
        deleteNotice.setFromType(true);

        List<User> ul = userRepository.findByDepartmentId(departmentId);

        if (!ul.isEmpty()) {
            for (User user : ul) {
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

            }

        }

        noticeRepository.flush();

        departmentRepository.deleteDepartmentById(departmentId);

    }

    @Override
    public Department modifyDepartmentInfo(Integer departmentId,String departmentName) {

        Department department;
        department = departmentRepository.findDepartmentById(departmentId);
        department.setId(departmentId);
        department.setName(departmentName);
        departmentRepository.saveAndFlush(department);
        return department;

    }

}
