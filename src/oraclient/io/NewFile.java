package oraclient.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import javax.swing.undo.UndoManager;

import oraclient.view.FileJDialog;

public class NewFile extends File {
    private Map<File, String> files;
    //    private List<File> files;
    private Map<File, Boolean> saved;

    public NewFile(String filePath) {
        super(filePath);
        if (files == null) {
            init();
        }
//        File file = new File(filePath);
//        addFile(file, filePath);
        //        addFile(new File(filePath));
        addFile(this, filePath);
        addSave(this, false);
    }

    private void init() {
        //        files = new ArrayList<>();
        files = new HashMap<>();
        saved = new HashMap<>();
    }
    
    public void addFile(File file, String filePath) {
        files.put(file, filePath);
    }

    public void addSave(File key, Boolean value) {
        saved.put(key, value);
    }

    public boolean isSaved(File key) {
//        for (Map.Entry<File, Boolean> entry : saved.entrySet()) {
//            if (key == entry.getKey()) {
//                return entry.getValue().booleanValue();
//            }
//        }
        return saved.get(key).booleanValue();
    }

    public boolean isSavedAll() {
//        for (Map.Entry<File, Boolean> entry : saved.entrySet()) {
//            if (entry.getValue().equals(false)) {
//                return false;
//            }            
//        }
        if (saved.containsValue(false)) {
            return false;
        } else {
            return true;
        }
    }

    public void save(File file, JComponent area) {
        if (area != null) {
            try {
                //        out = new BufferedOutputStream(new FileOutputStream(filePath));
                OutputStream out = new FileOutputStream(file);
                out.write(((JTextArea) area).getText().getBytes());
            } catch (IOException e) {
            }
        }
    }

    public void save(String filePath, JComponent area) {
        if (area != null) {
            
        }
    }

    public void saveAs(File file, JComponent area) {
        if (area != null) {
            try {
                OutputStream out = new FileOutputStream(file);
                out.write(((JTextArea) area).getText().getBytes());
                addFile(file, file.getAbsolutePath());
                addSave(file, true);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
        }
    }

    public void saveAll(JComponent tabbedPane, JComponent textArea) {
        if (tabbedPane != null && textArea != null) {
            try {
//                ((JTabbedPane) tabbedPane).getTabCount();
//                for (JTextArea area : (JTextArea)textArea) 
                for (Map.Entry<File, String> entry : files.entrySet()) {
                    OutputStream out = new FileOutputStream(entry.getKey());
                    out.write(((JTextArea) textArea).getText().getBytes());
//                    saved.replace(entry.getKey(), isSaved(entry.getValue()), true);
                }
            } catch (IOException e) {
            }
        }
    }

    public File find(File key) {
        System.out.println(key);
        for (Map.Entry<File, String> entry : files.entrySet()) {
            if (key == entry.getKey()) {
                return entry.getKey();
            }
        }
        return null;
    }

    public File find(String value) {
        System.out.println(value);
        for (Map.Entry<File, String> entry : files.entrySet()) {
            if (value == entry.getValue()) {
                return entry.getKey();
            }
        }
        return null;
    }

//    public String find(File key) {
//        System.out.println(key);
//        for (Map.Entry<File, String> entry : files.entrySet()) {
//            if (key == entry.getKey()) {
//                return entry.getValue();
//            }
//        }
//        return null;
//    }
//
//    public File find(String value) {
//        System.out.println(value);
//        for (Map.Entry<File, String> entry : files.entrySet()) {
//            if (value == entry.getValue()) {
//                return entry.getKey();
//            }
//        }
//        return null;
//    }

    public void openFile(String filePath) {
//        try {
//            files.put(filePath, new FileInputStream(filePath));
//            //input.addInputStream(new FileInputStream(location));
//        } catch (FileNotFoundException e) {
//        }
    }

    public void close() {
        files.clear();
    }
}
