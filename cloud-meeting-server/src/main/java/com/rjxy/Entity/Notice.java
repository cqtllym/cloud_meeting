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

@Data
@Entity
@Table(name = "notice")
public class Notice implements Cloneable {
    @Id
    @Column(name = "id", unique = true, length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "theme")
    private String theme;

    @Column(name = "type")
    private Integer type;

    @Column(name = "is_dealt")
    private Boolean isDealt = false;

    @Column(name = "from_id")
    private Integer fromId;

    @Column(name = "from_type")
    private Boolean fromType;

    @Column(name = "to_id")
    private Integer toId;

    @Column(name = "to_type")
    private Boolean toType;

    @Column(name = "result")
    private Boolean result;

    @Column(name = "create_time")
    @CreatedDate
    private Timestamp createTime;

    @Column(name = "modify_time")
    @LastModifiedDate
    private Timestamp modifyTime;

    public String getCreateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(createTime);
    }

    public String getModifyTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(modifyTime);
    }

    @Override
    public Notice clone() throws CloneNotSupportedException {
        return (Notice) super.clone();
    }

    public static void main(String[] args) {
        Notice deleteNotice = new Notice();
        deleteNotice.setTheme("公司" + " 已注销 ，您已被移出企业 ");
        deleteNotice.setType(3);
        deleteNotice.setIsDealt(false);
        deleteNotice.setFromId(1);
        deleteNotice.setFromType(false);
        try {
            Notice notice = deleteNotice.clone();
            System.out.println(notice.theme);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
