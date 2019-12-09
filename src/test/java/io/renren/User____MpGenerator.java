package io.renren;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author cth
 * @date 2019/06/03
 */
@Profile("test")
public class User____MpGenerator {
	
	 /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;              
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
	
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        String prefix = "User";
        String tableName = scanner("表名");
        String table=tableName.substring(0);
        table=underline_to_camel(tableName);

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        // 生成文件的输出目录
        gc.setOutputDir(projectPath + "/src/main/java");
        // 作者
        gc.setAuthor("jgl");
        // 生成后打开目录
        gc.setOpen(false);
        // 是否开启二级缓存
        gc.setEnableCache(false);
        // 设置时间类型使用哪个包下的
        gc.setDateType(DateType.ONLY_DATE);
        // 文件命名方式
        gc.setMapperName(prefix + table + "Dao");
        gc.setXmlName(prefix + table + "Dao");
        gc.setEntityName( prefix + table + "PO");
        gc.setControllerName(prefix + table + "Controller");
        gc.setServiceName(prefix + table + "Service");
        gc.setServiceImplName(prefix + table + "ServiceImpl");
        // 主键策略
        gc.setIdType(IdType.AUTO);
        //实体属性 Swagger2 注解
        gc.setSwagger2(true);
        
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://flow.834445.net:3306/flow_834445_net?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("flow_834445_net");
        dsc.setPassword("R5EsZriZmeAMaHPc");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("io.renren.modules.app")
                .setEntity("model.po")
                .setMapper("dao")
                .setController("controller")
                .setService("service")
                .setServiceImpl("service.impl")
                .setXml("/src/main/resources/mapper/app");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                /*return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;*/
                return projectPath + "/src/main/resources/mapper/app"
                        + "/" + tableInfo.getEntityName().substring(0,tableInfo.getEntityName().length()-2) + "Dao" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 使用自定义模板，不想要生成就设置为null,如果不设置null会使用默认模板
//        templateConfig.setEntity("templates/entity2.java");
//        templateConfig.setController("templates/controller2.java");
//        templateConfig.setMapper("templates/mapper2.java");
//        templateConfig.setService("templates/service2.java");
//        templateConfig.setServiceImpl(null);
//        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setSuperEntityClass("io.renren.common.utils.BaseEntity");
        // 使用lombok
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setSuperControllerClass("io.renren.common.utils.BaseController");
        strategy.setInclude(tableName);
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
//        strategy.setFieldPrefix("ggwl_","");//  去掉前缀
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
    
    
    private static String underline_to_camel(String s) {
    	
        String[] split = s.split("_");//按空格分隔成数组
        String s2="";
        for (int i = 0; i <split.length ; i++) {
            s2 += split[i].substring(0, 1).toUpperCase()+split[i].substring(1);
        }
        System.out.print(s2+"=============");
        return s2;
    }

}


