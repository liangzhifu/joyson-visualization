package com.joyson.visualization.common.config;

import com.github.pagehelper.PageHelper;
import com.joyson.visualization.common.constant.ProjectConstant;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by L on 2018/7/16.
 */
@Configuration
public class MybatisConfigurer implements TransactionManagementConfigurer {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage(ProjectConstant.MODEL_PACKAGE);
        //添加XML目录
        ResourcePatternResolver configResolver = new PathMatchingResourcePatternResolver();
        factory.setConfigLocation(configResolver.getResource("classpath:mybatis-config.xml"));

        //配置分页插件，详情请查阅官方文档
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        //分页尺寸为0时查询所有纪录不再执行分页
        properties.setProperty("pageSizeZero", "true");
        //页码<=0 查询第一页，页码>=总页数查询最后一页
        properties.setProperty("reasonable", "true");
        //支持通过 Mapper 接口参数来传递分页参数
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);

        //添加插件
        factory.setPlugins(new Interceptor[]{pageHelper});

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        SqlSessionFactory sqlSessionFactory = factory.getObject();
        //Mybatis自动支持驼峰
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactory;
    }

    /**
     * 加入事物管理
     * @param dataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager makeDataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return manager;
    }

    /**
     * 事务配置,考虑多数据源情况下
     * @return
     */
    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager();
    }
}
