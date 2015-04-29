package edu.sdsu.cs645.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("notepad")
public interface NotepadService extends RemoteService {
    String save(String s) throws IllegalArgumentException;
    String load() throws IllegalArgumentException;
}
