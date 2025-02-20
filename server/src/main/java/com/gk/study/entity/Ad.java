package com.gk.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
import java.io.File;
import java.io.Serializable;

@Data
@TableName("b_ad")
public class Ad implements Serializable {
    @Id
    @IsAutoIncrement
    @TableId(value = "id", type = IdType.AUTO)
    public Long id;
    @TableField
    public String image;
    @TableField(exist = false)
    public MultipartFile imageFile;
    @TableField
    public String link;
    @TableField
    public String createTime;

}
