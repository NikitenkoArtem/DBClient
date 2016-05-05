package oraclient.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;


import oraclient.view.FileJDialog;

public class NewFile extends File {
    private List<File> files;
    private String currentfile;
    private boolean saved = false;

    public NewFile(String filePath) {
        super(filePath);
        if (files == null) {
            init();
        }
        this.addFile(new File(filePath));
    }
    
    private void init() {
        files = new ArrayList<>();
    }

    public List<File> getFile() {
        return files;
    }

    public void addFile(File file) {
        files.add(file);
    }
    
    public void close() {
        files.clear();
    }
}
