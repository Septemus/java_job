package com.gk.study.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@TableName("b_classification")
public class Classification implements Serializable {
    @Id
    @IsAutoIncrement
    @TableId(value = "id",type = IdType.AUTO)
    public Long id;
    @TableField
    public String title;
    @TableField
    public Timestamp createTime;

}
