package nl.rhdev.wordpressrepository.ui.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class AuthLayout extends AppLayout {
    public AuthLayout() {
        addClassNames(
            LumoUtility.Background.CONTRAST_90,
            LumoUtility.Display.FLEX,
            LumoUtility.JustifyContent.CENTER,
            LumoUtility.AlignItems.CENTER
        );
    }
}
