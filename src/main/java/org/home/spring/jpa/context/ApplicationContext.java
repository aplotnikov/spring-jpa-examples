package org.home.spring.jpa.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

@Configuration
@ComponentScan(basePackages = "org.home.spring.jpa")
@EnableJpaRepositories("org.home.spring.jpa")
public class ApplicationContext {
    @Bean
    @Profile("dev")
    public DataSource aDevDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(HSQL)
                .build();
    }

    @Bean
    @Profile("prod")
    public DataSource aProdDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource("jdbc:hsqldb:mem:test", "sa", "");
        driverManagerDataSource.setDriverClassName("org.hsqldb.jdbcDriver");

        return driverManagerDataSource;
    }

    @Bean
    public AbstractEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPersistenceUnitName("org.home.spring.jpa");
        entityManagerFactoryBean.setPackagesToScan("org.home.spring.jpa.model");

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaVendorAdapter aJpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

        adapter.setDatabase(Database.HSQL);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");

        return adapter;
    }

    @Bean
    public PersistenceAnnotationBeanPostProcessor jpaPostProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }

    @Bean
    public PlatformTransactionManager transactionManager(AbstractEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return txManager;
    }
}
