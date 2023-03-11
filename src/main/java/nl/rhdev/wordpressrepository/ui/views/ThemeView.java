package nl.rhdev.wordpressrepository.ui.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;
import nl.rhdev.wordpressrepository.models.Theme;
import nl.rhdev.wordpressrepository.repositories.ThemeRepository;
import nl.rhdev.wordpressrepository.ui.layout.MainLayout;

@Route(value = "themes", layout = MainLayout.class)
@PermitAll
public class ThemeView extends VerticalLayout {
    public ThemeView(@Autowired ThemeRepository themeRepository) {

        Grid<Theme> grid = new Grid<>();
        grid.addColumn(Theme::getSlug).setHeader("Slug");
        grid.addColumn(Theme::getPath).setHeader("Storage Path");
        grid.addColumn(p -> p.getHeader().getVersion()).setHeader("Version");

        grid.setItems(themeRepository.stream().toList());

        add(grid);
    }
}
