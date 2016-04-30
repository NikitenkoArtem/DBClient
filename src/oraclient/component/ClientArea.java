package oraclient.component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;


public class ClientArea {
    private List<JTextArea> textAreas;

    public ClientArea() {
        if (textAreas == null) {
            init();
        }
    }

    private void init() {
        textAreas = new ArrayList<>();
    }


    public List<JTextArea> getTextAreas() {
        return textAreas;
    }

    public void addJTextArea(JTextArea area) {
        textAreas.add(area);
    }

    public void close() {
        textAreas.clear();
    }
}
