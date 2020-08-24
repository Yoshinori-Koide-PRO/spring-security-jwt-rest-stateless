package com.lynas.config;

import com.lynas.model.AppUser;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by sazzad on 9/7/15
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    @Autowired
    private ApplicationContext appContext;



    @Bean
    public HikariDataSource getDataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDataSourceClassName("org.postgresql.jdbc3.Jdbc3SimpleDataSource");
        dataSource.addDataSourceProperty("databaseName", "demorest");
        dataSource.addDataSourceProperty("portNumber", "5432");
        dataSource.addDataSourceProperty("serverName", "psql");
        dataSource.addDataSourceProperty("user", "postgres");
        dataSource.addDataSourceProperty("password", "postgres");
        return dataSource;
    }


    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager manager = new HibernateTransactionManager();
        manager.setSessionFactory(hibernate5SessionFactoryBean().getObject());
        return manager;
    }

    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean hibernate5SessionFactoryBean(){
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(appContext.getBean(HikariDataSource.class));
        localSessionFactoryBean.setAnnotatedClasses(
                AppUser.class
        );

        Properties properties = new Properties();
        properties.put("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        //properties.put("hibernate.current_session_context_class","thread");
        properties.put("hibernate.hbm2ddl.auto","update");

        localSessionFactoryBean.setHibernateProperties(properties);
        return localSessionFactoryBean;
    }
}
