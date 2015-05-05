package edu.sdsu.cs645.client;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import edu.sdsu.cs645.shared.Note;

public class Notepad implements EntryPoint {

    private final NotepadServiceAsync notepadService = GWT.create(NotepadService.class);

    private FlowPanel headerPanel = new FlowPanel();
    private FlowPanel loginPanel = new FlowPanel();
    private Label errorLabel = new Label();
    private PasswordTextBox passwordField = new PasswordTextBox();

    private FlowPanel notepadPanel = new FlowPanel();
    private FlowPanel buttonPanel = new FlowPanel();

    private RichTextArea editor = new RichTextArea();
    private RichTextToolbar toolbar = new RichTextToolbar(editor);

    private PopupPanel popupPanel = new PopupPanel(true, false);

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
        loginButton.addStyleName("loginButton");

        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(errorLabel);

        RootPanel.get().add(loginPanel);

        passwordField.getElement().setPropertyString("placeholder", "Password");
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
        AsyncCallback<String> callback = new AsyncCallback<String>() {
            public void onSuccess(String response) {
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

    private void checkForUpdate() {
        load();
    }

    private void createNotepadPanel() {
        final Button saveButton = new Button("Save");
        final Button loadButton = new Button("Load");

        notepadPanel.addStyleName("notepadPanel");
        editor.addStyleName("editor");
        buttonPanel.addStyleName("buttonPanel");

        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        notepadPanel.add(toolbar);
        notepadPanel.add(editor);

        notepadPanel.addAttachHandler(new AttachEvent.Handler() {
            public void onAttachOrDetach(AttachEvent attachEvent) {
                if (attachEvent.isAttached()) {
                    checkForUpdate();
                }
            }
        });

        RootPanel.get().add(notepadPanel);
        RootPanel.get().add(buttonPanel);

        saveButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                String content = editor.getHTML();
                save(content);
            }
        });

        loadButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                load();
            }
        });
    }

    private void save(String content) {
        AsyncCallback<Note> callback = new AsyncCallback<Note>() {
            @Override
            public void onFailure(Throwable throwable) {
                showPopupAlert(throwable.getMessage());
            }

            @Override
            public void onSuccess(Note response) {
                showPopupAlert("Saved!");
            }
        };

        notepadService.save(content, callback);
    }

    private void load() {
        AsyncCallback<Note> callback = new AsyncCallback<Note>() {
            @Override
            public void onFailure(Throwable throwable) {
                showPopupAlert(throwable.getMessage());
            }

            @Override
            public void onSuccess(Note response) {
                editor.setHTML(response.getContent());
                showPopupAlert("Loaded!");
            }
        };

        notepadService.load(callback);
    }

    private void showPopupAlert(String message) {
        popupPanel.setWidget(new Label(message));
        popupPanel.center();
        popupPanel.show();
        Timer timer = new Timer()
        {
            @Override
            public void run()
            {
                popupPanel.hide();
            }
        };

        timer.schedule(1000);
    }
}
