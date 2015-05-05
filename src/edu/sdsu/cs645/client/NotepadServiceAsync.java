package edu.sdsu.cs645.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.sdsu.cs645.shared.Note;

/**
 * The async counterpart of <code>NotepadService</code>.
 */
public interface NotepadServiceAsync {
    void load(AsyncCallback<Note> callback) throws IllegalArgumentException;
    void save(String content, AsyncCallback<Note> callback) throws IllegalArgumentException;
    void authenticate(String password, AsyncCallback<String> callback) throws IllegalArgumentException;
}
