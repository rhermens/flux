package nl.rhdev.wordpressrepository.controllers.rest;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.rhdev.wordpressrepository.models.Plugin;
import nl.rhdev.wordpressrepository.repositories.PluginRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/plugins")
public class PluginController {

    private final PluginRepository pluginRepository;

    @Autowired
    public PluginController(PluginRepository pluginRepository) {
        this.pluginRepository = pluginRepository;
    }

    @GetMapping({"", "/"})
    public Flux<Plugin> listPlugins() {
        return Flux.fromStream(pluginRepository.stream());
    }

    @GetMapping({"/{slug}", "/{slug}/"})
    public Mono<Plugin> get(@PathVariable String slug) {
        return Mono.just(pluginRepository.findBySlug(slug).orElseThrow()).doOnError(NoSuchElementException.class, e -> Mono.error(e));
    }

    @GetMapping(path = {"/{slug}.zip"}, produces = { "application/zip" })
    public Mono<FileSystemResource> getDownload(@PathVariable String slug) {
        return Mono.just(pluginRepository.findBySlug(slug).map(p -> new FileSystemResource(p.getPath()))
            .orElseThrow()).doOnError(NoSuchElementException.class, e -> Mono.error(e));
    }
}
