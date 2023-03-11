package nl.rhdev.wordpressrepository.ui.layout;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;

import nl.rhdev.wordpressrepository.services.SecurityService;
import nl.rhdev.wordpressrepository.ui.views.MainView;
import nl.rhdev.wordpressrepository.ui.views.PluginView;
import nl.rhdev.wordpressrepository.ui.views.ThemeView;

public class MainLayout extends AppLayout {
    private static class RouteTabs extends Tabs implements BeforeEnterObserver {
        private final Map<RouterLink, Tab> routerLinkTabMap = new HashMap<>();

        public void add(RouterLink routerLink) {
            routerLink.setHighlightCondition(HighlightConditions.sameLocation());
            routerLink.setHighlightAction((link, shouldHighlight) -> {
                if (shouldHighlight) setSelectedTab(routerLinkTabMap.get(routerLink));
            });
            routerLinkTabMap.put(routerLink, new Tab(routerLink));
            add(routerLinkTabMap.get(routerLink));
        }

        public void add(RouterLink... routerLinks) {
            for (RouterLink routerLink : routerLinks) {
                this.add(routerLink);
            }
        }

        @Override
        public void beforeEnter(BeforeEnterEvent event) {
            setSelectedTab(null);
        }
    }

    public MainLayout(SecurityService securityService) {
        UI.getCurrent().getElement().getThemeList().add(Lumo.DARK);
        H2 title = new H2("Flux Repository");

        RouteTabs tabs = new RouteTabs();
        tabs.add(
            new RouterLink("Home", MainView.class),
            new RouterLink("Plugins", PluginView.class),
            new RouterLink("Themes", ThemeView.class)
        );
        tabs.setHeightFull();

        Button logout = new Button("Logout");
        logout.addClickListener(e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setHeightFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);
        header.addClassNames(LumoUtility.Padding.Horizontal.MEDIUM);
        header.add(title, tabs, logout);

        addToNavbar(header);
    }

}
