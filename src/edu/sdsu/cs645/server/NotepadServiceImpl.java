package edu.sdsu.cs645.server;

import edu.sdsu.cs645.client.NotepadService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.sdsu.cs645.shared.Note;

import java.io.*;

@SuppressWarnings("serial")
public class NotepadServiceImpl extends RemoteServiceServlet implements NotepadService {
    @Override
    public String authenticate(String password) throws IllegalArgumentException {
        return password.trim().equals("sp2015") ? "OK" : "Error";
    }

    @Override
    public synchronized Note save(String content) throws IllegalArgumentException {
        String path = getServletContext().getRealPath("/");
//        String filename = path + "/data.txt";
        String filename = "/Users/pwang/CS645/Notepad/data.txt";

        Note note = Note.getInstance();
        note.setContent(content);
        note.updateLastModified();

//        try {
//            PrintWriter out = new PrintWriter(new FileWriter(filename));
//            content = content.replace("\r\n|\n", "<br />");
//            out.print(content);
//            out.close();
//        } catch (Exception e) {
//
//        }

        return note;
    }

    @Override
    public Note load() throws IllegalArgumentException {
        String path = getServletContext().getRealPath("/");
//        String filename = path + "/data.txt";
        String filename = "/Users/pwang/CS645/Notepad/data.txt";
        String response = "";
        String line;

        Note note = Note.getInstance();

//        try {
//            BufferedReader in = new BufferedReader(new FileReader(filename));
//            while ((line = in.readLine()) != null) {
//                response += line;
//            }
//            in.close();
//        } catch (Exception e) {
//            return "Failed to read file. " + e.getMessage();
//        }

        return note;
    }
}
