package dbclient.swing;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

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
            if (key.equals(entry.getKey())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public JTextArea find(File value) {
//        if (textAreas.containsValue(value)) {
            for (Map.Entry<JTextArea, File> entry : textAreas.entrySet()) {
                if (value.equals(entry.getValue())) {
                    return entry.getKey();
                }
            }
//        }
        return null;
    }
}
