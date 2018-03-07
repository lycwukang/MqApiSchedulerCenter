package com.ms.job.config;

import com.ms.job.dao.MqApiSchedulerReadDao;
import com.mysql.jdbc.Driver;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@MapperScan(basePackageClasses = MqApiSchedulerReadDao.class)
public class JdbcConfig {

    @Bean
    @Profile("dev")
    public JdbcProperty jdbcProperty() {
        JdbcProperty property = new JdbcProperty();
        property.setJdbcUrl("jdbc:mysql://172.30.1.57:3306/msmall_cmbc?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
        property.setJdbcUsername("b2c");
        property.setJdbcPassword("123456");
        return property;
    }

    @Bean
    @Autowired
    public DataSource jdbcDataSource(JdbcProperty jdbcProperty) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUrl(jdbcProperty.getJdbcUrl());
        dataSource.setUsername(jdbcProperty.getJdbcUsername());
        dataSource.setPassword(jdbcProperty.getJdbcPassword());
        return dataSource;
    }

    @Bean
    @Resource(name = "jdbcDataSource")
    public FactoryBean<SqlSessionFactory> jdbcSessionFactory(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:" + MqApiSchedulerReadDao.class.getPackage().getName().replace(".", "/") + "/**/*.xml"));
        return factoryBean;
    }
}