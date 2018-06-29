package com.jperezmota.wsellfiliates.vaadin.ui.login;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.jperezmota.wsellfiliates.config.ApplicationProperties;
import com.jperezmota.wsellfiliates.services.UserSession;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI(path = "/login")
@PreserveOnRefresh
@Theme("wsellfiliates")
public class LoginUI extends UI{
	
	private VerticalLayout rootLayout;
	private Component loginLayout;
	private Label welcome;
	private Label title;
	//

    private final Environment environment;

    private TextField txtUsername;
    private PasswordField txtPassword;
    private CheckBox rememberMe;

    private Button loginButton;
    
    @Autowired
    private UserSession userSession;

    @Autowired
    public LoginUI(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected void init(VaadinRequest request) {
    		createInterface();
    }
    
    private void createInterface() {
    		configureRootLayout();
    		createComponents();
    		addComponentsToUI();
    }
    
    private void configureRootLayout() {
        Responsive.makeResponsive(this);
    		super.setSizeFull();
    }
    
    private void createComponents() { 		
        rootLayout = new VerticalLayout();
        rootLayout.setSizeFull();
        rootLayout.setMargin(false);
        rootLayout.setSpacing(false);
        
        loginLayout = createLoginLayout();
    }
    
    private void addComponentsToUI() {
    		rootLayout.addComponent(loginLayout);
    		rootLayout.setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
    		super.setContent(rootLayout);
    }

    private Component createLoginLayout() {
        final VerticalLayout loginLayout = new VerticalLayout();
        loginLayout.setSizeUndefined();
        loginLayout.setMargin(true);
        loginLayout.addStyleName("login-panel");
        Responsive.makeResponsive(loginLayout);

        loginLayout.addComponent(buildLabels());
        loginLayout.addComponent(createFields());
        return loginLayout;
    }

    private Component buildLabels() {
        CssLayout labelsLayout = new CssLayout();
        labelsLayout.addStyleName("labels");

        welcome = new Label("Welcome");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);

        title = new Label(environment.getProperty(ApplicationProperties.APP_NAME));
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        
        labelsLayout.addComponent(welcome);
        labelsLayout.addComponent(title);
        return labelsLayout;
    }

    private Component createFields() {
        VerticalLayout fields = new VerticalLayout();
        fields.setMargin(false);

        txtUsername = new TextField("Username");
        txtUsername.setIcon(VaadinIcons.USER);
        txtUsername.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        txtUsername.setWidth("100%");

        txtPassword = new PasswordField("Password");
        txtPassword.setIcon(VaadinIcons.LOCK);
        txtPassword.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        txtPassword.setWidth("100%");

        loginButton = new Button("Login");
        loginButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        loginButton.setDisableOnClick(true);
        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        loginButton.setWidth("100%");
        loginButton.focus();

        fields.addComponents(txtUsername, txtPassword, loginButton);
        fields.setComponentAlignment(loginButton, Alignment.BOTTOM_RIGHT);
        loginButton.addClickListener(e -> login());
        return fields;
    }

    private void login() {
        try {
//            vaadinSecurity.login(username.getValue(), password.getValue(), rememberMe.getValue());
        } catch (RuntimeException ex) {
            txtUsername.focus();
            txtUsername.selectAll();
            txtPassword.setValue("");
        } catch (Exception ex) {
            Notification.show("An unexpected error occurred", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            loginButton.setEnabled(true);
        }
    }
    
}
