package edu.sdsu.cs645.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>NotepadService</code>.
 */
public interface NotepadServiceAsync {
  void notepadServer(String input, AsyncCallback<String> callback)
      throws IllegalArgumentException;
}
