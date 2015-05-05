package edu.sdsu.cs645.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.sdsu.cs645.shared.Note;

@RemoteServiceRelativePath("notepad")
public interface NotepadService extends RemoteService {
    String authenticate(String password) throws IllegalArgumentException;
    Note save(String content) throws IllegalArgumentException;
    Note load() throws IllegalArgumentException;
}
