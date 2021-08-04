package com.rjxy.Entity;

import lombok.Data;

import javax.persistence.*;
/**
 * @author: Joheey
 * Date: 2021/7/1
 * Time: 20:00
 * Description:会议室实体，预定义的实体，后续迭代开发中在写具体的功能。
 *              其中会议室可以不绑定公司与部门，方便前期开发。
 */
@Data
@Entity
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "id", unique = true, length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "status")
    private Boolean status = false;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "deleted")
    private Boolean deleted = false;

}
