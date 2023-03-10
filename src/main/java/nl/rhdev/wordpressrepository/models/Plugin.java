
package nl.rhdev.wordpressrepository.models;

import java.nio.file.Path;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Plugin {
    private String slug;

    @JsonIgnore
    private Path path;

    private PluginHeader header;
} 
