package nl.rhdev.wordpressrepository.controllers;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.rhdev.wordpressrepository.models.Theme;
import nl.rhdev.wordpressrepository.repositories.ThemeRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/themes")
public class ThemeController {

    private ThemeRepository themeRepository;

    @Autowired
    public ThemeController(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    @GetMapping({"", "/"})
    public Flux<Theme> listPlugins() {
        return Flux.fromStream(themeRepository.stream());
    }

    @GetMapping({"/{slug}", "/{slug}/"})
    public Mono<Theme> get(@PathVariable String slug) {
        return Mono.just(themeRepository.findBySlug(slug).orElseThrow()).doOnError(NoSuchElementException.class, e -> Mono.error(e));
    }

    @GetMapping(path = {"/{slug}.zip"}, produces = { "application/zip" })
    public Mono<FileSystemResource> getDownload(@PathVariable String slug) {
        return Mono.just(themeRepository.findBySlug(slug).map(p -> new FileSystemResource(p.getPath()))
            .orElseThrow()).doOnError(NoSuchElementException.class, e -> Mono.error(e));
    }
}
