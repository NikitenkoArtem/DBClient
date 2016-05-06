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
    //    private List<JTextArea> textAreas;
    private Map<String, JTextArea> textAreas;

    public ClientArea() {
        if (textAreas == null) {
            init();
        }
    }

    private void init() {
        //        textAreas = new ArrayList<>();
        textAreas = new HashMap<>();
    }

    //    public List<JTextArea> getTextAreas() {
    //        return textAreas;
    //    }
    //
    //    public void addJTextArea(JTextArea area) {
    //        textAreas.add(area);
    //    }

    public Map<String, JTextArea> getTextAreas() {
        return textAreas;
    }

    public void addJTextArea(String filePath, JTextArea area) {
        textAreas.put(filePath, area);
    }

    public JTextArea find(String key) {
        System.out.println(key);
        for (Map.Entry<String, JTextArea> entry : textAreas.entrySet()) {
            if (key == entry.getKey()) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void close() {
        textAreas.clear();
    }
}
