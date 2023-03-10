package nl.rhdev.wordpressrepository.repositories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import nl.rhdev.wordpressrepository.factories.ThemeFactory;
import nl.rhdev.wordpressrepository.models.Theme;
import nl.rhdev.wordpressrepository.properties.StorageProperties;

@Repository
@Slf4j
public class ThemeRepository {

    private final StorageProperties properties;

    @Autowired
    public ThemeRepository(StorageProperties properties) {
        this.properties = properties;
    }

    public Stream<Theme> stream() {
        try {
            return Files.list(Paths.get(properties.getPath(), "themes"))
                .filter(p -> p.toFile().isFile() && p.toString().endsWith(".zip"))
                .map(ThemeFactory::fromPath)
                .filter(Optional::isPresent)
                .map(Optional::get);
        } catch (IOException e) {
            log.error("Cannot list Themes: {}", e.getMessage());
            return Stream.empty();
        }
    }

    public Optional<Theme> findBySlug(String slug) {
        return this.stream()
            .filter(p -> p.getSlug().equals(slug))
            .findFirst();
    }
}
