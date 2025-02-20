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

@Data
@TableName("b_banner")
public class Banner implements Serializable {
    @Id
    @IsAutoIncrement
    @TableId(value = "id",type = IdType.AUTO)
    public Long id;
    @TableField
    public String image;
    @TableField(exist = false)
    public MultipartFile imageFile;
    @TableField
    public Long thingId;
    @TableField
    public String createTime;

}
