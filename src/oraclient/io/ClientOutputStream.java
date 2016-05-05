package oraclient.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;


public class ClientOutputStream {
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

    public void save(OutputStream os, JTextArea area) {
        if (os != null && area != null) {
            try {
                os.write((area.getText()).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
//            sqlFile.saved = true;
        }
    }
    
    public void close() {
        out.clear();
    }
}
