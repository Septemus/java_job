package com.gk.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName("b_tag")
public class Tag implements Serializable {
    @Id
    @IsAutoIncrement
    @TableId(value = "id",type = IdType.AUTO)
    public Long id;
    @TableField
    public String title;
    @TableField
    public Timestamp createTime;

}
