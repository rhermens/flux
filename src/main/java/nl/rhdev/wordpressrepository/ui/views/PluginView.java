package nl.rhdev.wordpressrepository.ui.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import jakarta.annotation.security.PermitAll;
import nl.rhdev.wordpressrepository.models.Plugin;
import nl.rhdev.wordpressrepository.repositories.PluginRepository;
import nl.rhdev.wordpressrepository.ui.layout.MainLayout;

@Route(value = "plugins", layout = MainLayout.class)
@PermitAll
public class PluginView extends VerticalLayout {
    public PluginView(@Autowired PluginRepository pluginRepository) {
        Grid<Plugin> grid = new Grid<>();
        grid.addColumn(Plugin::getSlug).setHeader("Slug");
        grid.addColumn(Plugin::getPath).setHeader("Storage Path");
        grid.addColumn(p -> p.getHeader().getVersion()).setHeader("Version");

        grid.setClassNameGenerator(p -> p.getBadPractise() ? LumoUtility.Background.ERROR_10 : null);

        grid.setItems(pluginRepository.stream().toList());

        add(grid);
    }
}
