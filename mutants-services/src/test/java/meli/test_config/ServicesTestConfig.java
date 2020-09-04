package meli.test_config;


import meli.interfaces.DetectionService;
import meli.interfaces.StatisticsDao;
import meli.interfaces.StatisticsService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class. Add Spring Beans here.
 */
@Configuration
@ComponentScan({"meli.services",})
public class ServicesTestConfig {
    @Bean
    public StatisticsDao propertyDao(){
        return Mockito.mock(StatisticsDao.class);
    }

}