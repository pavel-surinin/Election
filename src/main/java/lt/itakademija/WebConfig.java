package lt.itakademija;

import lt.itakademija.servlet.MyLoggerFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.Filter;

/**
 * Created by VytautasL on 2/20/2017.
 */
@Configuration
public class WebConfig {

    @Bean
    public Filter MyLoggerFilter() {
        return new MyLoggerFilter();
    }
}