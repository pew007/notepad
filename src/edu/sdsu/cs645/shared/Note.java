package edu.sdsu.cs645.shared;

import com.google.gwt.i18n.shared.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable{

    private static Note instance = null;
    private String content;
    private Date lastModified;

    private Note() {}

    public static Note getInstance() {

        if (instance == null) {
            instance = new Note();
        }

        return instance;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void updateLastModified() {
        this.lastModified = new Date();
    }

    public String getLastModifiedString() {
        return lastModified.toString();
    }
}
