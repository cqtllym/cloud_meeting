package com.rjxy.Repository;

import com.rjxy.Entity.Company;
import com.rjxy.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Company findCompanyById(Integer companyId);

    @Query("SELECT company FROM Company company WHERE company.name LIKE %?1%")
    List<Company> searchByName(String name);

    @Query("SELECT company FROM Company company WHERE company.deleted = true ")
    List<Company> findCompaniesByDeleted(Boolean deleted);

    Company findCompanyByIdAndDeletedFalse(Integer companyId);

    List<Company> findCompanyInformationById(Integer companyId);


}
