package nl.rhdev.wordpressrepository.factories;

import java.nio.file.Path;
import java.util.Optional;
import java.util.zip.ZipFile;

import lombok.extern.slf4j.Slf4j;
import nl.rhdev.wordpressrepository.models.Plugin;
import nl.rhdev.wordpressrepository.models.PluginHeader;

@Slf4j
public class PluginFactory extends AbstractWordPressFactory {

    public static Optional<Plugin> fromPath(Path path) {
        try {
            ZipFile zipFile = new ZipFile(path.toFile());
            String slug = getRootDirectory(zipFile);
            PluginHeader header = new PluginHeader();
            unmarshalHeader(readHeaderFile(slug + "/" + slug + ".php", zipFile), header);
            zipFile.close();

            return Optional.of(new Plugin(slug, path, header));
        } catch (Exception e) {
            log.error("Cannot create plugin from path", e);

            return Optional.empty();
        }
    }
}
