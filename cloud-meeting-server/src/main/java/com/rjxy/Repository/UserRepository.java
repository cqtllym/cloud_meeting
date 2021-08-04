package com.rjxy.Repository;

import com.rjxy.Entity.User;
import com.rjxy.Model.UserVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserById(Integer userId);

    User findUserByName(String userName);

    User findUserInformationByNameAndCompanyId(String userName,Integer companyId);

    User findUserByIdAndDeletedFalse(Integer userId);

    User findUserByNameAndDeletedFalse(String userName);

    User findUsersByCompanyId(Integer companyId);

    List<User> findAllByName(String userName);

    List<User> findAllByNameAndDeletedFalse(String userName);

    List<User> findAllByTelephone(String telephone);

    List<User> findAllByTelephoneAndDeletedFalse(String telephone);

    List<User> findByPermission(Integer permission);

    List<User> findByCompanyId(Integer companyId);

    List<User> findUsersByCompanyIdAndPermission(Integer companyId, Integer permission);

    List<User> findByDepartmentId(Integer departmentId);

    @Query("SELECT user FROM User user WHERE user.companyId = ?1 AND user.name LIKE %?2% AND user.deleted = FALSE")
    List<User> findUsersByCompanyIdAndName(Integer companyId, String userName);

    @Query("SELECT user FROM User user WHERE user.departmentId = ?1 AND user.name LIKE %?2% AND user.deleted = FALSE")
    List<User> findUsersByDepartmentIdAndName(Integer departmentId,String userName);

    User findUserByCompanyIdAndId(Integer companyId,Integer userId);

}
