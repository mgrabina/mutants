package meli.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration class. Add Spring Beans here.
 */
@Configuration
@ComponentScan({"meli.webapp.controller", "meli.persistence", "meli.services"})
@EnableTransactionManagement
public class WebConfig {

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("meli.models");
        factoryBean.setDataSource(dataSource());

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        final Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.default_schema", "public");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
        properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");	// For boot performance
        properties.setProperty("format_sql", "true");
        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);


//		PROD
//        String url="jdbc:postgresql://ec2-54-197-230-161.compute-1.amazonaws.com:5432/d92oufhvuh3bv6?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
//        String username="ljvtrxjswswggg";
//        String password="f7dd267936bf25c605eff67f009bb8ff4b8d5bcd7e1386948ba19e0de8b1b6b6";

//		DEV
//		String url= "jdbc:postgresql://localhost:5432/mutants";
		//PROD
		String url= "jdbc:postgresql://mutants.ca6kkmjlyty6.us-east-1.rds.amazonaws.com:5432/ebdb";



		String username="meli";
		String password="secret_password";

        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);

//        Resource resource = new ClassPathResource("schema.sql");
//        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
//        databasePopulator.execute(ds);

        return ds;
    }

}