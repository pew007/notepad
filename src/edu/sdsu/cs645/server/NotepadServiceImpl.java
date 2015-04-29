package edu.sdsu.cs645.server;

import edu.sdsu.cs645.client.NotepadService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class NotepadServiceImpl extends RemoteServiceServlet implements NotepadService {

    @Override
    public String save(String content) throws IllegalArgumentException {
        return "save method server side";
    }

    @Override
    public String load() throws IllegalArgumentException {
        return "load method server side";
    }
}
