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
    //    private Map<String, JTextArea> textAreas;
    private Map<JTextArea, File> textAreas;

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

    //    public Map<String, JTextArea> getTextAreas() {
    //        return textAreas;
    //    }
    //
    //    public void addJTextArea(String filePath, JTextArea area) {
    //        textAreas.put(filePath, area);
    //    }

    public Map<JTextArea, File> getTextAreas() {
        return textAreas;
    }

    public void addJTextArea(JTextArea area, File file) {
        textAreas.put(area, file);
    }

    public JTextArea find(JTextArea key) {
        System.out.println(key);
        for (Map.Entry<JTextArea, File> entry : textAreas.entrySet()) {
            if (key == entry.getKey()) {
                return entry.getKey();
            }
        }
        return null;
    }

    public JTextArea find(File value) {
        System.out.println(value);
//        if (textAreas.containsValue(value)) {
            for (Map.Entry<JTextArea, File> entry : textAreas.entrySet()) {
                if (value == entry.getValue()) {
                    return entry.getKey();
                }
            }
//        }
        return null;
    }

    public void close() {
        textAreas.clear();
    }
}
