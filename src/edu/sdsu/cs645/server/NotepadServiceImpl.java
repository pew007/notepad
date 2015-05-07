package edu.sdsu.cs645.server;

import edu.sdsu.cs645.client.NotepadService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.sdsu.cs645.shared.Note;

import java.io.*;

@SuppressWarnings("serial")
public class NotepadServiceImpl extends RemoteServiceServlet implements NotepadService {

//    private String filename = getServletContext().getRealPath("/") + "object.data";
//    private String filename = "/Users/pwang/CS645/Notepad/object.data";

    @Override
    public String authenticate(String password) throws IllegalArgumentException {
        return password.trim().equals("sp2015") ? "OK" : "Error";
    }

    @Override
    public synchronized Note save(String content) throws IllegalArgumentException {

        Note note = Note.getInstance();
        note.setContent(content);
        note.updateLastModified();

//        try {
//            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
//            out.writeObject(note);
//            out.close();
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Failed to save note. " + e.getMessage());
//        }

        return note;
    }

    @Override
    public Note load() throws IllegalArgumentException {

//        Note note;
//
//        try {
//            FileInputStream fin = new FileInputStream(filename);
//            ObjectInputStream ois = new ObjectInputStream(fin);
//            note = (Note) ois.readObject();
//            fin.close();
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Failed to load note. " + e.getMessage());
//        }

        return Note.getInstance();
    }
}
