package erp.javaguides.erpbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import erp.javaguides.erpbackend.utility.UtilityService;

@Configuration
public class AppConfig {
    
    @Bean
    public UtilityService utilityService() {
        return new UtilityService();
    }
}
