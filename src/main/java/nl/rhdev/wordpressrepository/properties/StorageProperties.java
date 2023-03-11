
package nl.rhdev.wordpressrepository.properties;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("storage")
public class StorageProperties {

    @Getter
    @Setter
    private Path path = Path.of("upload-dir");

    @Getter
    @Setter
    private Path pluginPath = Path.of(path.toString(), "plugins");

    @Getter
    @Setter
    private Path themePath = Path.of(path.toString(), "themes");
}
