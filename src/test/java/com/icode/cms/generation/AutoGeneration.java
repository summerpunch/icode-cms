package com.icode.cms.generation;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.Data;
import org.junit.Test;

/**
 * Title: 自动生成代码<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Mail: summerpunch@163.com<br>
 * Date: 2019/3/7 15:03<br>
 */
public class AutoGeneration {


    @Test
    public void auto() {
        DBConfig dbConfig = new DBConfig();
        generateByTables(
                dbConfig,
                "cms_dictionary"
        );
    }

    @Data
    class DBConfig {
        String url = "jdbc:mysql://localhost/icode-cms?serverTimezone=Asia/Shanghai&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        String name = "root";
        String password = "123456";
        String driverName = "com.mysql.jdbc.Driver";
        String author = "XiaChong";
        /**
         * 生成的包名
         */
        String packageName = "com.icode.cms";
        /**
         * 代码生成路径
         */
        String paths = "D:\\ideaWorkspace\\icode-cms\\src\\main\\java";
        /**
         * 设置生成的service接口的名字的首字母是否为I
         */
        Boolean serviceNameStartWithI = true;

        /**
         * 实体是否为lombok模型（默认 false）
         */
        Boolean lombokModel = false;
    }

    private void generateByTables(DBConfig dbConfig, String... tableNames) {

        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
        config
                //是否支持AR模式
                .setActiveRecord(true)
                //作者
                .setAuthor(dbConfig.getAuthor())
                //生成路径
                .setOutputDir(dbConfig.getPaths())
                //文件覆盖
                .setFileOverride(true)
                //XMLResultMap
                .setBaseResultMap(true)
                //XMLcolumList
                .setBaseColumnList(true)
                //XML二级缓存
                .setEnableCache(false)
                //主键策略
                .setIdType(IdType.AUTO);
        if (!dbConfig.getServiceNameStartWithI()) {
            //设置生成的service接口的名字的首字母是否为I
            config.setServiceName("%sService");
        }


        //2. 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        //设置数据库类型
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbConfig.getUrl())
                .setUsername(dbConfig.getName())
                .setPassword(dbConfig.getPassword())
                .setDriverName(dbConfig.getDriverName());

        //3. 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                //全局大写命名
                .setCapitalMode(true)
                //实体是否为lombok模型（默认 false）
                .setEntityLombokModel(dbConfig.getLombokModel())
                //指定表名字段名是否使用下划线
                .setDbColumnUnderline(true)
                //数据库表映射到实体的命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                //修改替换成你需要的表名，多个表名传数组
                .setInclude(tableNames);

        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent(dbConfig.getPackageName())
                .setController("controller")
                .setEntity("repository.entity");

        //5. 整合配置
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(pkConfig);

        //6. 执行
        ag.execute();
    }
}