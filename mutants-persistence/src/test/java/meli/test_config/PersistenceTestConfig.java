package meli.test_config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class. Add Spring Beans here.
 */
@Configuration
@ComponentScan({"meli.persistence",})
public class PersistenceTestConfig {

    // Add your Spring Beans here...
}