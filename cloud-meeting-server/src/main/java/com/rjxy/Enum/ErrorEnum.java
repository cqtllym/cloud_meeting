package com.rjxy.Enum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorEnum {
    // 登录态相关
    SessionNotExistError(300, "session失效或不存在"),
    PasswordWrongError(301, "账号密码错误"),

    //通用方法
    IllegalNullFieldError(303, "违规的空字段"),


    // 用户相关
    UserHasCompanyError(400, "用户已经在企业中"),
    UserNotExistError(401, "用户不存在或已注销"),
    UserNameExistError(402, "用户名已占用，请勿重复注册"),
    UserModifyError(403, "用户信息更改错误"),
    UserQuitCompanyError(404, "用户信息与将要退出的企业不符合"),
    AdminSettingError(405, "管理员权限变更失败，变更身份不能与原身份一致或该员工不属于本公司"),
    UserNotHaveCompanyError(406, "用户不在企业中"),


    // 会议相关
    CreateMeetingError(500, "创建会议失败"),
    MeetingNumberExpired(501, "会议号失效"),
    DeleteMeetingError(502, "删除会议失败"),
    UpdateMeetingError(503, "修改会议失败"),

    // 企业相关
    CompanyNotExistError(600, "企业不存在或已注销"),
    FireUserNotInCompanyError(601, "您不能开除不属于您公司的员工或企业的创建者"),
    ModifyCompanyError(602,"修改企业信息失败"),

    //会议室相关
    RoomActiveChangeError(701, "状态更改失败"),
    RoomDeleteError(702, "会议室删除失败"),
    RoomModifyError(703, "会议室信息更改失败"),
    RoomOccupiedError(704, "会议室已被占用"),
    RoomReservationNotExistError(705, "会议室预约不存在"),
    RoomReservationHasStartedError(706, "会议室预约已到预约时间，无法更改"),
    RoomReservationCancelFailedError(707, "会议室预约取消失败"),
    InvalidEnterTimeError(708, "非有效进入会议室时间"),
    InvalidParticipantError(709, "非参会人无法进入会议室"),

    // 部门相关
    DepartmentExistError(800, "部门已存在"),
    DepartmentNotExistError(801, "部门不存在或已删除"),
    JoinDepartmentError(802, "加入部门失败"),
    ModifyDepartmentError(803, "修改部门名称失败");

    private final int code;
    private final String errMsg;

    public int getCode() {
        return code;
    }

    public String getErrMsg() {
        return errMsg;
    }

}
