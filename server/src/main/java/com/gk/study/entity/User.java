package com.gk.study.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
@TableName("b_user")
public class User implements Serializable {
    public static final int NormalUser = 1;
    public static final int DemoUser = 2;
    public static final int AdminUser = 3;

    @TableId(value = "id",type = IdType.AUTO)
    public String id;
    @TableField
    public String username;
    @TableField
    public String password;
    @TableField(exist = false)
    public String rePassword;
    @TableField
    public String nickname;
    @TableField
    public String mobile;
    @TableField
    public String email;
    @TableField
    public String description;
    @TableField
    public String role;
    @TableField
    public String status;
    @TableField
    public String score;
    @TableField
    public String avatar;
    @TableField(exist = false)
    public MultipartFile avatarFile;
    @TableField
    public String token;
    @TableField
    public String createTime;
    @TableField
    public String pushEmail;
    @TableField
    public String pushSwitch;
    @TableField
    public String phoneNumber;
    @TableField
    @Column
    @EnumValue
    @Enumerated(value = EnumType.STRING)
    @ColumnType(MySqlTypeConstant.VARCHAR)
    public Gender gender;

    public static class WxUserInfo {
        public String avatarUrl;
        public String nickName;
        public String city;
        public String province;
        public String country;
        public Gender gender;
    }
    public static class WxUserLoginForm {
        public String code;
        public WxUserInfo userInfo;
    }

}
