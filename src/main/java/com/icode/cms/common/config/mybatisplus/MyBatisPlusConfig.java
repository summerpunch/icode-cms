package com.icode.cms.common.config.mybatisplus;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Title: mybatis-plus配置相关<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Mail: summerpunch@163.com<br>
 * Date: 2019/3/6 10:02<br>
 */
@Configuration
@MapperScan("com.icode.cms.repository.mapper*")
public class MyBatisPlusConfig {

    /**
     * Title: mybatis-plus SQL执行效率插件【生产环境可以关闭】<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/6 10:02<br>
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     * Title: mybatis-plus分页插件<br>
     * Description: 文档：http://mp.baomidou.com<br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/6 10:02<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        //开启 PageHelper 的支持
        return new PaginationInterceptor().setLocalPage(true);
    }

}
