package com.rjxy.Controller;

import com.rjxy.Entity.Company;
import com.rjxy.Entity.Department;
import com.rjxy.Entity.Notice;
import com.rjxy.Entity.User;
import com.rjxy.Enum.ErrorEnum;
import com.rjxy.Model.CompanyVo;
import com.rjxy.Model.DepartmentVo;
import com.rjxy.Model.NoticeVo;
import com.rjxy.Model.UserVo;
import com.rjxy.Repository.CompanyRepository;
import com.rjxy.Results.Result;
import com.rjxy.Service.CompanyService;
import com.rjxy.Service.DepartmentService;
import com.rjxy.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author: Joheey
 * Date: 2021/7/6
 * Time: 10:27
 * Description:公司操作
 */

@CrossOrigin
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;
    CompanyRepository companyRepository;

    @Autowired
    UserService userService;

    @Autowired
    DepartmentService departmentService;

    @PutMapping("/register")
    public Result<CompanyVo> registerCompany(@RequestParam String companyName,
                                           @RequestParam Integer userId,
                                           @RequestParam(required = false) String companyAddress,
                                           @RequestParam(required = false) String companyType) {
        if (userService.getUserById(userId).getCompanyId() == null) {
            return Result.success(CompanyVo.setCompany(companyService.registerCompany(companyName, userId, companyAddress, companyType)));
        } else {
            return Result.error(ErrorEnum.UserHasCompanyError);
        }
    }

    @GetMapping("/detail")
    public Result<CompanyVo> getCompanyDetail(@RequestParam Integer companyId) {
        return Result.success(CompanyVo.setCompany(companyService.getCompany(companyId)));
    }

    @GetMapping("/employee_list")
    public Result<List<UserVo>> getEmployeeList(@RequestParam Integer company_id) {

        List<User> lu = companyService.getCompanyEmployeeList(company_id);
        List<UserVo> luo = new LinkedList<>();

        for (User user : lu) {
            UserVo uv = new UserVo();
            uv.setUserVo(user, companyService.getCompany(user.getCompanyId()),departmentService.getDepartment(user.getDepartmentId()));
            luo.add(uv);
        }

        return Result.success(luo);
    }

    @GetMapping("/search")
    public Result<List<CompanyVo>> searchCompany(@RequestParam String name) {
        List<Company> lc = companyService.searchCompanyByName(name);
        List<CompanyVo> lcv = new LinkedList<>();
        for (Company company : lc) {
            Integer founderId = company.getOwnerId();
            User founder = userService.getUserById(founderId);
            lcv.add(CompanyVo.getCompany(company, founder));
        }
        return Result.success(lcv);
    }

    @GetMapping("/notice_list")
    public Result<List<NoticeVo>> getNoticeList(@RequestParam Integer companyId) {
        List<Notice> ln = companyService.getNoticeListById(companyId);
        List<NoticeVo> lno = new LinkedList<>();
        NoticeVo nv;
        String fromName, toName;
        for (Notice notice : ln) {
            if (notice.getFromType())
                fromName = companyService.getCompany(notice.getFromId()).getName();
            else
                fromName = userService.getUserByIdAll(notice.getFromId()).getName();
            if (notice.getToType())
                toName = companyService.getCompany(notice.getToId()).getName();
            else
                toName = userService.getUserById(notice.getToId()).getName();
            nv = NoticeVo.setNoticeVo(notice, fromName, toName);
            lno.add(nv);
        }
        return Result.success(lno);
    }

    @GetMapping("/invitation")
    public Result<String> inviteUser(@RequestParam Integer company_id, @RequestParam Integer user_id) {
        companyService.inviteUser(company_id, user_id);
        return Result.success("邀请成功");
    }

    @PostMapping("/deal_application")
    public Result<String> dealApplication(@RequestParam Integer noticeId, @RequestParam Boolean result) {
        return Result.success(companyService.dealApplication(noticeId, result));
    }

    @GetMapping("/get_common_employee")
    public Result<List<UserVo>> getCompanyCommonEmployee(Integer companyId) {
        List<UserVo> uvl = new LinkedList<>();
        List<User> ul = companyService.getCompanyCommonEmployeeList(companyId);
        Company company = companyService.getCompany(companyId);

        for (User user : ul) {
            uvl.add(new UserVo().setUserVo(user, company));
        }

        return Result.success(uvl);

    }

    @GetMapping("/get_admin")
    public Result<List<UserVo>> getCompanyAdmin(Integer companyId) {
        List<UserVo> uvl = new LinkedList<>();
        List<User> ul = companyService.getCompanyAdminList(companyId);
        Company company = companyService.getCompany(companyId);

        for (User user : ul) {
            uvl.add(new UserVo().setUserVo(user, company));
        }

        return Result.success(uvl);
    }

    @PutMapping("set_admin")
    public Result<String> setAdmin(@RequestParam Integer userId, @RequestParam Integer companyId) {
        if (companyService.setCompanyAdministrator(userId, companyId))
            return Result.success("变更成功");
        else return Result.error(ErrorEnum.AdminSettingError);
    }

    @PutMapping("remove_admin")
    public Result<String> removeAdmin(@RequestParam Integer userId, @RequestParam Integer companyId) {
        if (companyService.removeCompanyAdministrator(userId, companyId))
            return Result.success("变更成功");
        else return Result.error(ErrorEnum.AdminSettingError);
    }

    @PostMapping("/delete")
    public Result<String> delete(@RequestParam Integer companyId) {
        if (companyService.getCompanyByDeletedAndId(companyId) != null) {
            companyService.deleteCompany(companyId);
            return Result.success("注销成功");
        } else {
            return Result.error(ErrorEnum.CompanyNotExistError);
        }
    }

    @PostMapping("/fire")
    public Result<String> fire(@RequestParam Integer companyId,
                               @RequestParam Integer[] userIdList) {
        if (companyService.fireEmployees(companyId, userIdList))
            return Result.success("已移除员工");
        else
            return Result.error(ErrorEnum.FireUserNotInCompanyError);
    }

    @GetMapping("/information")
    public Result<List<CompanyVo>>getCompanyInfo(@RequestParam Integer userId){
        User user = userService.getUserById(userId);
        Integer companyId = user.getCompanyId();
        Integer founderId = (companyService.getCompany(companyId)).getOwnerId();
        User founder = userService.getUserById(founderId);
        List<Company> Lc =companyService.getCompanyById(companyId);
        List<CompanyVo> Lco = new LinkedList<>();
        for (Company company : Lc) {
            Lco.add(CompanyVo.getCompany(company,founder));
        }
        return Result.success(Lco);
    }

    @GetMapping("/find_information")
    public Result<List<UserVo>> getUserInformation(@RequestParam Integer companyId,
                                                   @RequestParam String userName) {
        List<UserVo> luv = new LinkedList<>();
        Company company = companyService.getCompany(companyId);
        UserVo uv;
        List<User> lc = companyService.getUserByCompanyIdAndName(companyId, userName);

            for (User user : lc) {
                if (user.getDepartmentId() == null) {
                    uv = new UserVo();
                    uv.setUserVo(user, company);
                    luv.add(uv);
                } else {
                    uv = new UserVo();
                    uv.setUserVo(user, company, departmentService.getDepartment(user.getDepartmentId()));
                    luv.add(uv);
                }

            }

            return Result.success(luv);

    }

    @PostMapping("/modify")
    public Result<CompanyVo> modifyCompany(@RequestParam Integer companyId,@RequestParam(required = false) String companyAddress) {

        Company company;
        company = companyService.getCompany(companyId);

        if (company != null) {

            if (companyService.getCompanyByDeletedAndId(companyId) != null){
                return Result.success(CompanyVo.setCompany(companyService.modifyCompany(companyId,companyAddress)));
            } else {
                return Result.error(ErrorEnum.ModifyCompanyError);
            }

        } else {
            return Result.error(ErrorEnum.CompanyNotExistError);
        }

    }

}
