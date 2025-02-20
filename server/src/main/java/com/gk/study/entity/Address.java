package com.gk.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@TableName("b_address")
public class Address implements Serializable {
    @Id
    @IsAutoIncrement
    @TableId(value = "id",type = IdType.AUTO)
    public Long id;
    @TableField
    public String name;
    @TableField
    public String mobile;
    @TableField
    public String description;
    @TableField
    public String def; // 默认
    @TableField
    public String createTime;
    @TableField
    public String userId;

}
