package dbclient.io;

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

import dbclient.swing.ClientArea;

public class NewFile extends File {
    private Map<File, Boolean> files;

    public NewFile(String filePath) {
        super(filePath);
        if (files == null) {
            init();
        }
    }

    private void init() {
        files = new HashMap<>();
    }

    public Map<File, Boolean> getFiles() {
        return files;
    }

    public void putFile(File key, Boolean value) {
        files.put(key, value);
    }

    public boolean isSaved(File key) {
        return files.get(key).booleanValue();
    }

    public boolean isSavedAll() {
        if (files.containsValue(false)) {
            return false;
        } else {
            return true;
        }
    }

    public void save(File file, JComponent textArea) {
        if (textArea != null) {
            try (OutputStream out = new FileOutputStream(file)) {
                out.write(((JTextArea) textArea).getText().getBytes());
            } catch (IOException e) {
            }
        }
    }

    public void saveAs(File file, JComponent textArea) {
        if (textArea != null) {
            try (OutputStream out = new FileOutputStream(file)) {
                try {
                    out.write(((JTextArea) textArea).getText().getBytes());
                    putFile(file, true);
                } catch (FileNotFoundException e) {
                }
            } catch (IOException e) {
            }
        }
    }

    public void saveAll(ClientArea textArea) {
        if (textArea != null) {
            for (Map.Entry<File, Boolean> entry : files.entrySet()) {
                try (OutputStream out = new FileOutputStream(entry.getKey())) {
                    out.write(textArea.find(entry.getKey()).getText().getBytes());
//                    saved.replace(entry.getKey(), isSaved(entry.getValue()), true);
                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                }
            }
        }
    }

    public File find(File key) {
        for (Map.Entry<File, Boolean> entry : files.entrySet()) {
            if (key == entry.getKey()) {
                return entry.getKey();
            }
        }
        return null;
    }

    public File find(String absoluteFilePath) {
        for (Map.Entry<File, Boolean> entry : files.entrySet()) {
            String absolutePath = entry.getKey().getAbsolutePath();
            if (absoluteFilePath.equalsIgnoreCase(absolutePath)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public File find(Boolean value) {
        for (Map.Entry<File, Boolean> entry : files.entrySet()) {
//            if (value == entry.getValue()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void openFile(File file, JComponent component) {
        try {
            files.put(file, false);
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
}
