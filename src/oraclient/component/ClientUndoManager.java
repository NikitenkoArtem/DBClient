package oraclient.component;

import java.io.File;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;


import oraclient.sql.file.NewFile;

public class ClientUndoManager {
    private List<UndoManager> undoMgrs;

    public ClientUndoManager() {
        if (undoMgrs == null) {
            init();
        }
    }

    private void init() {
        undoMgrs = new ArrayList<>();
    }
    
    public List<UndoManager> getUndoMgrs() {
        return undoMgrs;
    }

    public void addUndoManager(UndoManager mgr) {
        undoMgrs.add(mgr);
    }
    
    public void close() {
        undoMgrs.clear();
    }
}
