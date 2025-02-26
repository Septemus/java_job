package com.gk.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@TableName("b_thing")
public class Thing implements Serializable {
    @Id
    @IsAutoIncrement
    @TableId(value = "id",type = IdType.AUTO)
    public Long id;
    @TableField
    public String title;
    @TableField(exist = false)
    public String cover;
    @TableField
    public String description;
    @TableField
    public String education;
//    @TableField
//    public String status;
    @TableField
    public Timestamp createTime;
    @TableField
    public String location;
    @TableField
    public String salary;
    @TableField
    public String workExpe;
//    @TableField
//    public String pv;
    @TableField
    public int recommendCount=0;
    @TableField
    public int wishCount=0;
    @TableField
    public int collectCount=0;
    @TableField
    public Long classificationId;

    @TableField(exist = false)
    public List<String> tags; // 标签

    @TableField(exist = false)
    public List<Long> tagids; // 标签

//    @TableField(exist = false)
//    public MultipartFile imageFile;

    @TableField
    public String userId;
    @TableField
    public Long companyId;
    @TableField(exist = false)
    public String companyName;

}
