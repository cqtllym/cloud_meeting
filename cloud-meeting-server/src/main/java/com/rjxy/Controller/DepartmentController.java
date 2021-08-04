package com.rjxy.Controller;

import com.rjxy.Entity.Department;
import com.rjxy.Entity.User;
import com.rjxy.Enum.ErrorEnum;
import com.rjxy.Model.CompanyVo;
import com.rjxy.Model.DepartmentVo;
import com.rjxy.Model.UserVo;
import com.rjxy.Results.Result;
import com.rjxy.Service.CompanyService;
import com.rjxy.Service.DepartmentService;
import com.rjxy.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    CompanyService companyService;

    @Autowired
    UserService userService;

    @Autowired
    DepartmentService departmentService;

    @PutMapping("/setup")
    public Result<DepartmentVo> setupDepartment(Integer companyId, String departmentName) {

        if (departmentService.getDepartmentByNameAndCompany(departmentName,companyId) == null){
            return Result.success(DepartmentVo.setDepartment(departmentService.setupDepartment(companyId,departmentName)));
        } else {
            return Result.error(ErrorEnum.DepartmentExistError);
        }

    }

    @DeleteMapping("/delete")
    public Result<String> deleteDepartment(Integer departmentId) {
        if (departmentService.getDepartmentById(departmentId) != null) {
            departmentService.deleteDepartment(departmentId);
            return Result.success("删除成功");
        } else {
            return Result.error(ErrorEnum.DepartmentNotExistError);
        }
    }

    @GetMapping("/detail")
    public Result<DepartmentVo> getDepartmentDetail(@RequestParam Integer departmentId) {
        return Result.success(DepartmentVo.setDepartment(departmentService.getDepartmentById(departmentId)));
    }

    @GetMapping("/list")
    public Result<List<DepartmentVo>> getDepartment(@RequestParam Integer companyId) {

        List<Department> ld = departmentService.getDepartmentByCompany(companyId);
        List<DepartmentVo> ldo = new LinkedList<>();

        for (Department department : ld) {
            DepartmentVo dv = new DepartmentVo();
            dv.setDepartmentVo(department, companyService.getCompany(department.getCompanyId()));
            ldo.add(dv);
        }

        return Result.success(ldo);
    }

    @GetMapping("/find_employee")
    public Result<List<UserVo>> findEmployee(@RequestParam Integer departmentId,@RequestParam Integer companyId) {

        if (companyId != null) {

            if (departmentId == null){

                List<User> lu = companyService.getCompanyEmployeeList(companyId);
                List<UserVo> luo = new LinkedList<>();

                for (User user : lu) {
                    if (user.getDepartmentId() == null) {
                        UserVo uv = new UserVo();
                        uv.setUserVo(user, companyService.getCompany(user.getCompanyId()));
                        luo.add(uv);
                    }
                }

                return Result.success(luo);

            } else {

                List<User> lu = userService.getUsersByDepartment(departmentId);
                List<UserVo> luo = new LinkedList<>();

                for (User user : lu) {
                    UserVo uv = new UserVo();
                    uv.setUserVo(user,companyService.getCompany(companyId),departmentService.getDepartment(departmentId));
                    luo.add(uv);
                }

                return Result.success(luo);

            }

        } else {
            return Result.error(ErrorEnum.UserNotExistError);
        }


    }

    @PostMapping("/modify")
    public Result<DepartmentVo> modify(@RequestParam Integer departmentId,@RequestParam String departmentName) {

        Department department ;
        department = departmentService.getDepartment(departmentId);

        if (department != null) {

            if (departmentService.getDepartmentByNameAndCompany(departmentName,department.getCompanyId()) == null){
                return Result.success(DepartmentVo.setDepartment(departmentService.modifyDepartmentInfo(departmentId,departmentName)));
            } else {
                return Result.error(ErrorEnum.ModifyDepartmentError);
            }

        } else {
            return Result.error(ErrorEnum.DepartmentNotExistError);
        }

    }

}
