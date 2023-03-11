
package nl.rhdev.wordpressrepository.models;

import java.nio.file.Path;
import java.util.Optional;
import java.util.zip.ZipFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rhdev.wordpressrepository.marshal.HeaderMarshaller;
import nl.rhdev.wordpressrepository.zip.WordPressZip;

@Data
@AllArgsConstructor
@Getter
@Setter
@Slf4j
public class Theme {
    private String slug;

    @JsonIgnore
    private Path path;

    private ThemeHeader header;

    public static Optional<Theme> fromPath(Path path) {
        try {
            WordPressZip zipFile = new WordPressZip(new ZipFile(path.toFile()));
            String slug = zipFile.getRootDirectory();
            HeaderMarshaller<ThemeHeader> marshaller = new HeaderMarshaller<>();
            String headerContents = zipFile.readFile(Path.of(slug, "style.css"));
            ThemeHeader header = new ThemeHeader();
            marshaller.unmarshal(headerContents, header);

            zipFile.close();

            return Optional.of(new Theme(slug, path, header));
        } catch (Exception e) {
            log.error("Cannot create plugin from path", e);

            return Optional.empty();
        }
    }
}
