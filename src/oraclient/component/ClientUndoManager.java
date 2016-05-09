package oraclient.component;

import java.io.File;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;


import oraclient.io.NewFile;

public class ClientUndoManager {
    private Map<JTextArea, UndoManager> undoMgrs;

    public ClientUndoManager() {
        if (undoMgrs == null) {
            init();
        }
    }

    private void init() {
        undoMgrs = new HashMap<>();
    }

    public Map<JTextArea, UndoManager> getUndoMgrs() {
        return undoMgrs;
    }

    public void putUndoManager(JTextArea area, UndoManager mgr) {
        undoMgrs.put(area, mgr);
    }

    public UndoManager find(JTextArea key) {
        for (Map.Entry<JTextArea, UndoManager> entry : undoMgrs.entrySet()) {
            if (key.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }
    public void close() {
        undoMgrs.clear();
    }
}
