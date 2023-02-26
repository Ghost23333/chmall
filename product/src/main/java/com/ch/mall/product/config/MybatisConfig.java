package com.ch.mall.product.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName MabatisConfig.java
 * @Description Mybatis配置类
 * @createTime 2022年11月19日 16:11:00
 */
@Configuration
@MapperScan("com.ch.mall.product.dao")
@EnableTransactionManagement//开启事务
public class MybatisConfig {

    //分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setOverflow(true);
        return paginationInterceptor;
    }

}
