package meli.webapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * Configuration class. Add Spring Beans here.
 */
@Configuration
@ComponentScan({"meli.webapp.controller", "meli.services"})
public class WebConfig {

    // Add your Spring Beans here...
}