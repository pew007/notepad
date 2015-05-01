package edu.sdsu.cs645.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("notepad")
public interface NotepadService extends RemoteService {
    String authenticate(String password) throws IllegalArgumentException;
    String save(String content) throws IllegalArgumentException;
    String load() throws IllegalArgumentException;
}
