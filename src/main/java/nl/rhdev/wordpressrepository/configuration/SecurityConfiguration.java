package nl.rhdev.wordpressrepository.configuration;

import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

import lombok.extern.slf4j.Slf4j;
import nl.rhdev.wordpressrepository.properties.InitialUserProperties;
import nl.rhdev.wordpressrepository.ui.views.LoginView;

@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfiguration extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            .requestMatchers("/public/**").permitAll();

        super.configure(http);

        setLoginView(http, LoginView.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource, InitialUserProperties initialUserProperties) {
        JdbcUserDetailsManager userManager = new JdbcUserDetailsManager(dataSource);
        if (!userManager.userExists(initialUserProperties.getUsername())) {
            userManager.createUser(
                User.withUsername(initialUserProperties.getUsername())
                    .password(passwordEncoder().encode(initialUserProperties.getPassword()))
                    .roles("ADMIN")
                    .build()
            );

            log.info("\n\n Created initial admin user with password: {} \n", initialUserProperties.getPassword());
        }

        return userManager;
    }
}
