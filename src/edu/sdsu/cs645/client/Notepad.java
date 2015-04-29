package edu.sdsu.cs645.client;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import edu.sdsu.cs645.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class Notepad implements EntryPoint {

    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    private final NotepadServiceAsync notepadService = GWT.create(NotepadService.class);

    public void onModuleLoad() {
        createLoginPanel();
    }

    private void createLoginPanel() {
        final FlowPanel loginPanel = new FlowPanel();
        final Button loginButton = new Button("Login");
        final TextBox passwordField = new PasswordTextBox();

        loginPanel.addStyleName("loginPanel");

        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        RootPanel.get().add(loginPanel);

        passwordField.setFocus(true);
        passwordField.selectAll();

        passwordField.addKeyUpHandler(new KeyUpHandler() {
            public void onKeyUp(KeyUpEvent keyUpEvent) {
                if (keyUpEvent.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    authenticateUser(passwordField.getText());
                }
            }
        });

        loginButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                authenticateUser(passwordField.getText());
            }
        });
    }

    private void authenticateUser(String password) {
        Label errorLabel = new Label();
        errorLabel.setText("");

        if (!FieldVerifier.isValidPassword(password)) {
            errorLabel.setText("Invalid password. Please try again.");
        }
    }
}
