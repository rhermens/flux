package nl.rhdev.wordpressrepository.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.rhdev.wordpressrepository.annotations.Licensed;
import nl.rhdev.wordpressrepository.models.Plugin;
import nl.rhdev.wordpressrepository.repositories.PluginRepository;

@RestController
@RequestMapping("/api/v1/plugins")
public class PluginController {

    private final PluginRepository pluginRepository;

    @Autowired
    public PluginController(PluginRepository pluginRepository) {
        this.pluginRepository = pluginRepository;
    }

    @GetMapping({"", "/"})
    public List<Plugin> listPlugins() {
        return pluginRepository.stream().toList();
    }

    @GetMapping({"/{slug}", "/{slug}/"})
    public Plugin get(@PathVariable String slug) {
        return pluginRepository.findBySlug(slug).orElseThrow();
    }

    @GetMapping(path = {"/{slug}.zip"}, produces = { "application/zip" })
    @Licensed
    public FileSystemResource getDownload(@PathVariable String slug) {
        return pluginRepository.findBySlug(slug).map(p -> new FileSystemResource(p.getPath())).orElseThrow();
    }
}
