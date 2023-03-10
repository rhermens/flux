package nl.rhdev.wordpressrepository.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.rhdev.wordpressrepository.annotations.HeaderKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeHeader {

    @HeaderKey(normalizedKey = "THEME NAME")
    public String name;

    @HeaderKey(normalizedKey = "THEME URI")
    public String uri;

    @HeaderKey(normalizedKey = "DESCRIPTION")
    public String description;

    @HeaderKey(normalizedKey = "VERSION")
    public String version;

    @HeaderKey(normalizedKey = "AUTHOR")
    public String author;

    @HeaderKey(normalizedKey = "AUTHOR URI")
    public String authorUri;

    @HeaderKey(normalizedKey = "TEXT DOMAIN")
    public String textDomain;

    @HeaderKey(normalizedKey = "DOMAIN PATH")
    public String domainPath;

    @HeaderKey(normalizedKey = "UPDATE URI")
    public String updateUri;

    @HeaderKey(normalizedKey = "TAGS")
    public String tags;
}
