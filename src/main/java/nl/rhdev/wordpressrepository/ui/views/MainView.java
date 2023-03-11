package nl.rhdev.wordpressrepository.ui.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;
import nl.rhdev.wordpressrepository.ui.layout.MainLayout;

@Route(value = "", layout = MainLayout.class)
@PermitAll
public class MainView extends VerticalLayout {
    public MainView() {
    }
}
