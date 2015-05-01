package edu.sdsu.cs645.server;

import edu.sdsu.cs645.client.NotepadService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

@SuppressWarnings("serial")
public class NotepadServiceImpl extends RemoteServiceServlet implements NotepadService {

    @Override
    public String authenticate(String password) throws IllegalArgumentException {
        return password.trim().equals("sp2015") ? "OK" : "Error";
    }

    @Override
    public String save(String content) throws IllegalArgumentException {
        String path = getServletContext().getRealPath("/");
//        String filename = path + "/data.txt";
        String filename = "/Users/pwang/CS645/Notepad/data.txt";

        try {
            PrintWriter out = new PrintWriter(new FileWriter(filename));
            content = content.replace("\r\n|\n", "<br />");
            out.print(content);
            out.close();
        } catch (Exception e) {
            return "Failed to save file";
        }

        return "OK, document saved.";
    }

    @Override
    public String load() throws IllegalArgumentException {
        String path = getServletContext().getRealPath("/");
        String filename = path + "/data/txt";
        String answer = "";
        String line;

        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            while ((line = in.readLine()) != null) {
                answer += line;
                in.close();
            }
        } catch (Exception e) {
            return "Failed to read file.";
        }

        return answer;
    }
}
