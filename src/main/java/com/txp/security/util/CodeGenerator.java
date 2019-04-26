package com.txp.security.util;

import java.util.Collections;
import java.util.HashMap;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.util.Assert;

public class CodeGenerator {
    public static void main(String[] args) {
        Assert.notNull(null,"使用后关闭，别一不下心覆盖了原先的代码！");
        generator(
                //可以从第一个module开始写路径
                //cloud_demo为工作空间project
                //cd_provider为第一层module，则可写为
                //"cd_provider/cd_dept"
                "../security_demo",   // 项目所在路径
                "txp",                          // 作者
                "com.txp.security",     // 包名
                "",                              // 表前缀
                new String[]{"user"},            // 表名
                "com.mysql.jdbc.Driver", "root", "root", "jdbc:mysql://127.0.0.1/security");
    }

    /**
     * MySQL generate
     *
     * @param author        作者
     * @param packageParent 包配置，父级包
     * @param tablePrefix   需要去除的表前缀，没有传空
     * @param include       需要生成代码的表集合，可不传默认生成全部表
     * @param driverName    数据库驱动
     * @param username      数据库用户名
     * @param password      数据库密码
     * @param url           数据库地址
     */
    public static void generator(String moduleName, String author, String packageParent,
                                 String tablePrefix, String[] include, String driverName, String username, String password, String url) {
        // 代码生成器
        new AutoGenerator()
                .setGlobalConfig(setGlobalConfig(moduleName, author))
                .setDataSource(setDataSourceConfig(driverName, username, password, url))
                .setStrategy(setStrategyConfig(tablePrefix, include))
                .setPackageInfo(setPackageConfig(packageParent))
                .setCfg(
                        new InjectionConfig() {
                            public void initMap() {
                                this.setMap(new HashMap<>(16));
                            }
                        }.setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
                            public String outputFile(TableInfo tableInfo) {
                                return System.getProperty("user.dir") + "/" + moduleName + "/src/main/java/" + packageParent.replace('.', '/') + "/mapper/xml/" + tableInfo.getEntityName() + "Mapper.xml";
                            }
                        }))
                ).setTemplate(setTemplateConfig()).execute();
    }

    /**
     * 全局配置
     *
     * @return
     */
    private static GlobalConfig setGlobalConfig(String moduleName, String author) {
        return new GlobalConfig()
                // 输出目录
                .setOutputDir(System.getProperty("user.dir") + "/" + moduleName + "/src/main/java")
                .setFileOverride(true)      // 是否覆盖文件
                .setActiveRecord(false)     // 开启 activeRecord 模式
                .setEnableCache(false)      // XML 二级缓存
                .setBaseResultMap(true)     // XML ResultMap
                .setBaseColumnList(true)    // XML columList
                .setKotlin(false)           // 是否生成 kotlin 代码
                .setAuthor(author)          // 作者
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                .setEntityName("%s")
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController");
    }

    /**
     * 数据源配置
     *
     * @return
     */
    private static DataSourceConfig setDataSourceConfig(String driverName, String username, String password, String url) {
        return new DataSourceConfig().setDbType(DbType.MYSQL)
                .setTypeConvert(new MySqlTypeConvert())
                .setDriverName(driverName)
                .setUsername(username)
                .setPassword(password)
                .setUrl(url);
    }

    /**
     * 策略配置
     *
     * @return
     */
    private static StrategyConfig setStrategyConfig(String tablePrefix, String[] include) {
        StrategyConfig config = new StrategyConfig();
        // 全局大写命名
        config.setCapitalMode(false)
                // 表名生成策略
                .setNaming(NamingStrategy.underline_to_camel)
                // 自定义 mapper 父类
                .setSuperMapperClass("com.baomidou.mybatisplus.core.mapper.BaseMapper")
                // 自定义 service 实现类父类
                .setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl")
                // 自定义 service 接口父类
                .setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService")
                // 【实体】是否为构建者模型（默认 false）
                .setEntityBuilderModel(false)
                // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                .setEntityLombokModel(true)
                // Boolean类型字段是否移除is前缀处理
                .setEntityBooleanColumnRemoveIsPrefix(true)
                .setRestControllerStyle(true);
        // .setControllerMappingHyphenStyle(true)
        if (tablePrefix != null && tablePrefix.trim().length() > 0) {
            config.setTablePrefix(tablePrefix);
        }
        if (null != include && include.length > 0) {
            config.setInclude(include);
        }
        return config;
    }

    /**
     * 包配置
     *
     * @return
     */
    private static PackageConfig setPackageConfig(String packageParent) {
        return new PackageConfig()
                .setParent(packageParent)
                .setEntity("model.entity")
                .setMapper("mapper")
                .setService("service")
                .setServiceImpl("service.impl")
                .setController("web.controller");
    }

    /**
     * @return
     */
    private static TemplateConfig setTemplateConfig() {
        return new TemplateConfig().setXml(null);
    }
}