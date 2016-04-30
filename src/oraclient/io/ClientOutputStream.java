package oraclient.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import oraclient.sql.file.NewFile;

public class ClientOutputStream extends OutputStream {
    private static List<OutputStream> out;

    public ClientOutputStream() {
        if (out == null) {
            init();
        }
    }
    
    private void init() {
        out = new ArrayList<>();
    }

    public List<OutputStream> getOut() {
        return out;
    }

    public void addOutputStream(OutputStream os) {
        out.add(os);
    }

    private void save(OutputStream os, JTextArea area) {
        if (os != null && area != null) {
            try {
                os.write((area.getText()).getBytes());
            } catch (IOException e) {
            }
//            sqlFile.saved = true;
        }
    }
    
    public void close() {
        out.clear();
    }

    @Override
    public void write(int b) throws IOException {
        // TODO Implement this method
    }
}
