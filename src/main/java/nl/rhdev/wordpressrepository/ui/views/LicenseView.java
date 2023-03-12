package nl.rhdev.wordpressrepository.ui.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;
import nl.rhdev.wordpressrepository.models.License;
import nl.rhdev.wordpressrepository.repositories.LicenseRepository;
import nl.rhdev.wordpressrepository.ui.layout.MainLayout;

@Route(value = "licenses", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class LicenseView extends VerticalLayout {
    public LicenseView(@Autowired LicenseRepository licenseRepository) {
        Grid<License> grid = new Grid<>();

        grid.addColumn(License::getValue).setHeader("Value");

        grid.setItems(licenseRepository.findAll());

        add(grid);
    }
}
