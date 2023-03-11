package nl.rhdev.wordpressrepository;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;
import nl.rhdev.wordpressrepository.properties.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@Slf4j
public class WordpressRepositoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordpressRepositoryApplication.class, args);
	}

    @Bean
    CommandLineRunner init(StorageProperties properties) {
        return (args) -> {
            log.info("Initializing storage service");

            try {
                Files.createDirectories(properties.getPath());
                Files.createDirectories(properties.getThemePath());
                Files.createDirectories(properties.getPluginPath());
            } catch (IOException e) {
                log.error("Cannot create directory", e);
            }
        };
    }
}
