package oraclient.component;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTextArea;


public class ClientArea {
    private Map<JTextArea, File> textAreas;
    

    public ClientArea() {
        if (textAreas == null) {
            init();
        }
    }

    private void init() {
        textAreas = new HashMap<>();
    }
    
    public Map<JTextArea, File> getTextAreas() {
        return textAreas;
    }

    public void addJTextArea(JTextArea area, File file) {
        textAreas.put(area, file);
    }

    public JTextArea find(JTextArea key) {
        for (Map.Entry<JTextArea, File> entry : textAreas.entrySet()) {
            if (key == entry.getKey()) {
                return entry.getKey();
            }
        }
        return null;
    }

    public JTextArea find(File value) {
//        if (textAreas.containsValue(value)) {
            for (Map.Entry<JTextArea, File> entry : textAreas.entrySet()) {
                if (value == entry.getValue()) {
                    return entry.getKey();
                }
            }
//        }
        return null;
    }
}
