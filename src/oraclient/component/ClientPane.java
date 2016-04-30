package oraclient.component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;


public class ClientPane {
    private List<JScrollPane> scrollPanes;

    public ClientPane() {
        if (scrollPanes == null) {
            init();
        }
    }

    private void init() {
        scrollPanes = new ArrayList<>();
    }

    public List<JScrollPane> getScrollPanes() {
        return scrollPanes;
    }

    public void addJScrollPane(JScrollPane scrollPane) {
        scrollPanes.add(scrollPane);
    }

    public void close() {
        scrollPanes.clear();
    }
}
