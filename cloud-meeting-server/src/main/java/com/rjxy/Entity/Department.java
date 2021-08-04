package com.rjxy.Entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Set;

/**
 * @author: Joheey
 * Date: 2021/7/1
 * Time: 20:00
 * Description:部门实体，预定义的实体，后续迭代开发中在写具体的功能。对于User，department_id可以为空
 */
@Data
@Entity
@Table(name = "department")
public class Department {
    @Id
    @Column(name = "id",unique = true, length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "create_time")
    @CreatedDate
    private Timestamp createTime;

    @Column(name = "modify_time")
    @LastModifiedDate
    private Timestamp modifyTime;

    @Column(name = "company_id")
    private Integer companyId;

    public String getCreateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(createTime);
    }

    public String getModifyTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(modifyTime);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user_id=" + user_id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", companyId=" + companyId +
                '}';
    }
}
