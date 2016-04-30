package oraclient.sql.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import oraclient.io.ClientInputStream;
import oraclient.io.ClientOutputStream;

import oraclient.view.FileJDialog;

public class NewFile extends File {
    private List<File> file;
    private String currentfile;
    private boolean saved = false;

    public NewFile(String filePath) {
        super(filePath);
        if (file == null) {
            init();
        }
        this.addFile(new File(filePath));
        ClientOutputStream out;
        try {
            out = new ClientOutputStream();
            out.addOutputStream(new FileOutputStream(filePath));
        } catch (FileNotFoundException ex) {
        }
    }
    
    private void init() {
        file = new ArrayList<>();
    }

    public List<File> getFile() {
        return file;
    }

    public void addFile(File f) {
        file.add(f);
    }
    
    public void close() {
        file.clear();
    }
}
