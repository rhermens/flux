package nl.rhdev.wordpressrepository.ui.views;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import nl.rhdev.wordpressrepository.ui.layout.AuthLayout;

@Route(value = "login", layout = AuthLayout.class)
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm loginForm = new LoginForm();

    public LoginView() {
        loginForm.setAction("login");

        add(loginForm);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (
            event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")
        ) {
            loginForm.setError(true);
        }
    }
}
