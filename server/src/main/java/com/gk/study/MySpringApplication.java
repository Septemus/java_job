package com.gk.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lengqin1024(微信)
 * @email net936@163.com
 */
@SpringBootApplication
@ComponentScan(value = {"com.gitee.sunchenbin.mybatis.actable.manager.*","com.gk.study.*"})
@MapperScan(value = {"com.gitee.sunchenbin.mybatis.actable.dao.*","com.gk.study.mapper"})
public class MySpringApplication {

    public static void main(String[] args) {
        try{
            SpringApplication.run(MySpringApplication.class, args);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
