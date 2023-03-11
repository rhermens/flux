
package nl.rhdev.wordpressrepository.models;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
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

    private Boolean badPractise = false;

    private PluginHeader header;

    public static Optional<Plugin> fromPath(Path path) {
        try {
            WordPressZip zipFile = new WordPressZip(new ZipFile(path.toFile()));
            String slug = zipFile.getRootDirectory();
            HeaderMarshaller<PluginHeader> marshaller = new HeaderMarshaller<>();
            Plugin plugin = new Plugin();

            plugin.setSlug(slug);
            plugin.setPath(path);

            try {
                String headerContents = zipFile.readFile(Path.of(slug, slug + ".php"));
                PluginHeader header = new PluginHeader();
                marshaller.unmarshal(headerContents, header);

                plugin.setHeader(header);
            } catch (NoSuchElementException e) {
                plugin.setBadPractise(true);

                String headerContents = zipFile.getZipFile().stream()
                    .filter(entry -> !entry.isDirectory() && entry.getName().endsWith(".php"))
                    .map(entry -> {
                        try {
                            return zipFile.readFile(Path.of(entry.getName()));
                        } catch (Exception innerEx) {
                            return "";
                        }
                    })
                    .filter(content -> content.toUpperCase().contains("PLUGIN NAME:"))
                    .collect(Collectors.joining(" "));

                log.info("Joined content.. {}", headerContents);

                PluginHeader header = new PluginHeader();
                marshaller.unmarshal(headerContents, header);

                plugin.setHeader(header);
            } 

            zipFile.close();

            return Optional.of(plugin);
        } catch (Exception e) {
            log.error("Cannot create plugin from path", e);

            return Optional.empty();
        }
    }
} 
