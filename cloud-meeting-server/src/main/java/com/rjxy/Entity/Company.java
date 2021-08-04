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
 * Description:公司实体
 */
@Data
@Entity
@Table(name = "company")
public class Company {
    @Id
    @Column(name = "id", unique = true, length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "create_time")
    @CreatedDate
    private Timestamp createTime;

    @Column(name = "modify_time")
    @LastModifiedDate
    private Timestamp modifyTime;

    //企业拥有者(一对一关系)
    @Column(name = "owner_id")
    private Integer ownerId;

    @Column(name = "c_address")
    private String cAddress;

    @Column(name = "c_type")
    private String cType;


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
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deleted=" + deleted +
                ", create_time=" + createTime +
                ", modify_time=" + modifyTime +
                ", owner=" + ownerId +
                '}';
    }
}
