
package nl.rhdev.wordpressrepository.models;

import java.nio.file.Path;
import java.util.Optional;
import java.util.zip.ZipFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rhdev.wordpressrepository.marshal.HeaderMarshaller;
import nl.rhdev.wordpressrepository.zip.WordPressZip;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class Plugin {
    private String slug;

    @JsonIgnore
    private Path path;

    private PluginHeader header;

    public static Optional<Plugin> fromPath(Path path) {
        try {
            WordPressZip zipFile = new WordPressZip(new ZipFile(path.toFile()));
            String slug = zipFile.getRootDirectory();
            HeaderMarshaller<PluginHeader> marshaller = new HeaderMarshaller<>();
            String headerContents = zipFile.readFile(Path.of(slug, slug + ".php"));
            PluginHeader header = new PluginHeader();
            marshaller.unmarshal(headerContents, header);

            zipFile.close();

            return Optional.of(new Plugin(slug, path, header));
        } catch (Exception e) {
            log.error("Cannot create plugin from path", e);

            return Optional.empty();
        }
    }
} 
