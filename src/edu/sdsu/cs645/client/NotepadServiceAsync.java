package edu.sdsu.cs645.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>NotepadService</code>.
 */
public interface NotepadServiceAsync {
    void load(AsyncCallback<String> callback) throws IllegalArgumentException;
    void save(String content, AsyncCallback<String> callback) throws IllegalArgumentException;
    void authenticate(String password, AsyncCallback<String> callback) throws IllegalArgumentException;
}
