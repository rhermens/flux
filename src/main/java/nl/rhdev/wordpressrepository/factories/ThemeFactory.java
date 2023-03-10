package nl.rhdev.wordpressrepository.factories;

import java.nio.file.Path;
import java.util.Optional;
import java.util.zip.ZipFile;

import lombok.extern.slf4j.Slf4j;
import nl.rhdev.wordpressrepository.models.Theme;
import nl.rhdev.wordpressrepository.models.ThemeHeader;

@Slf4j
public class ThemeFactory extends AbstractWordPressFactory {

    public static Optional<Theme> fromPath(Path path) {
        try {
            ZipFile zipFile = new ZipFile(path.toFile());
            String slug = getRootDirectory(zipFile);
            ThemeHeader header = new ThemeHeader();
            unmarshalHeader(readHeaderFile(slug + "/style.css", zipFile), header);
            zipFile.close();

            return Optional.of(new Theme(slug, path, header));
        } catch (Exception e) {
            log.error("Cannot create Theme from path", e);

            return Optional.empty();
        }
    }
}
