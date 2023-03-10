
package nl.rhdev.wordpressrepository.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("storage")
public class StorageProperties {

    @Getter
    @Setter
    private String path = "upload-dir";
}
