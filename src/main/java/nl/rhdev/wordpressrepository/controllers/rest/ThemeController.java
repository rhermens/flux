package nl.rhdev.wordpressrepository.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.rhdev.wordpressrepository.annotations.Licensed;
import nl.rhdev.wordpressrepository.models.Theme;
import nl.rhdev.wordpressrepository.repositories.ThemeRepository;

@RestController
@RequestMapping("/api/v1/themes")
public class ThemeController {

    private ThemeRepository themeRepository;

    @Autowired
    public ThemeController(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    @GetMapping({"", "/"})
    public List<Theme> listPlugins() {
        return themeRepository.stream().toList();
    }

    @GetMapping({"/{slug}", "/{slug}/"})
    public Theme get(@PathVariable String slug) {
        return themeRepository.findBySlug(slug).orElseThrow();
    }

    @GetMapping(path = {"/{slug}.zip"}, produces = { "application/zip" })
    @Licensed
    public FileSystemResource getDownload(@PathVariable String slug) {
        return themeRepository.findBySlug(slug).map(p -> new FileSystemResource(p.getPath())).orElseThrow();
    }
}
