
package nl.rhdev.wordpressrepository.repositories;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import nl.rhdev.wordpressrepository.models.Plugin;
import nl.rhdev.wordpressrepository.properties.StorageProperties;

@Repository
@Slf4j
public class PluginRepository {

    private final StorageProperties properties;

    @Autowired
    public PluginRepository(StorageProperties properties) {
        this.properties = properties;
    }

    public Stream<Plugin> stream() {
        try {
            return Files.list(properties.getPluginPath())
                .filter(p -> p.toFile().isFile() && p.toString().endsWith(".zip"))
                .map(Plugin::fromPath)
                .filter(Optional::isPresent)
                .map(Optional::get);
        } catch (IOException e) {
            log.error("Cannot list plugins: {}", e.getMessage());
            return Stream.empty();
        }
    }

    public Optional<Plugin> findBySlug(String slug) {
        return this.stream()
            .filter(p -> p.getSlug().equals(slug))
            .findFirst();
    }
}
