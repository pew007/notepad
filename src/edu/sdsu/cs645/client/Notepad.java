package edu.sdsu.cs645.client;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
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

    private FlowPanel headerPanel = new FlowPanel();
    private FlowPanel loginPanel = new FlowPanel();
    private Label errorLabel = new Label();
    private PasswordTextBox passwordField = new PasswordTextBox();

    private FlowPanel notepadPanel = new FlowPanel();
    private FlowPanel buttonPanel = new FlowPanel();

    public void onModuleLoad() {
        createHeader();
        createLoginPanel();
    }

    private void createHeader() {
        final HTML header = new HTML();
        header.setHTML("<h1>Notepad</h1>");
        headerPanel.add(header);

        RootPanel.get().add(headerPanel);
    }

    private void createLoginPanel() {
        final Button loginButton = new Button("Login");

        loginPanel.addStyleName("loginPanel");
        errorLabel.addStyleName("errorLabel");

        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(errorLabel);

        RootPanel.get().add(loginPanel);

        passwordField.setFocus(true);
        passwordField.selectAll();

        passwordField.addKeyUpHandler(new KeyUpHandler() {
            public void onKeyUp(KeyUpEvent keyUpEvent) {
                if (keyUpEvent.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    authenticateUser();
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
        AsyncCallback callback = new AsyncCallback() {
            public void onSuccess(Object response) {
                errorLabel.setText("");
                if (response.equals("Error")) {
                    errorLabel.setText("Invalid password. Please try again.");
                } else {
                    removeLoginPanel();
                    createNotepadPanel();
                }
            }

            public void onFailure(Throwable throwable) {}
        };

        notepadService.authenticate(passwordField.getText(), callback);
    }

    private void removeLoginPanel() {
        RootPanel.get().remove(loginPanel);
    }

    private void createNotepadPanel() {
        final Button saveButton = new Button("Save");
        final Button loadButton = new Button("Load");
        final RichTextArea editor = new RichTextArea();

        notepadPanel.addStyleName("notepadPanel");
        editor.addStyleName("editor");

        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        notepadPanel.add(editor);

        RootPanel.get().add(notepadPanel);
        RootPanel.get().add(buttonPanel);

        saveButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                String content = editor.getText();
                save(content);
            }
        });
    }

    private void save(String content) {
        AsyncCallback callback = new AsyncCallback() {
            @Override
            public void onFailure(Throwable throwable) {
                errorLabel.setText(throwable.getMessage());
            }

            @Override
            public void onSuccess(Object o) {
                String response = (String) o;
                errorLabel.setText(response);
            }
        };

        notepadService.save(content, callback);
    }
}
