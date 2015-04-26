package edu.sdsu.cs645.client;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Window;
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

    private Button loginButton = new Button("Login");
    private TextBox passwordField = new PasswordTextBox();
    private Label errorLabel = new Label();

    public void onModuleLoad() {

        loginButton.addStyleName("loginButton");

        RootPanel.get("passwordFieldContainer").add(passwordField);
        RootPanel.get("loginButtonContainer").add(loginButton);
        RootPanel.get("errorLabelContainer").add(errorLabel);

        passwordField.setFocus(true);
        passwordField.selectAll();

        passwordField.addKeyUpHandler(new KeyUpHandler() {
            public void onKeyUp(KeyUpEvent keyUpEvent) {
                if (keyUpEvent.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    authenticateUser();
                    Window.Location.replace("/notepad.html");
                }
            }
        });

        loginButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                authenticateUser();
            }
        });
    }

    private void authenticateUser() {
        errorLabel.setText("");
        String password = passwordField.getText();
        if (!FieldVerifier.isValidPassword(password)) {
            errorLabel.setText("Invalid password. Please try again.");
        }
    }
}
