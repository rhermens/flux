
package nl.rhdev.wordpressrepository.models;

import java.nio.file.Path;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class Theme {
    private String slug;

    @JsonIgnore
    private Path path;

    private ThemeHeader header;
}
