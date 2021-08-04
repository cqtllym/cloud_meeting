package com.rjxy.Model;

import com.rjxy.Entity.Notice;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class NoticeVo {

    private Integer id;

    private String theme;

    private String type;

    private String isDealt;

    private Integer fromId;

    private String fromType;

    private String fromName;

    private Integer toId;

    private String toType;

    private String toName;

    private Boolean result;

    private String createTime;

    public static NoticeVo setNoticeVo(Notice notice, String fromName, String toName) {
        NoticeVo nv = new NoticeVo();
        nv.setId(notice.getId());
        nv.setTheme(notice.getTheme());
        switch (notice.getType()) {
            case 1:
                nv.setType("同意拒绝");
                break;
            case 2:
                nv.setType("加入会议");
                break;
            case 3:
                nv.setType("只读通知");
                break;
            default:
                nv.setType("无效通知");
                break;
        }
        nv.setIsDealt(notice.getIsDealt() ? "已处理" : "未处理");
        nv.setFromId(notice.getFromId());
        nv.setFromType(notice.getFromType() ? "企业" : "个人");
        nv.setFromName(fromName);
        nv.setToId(notice.getToId());
        nv.setToType(notice.getToType() ? "企业" : "个人");
        nv.setToName(toName);
        nv.setResult(notice.getResult());
        nv.setCreateTime(notice.getCreateTime());

        return nv;

    }

}
