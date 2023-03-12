package nl.rhdev.wordpressrepository.ui.views;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;
import nl.rhdev.wordpressrepository.models.User;
import nl.rhdev.wordpressrepository.repositories.UserRepository;
import nl.rhdev.wordpressrepository.ui.layout.MainLayout;

@Route(value = "users", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class UserView extends VerticalLayout {

    public UserView(@Autowired UserRepository userRepository) {
        Grid<User> grid = new Grid<>();

        grid.addColumn(User::getUsername).setHeader("Username");
        grid.addColumn(u -> u.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(", "))).setHeader("Authorities");

        List<User> users = userRepository.findAll();
        grid.setItems(users);

        add(grid);
    }
}
