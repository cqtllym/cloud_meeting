package com.rjxy.Entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id", unique = true, nullable = false, length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "permission", nullable = false)
    private Integer permission = 1;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @Column(name = "sex")
    private Integer sex;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "create_time")
    @CreatedDate
    private Timestamp createTime;

    @Column(name = "modify_time")
    @LastModifiedDate
    private Timestamp modifyTime;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "department_id")
    private Integer departmentId;

    public User(String username, String password, Integer permission) {
        this.name = username;
        this.password = password;
        this.permission = permission;
    }

    public User(String name, String password, String telephone, Integer sex) {
        this.name = name;
        this.password = password;
        this.telephone = telephone;
        this.sex = sex;
    }

    public User() {

    }

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
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", permission=" + permission +
                ", telephone=" + telephone +
                ", sex=" + sex +
                ", deleted=" + deleted +
                ", create_time=" + createTime +
                ", modify_time=" + modifyTime +
                '}';
    }
}
