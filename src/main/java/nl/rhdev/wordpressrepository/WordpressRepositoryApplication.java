package nl.rhdev.wordpressrepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import nl.rhdev.wordpressrepository.properties.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class WordpressRepositoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordpressRepositoryApplication.class, args);
	}

    @Bean
    CommandLineRunner init(StorageProperties properties) {
        return (args) -> {
            Logger logger = LoggerFactory.getLogger(CommandLineRunner.class);
            logger.info("Initializing storage service");

            try {
                Files.createDirectories(Paths.get(properties.getPath()));
            } catch (IOException e) {
                logger.error("Cannot create directory", e);
            }
        };
    }
}
