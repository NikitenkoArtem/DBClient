package oraclient.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTextArea;

import oraclient.component.ClientArea;

public class NewFile extends File {
    private Map<File, String> files;
    private Map<File, Boolean> saved;

    public NewFile(String filePath) {
        super(filePath);
        if (files == null) {
            init();
        }
//        putFile(this, filePath);
//        putSave(this, false);
    }

    private void init() {
        files = new HashMap<>();
        saved = new HashMap<>();
    }

    public void putFile(File file, String filePath) {
        files.put(file, filePath);
    }

    public void putSave(File key, Boolean value) {
        saved.put(key, value);
    }

    public boolean isSaved(File key) {
        return saved.get(key).booleanValue();
    }

    public boolean isSavedAll() {
        if (saved.containsValue(false)) {
            return false;
        } else {
            return true;
        }
    }

    public void save(File file, JComponent area) {
        if (area != null) {
            try (OutputStream out = new FileOutputStream(file)) {
                out.write(((JTextArea) area).getText().getBytes());
            } catch (IOException e) {
            }
        }
    }

    public void saveAs(File file, JComponent area) {
        if (area != null) {
            try (OutputStream out = new FileOutputStream(file)) {
                try {
                    out.write(((JTextArea) area).getText().getBytes());
                    putFile(file, file.getAbsolutePath());
                    putSave(file, true);
                } catch (FileNotFoundException e) {
                }
            } catch (IOException e) {
            }
        }
    }

    public void saveAll(ClientArea area) {
        if (area != null) {
            for (Map.Entry<File, String> entry : files.entrySet()) {
                try (OutputStream out = new FileOutputStream(entry.getKey())) {
                    out.write(area.find(entry.getKey()).getText().getBytes());
//                    saved.replace(entry.getKey(), isSaved(entry.getValue()), true);
                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                }
            }
        }
    }

    public File find(File key) {
        for (Map.Entry<File, String> entry : files.entrySet()) {
            if (key == entry.getKey()) {
                return entry.getKey();
            }
        }
        return null;
    }

    public File find(String value) {
        for (Map.Entry<File, String> entry : files.entrySet()) {
            if (value == entry.getValue()) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void openFile(File file, JComponent component) {
        try {
            files.put(file, file.getAbsolutePath());
            saved.put(file, false);
            try (InputStream in = new FileInputStream(file)) {
                int i = 0;
                do {
                    i = in.read();
                    if (i != -1) {
                        Character text = (char) i;
                        ((JTextArea) component).append(text.toString());
                    }
                } while (i != -1);
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public void clear() {
        files.clear();
    }
}
