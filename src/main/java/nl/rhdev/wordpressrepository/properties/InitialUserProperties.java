package nl.rhdev.wordpressrepository.properties;

import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("initial-user")
public class InitialUserProperties {

    @Getter
    @Setter
    private String username = "admin";

    @Getter
    @Setter
    private String password = UUID.randomUUID().toString();
}
