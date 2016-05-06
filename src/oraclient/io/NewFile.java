package oraclient.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTextArea;

public class NewFile extends File {
    private Map<String, File> files;
//    private List<File> files;
    private String currentfile;
    private Map<String, Boolean> saved;

    public NewFile(String filePath) {
        super(filePath);
        if (files == null) {
            init();
        }
        addFile(filePath, new File(filePath));
//        addFile(new File(filePath));
        addSave(filePath, false);
    }

    private void init() {
//        files = new ArrayList<>();
        files = new HashMap<>();
        saved = new HashMap<>();
    }

//    public void addFile(File file) {
//        files.add(file);
//    }

    public void addFile(String filePath, File file) {
        files.put(filePath, file);
    }

    public void addSave(String key, Boolean value) {
        saved.put(key, value);
    }

    public boolean isSaved(String key) {
        for (Map.Entry<String, Boolean> entry : saved.entrySet()) {
            if (key == entry.getKey()) {
                return entry.getValue();
            }
        }
        return false;
    }

    public boolean isAllSaved() {
        for (Map.Entry<String, Boolean> entry : saved.entrySet()) {
            if (entry.getValue().equals(false)) {
                return false;
            }
        }
        return true;
    }

    public void save(String filePath, JComponent area) {
        if (area != null) {
            try {
        //        out = new BufferedOutputStream(new FileOutputStream(filePath));
                OutputStream out = new FileOutputStream(filePath);
                out.write(((JTextArea) area).getText().getBytes());
            } catch (IOException e) {
            }
        }
    }
    
    public void saveAll(String filePath, JComponent area) {
        if (area != null) {
            try {
        //        out = new BufferedOutputStream(new FileOutputStream(filePath));
                OutputStream out = new FileOutputStream(filePath);
                out.write(((JTextArea) area).getText().getBytes());
            } catch (IOException e) {
            }
        }
    }
    
    public void saveAs(String filePath, JComponent area) {
        if (area != null) {
            try {
        //        out = new BufferedOutputStream(new FileOutputStream(filePath));
                OutputStream out = new FileOutputStream(filePath);
                out.write(((JTextArea) area).getText().getBytes());
            } catch (IOException e) {
            }
        }
    }

    public void close() {
        files.clear();
    }
}
