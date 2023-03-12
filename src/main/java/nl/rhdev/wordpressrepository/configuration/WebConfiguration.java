package nl.rhdev.wordpressrepository.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import nl.rhdev.wordpressrepository.interceptors.LicensedInterceptor;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    private final LicensedInterceptor licensedInterceptor;

    @Autowired
    public WebConfiguration(LicensedInterceptor licensedInterceptor) {
        this.licensedInterceptor = licensedInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(licensedInterceptor);
    }
}
