package oraclient.sql.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;

public class SqlFile extends File {
    private List<File> file;
    private List<OutputStream> os;
    private List<JTextArea> sqlFile;
    private List<JScrollPane> sqlFileIndex;
    private List<UndoManager> undoMgr;
    private String currentfile;
    private boolean saved = false;
    
    public SqlFile(String pathname) {
        // TODO Implement this method
        super(pathname);
    }

    public void addFile(List<File> file, String filePath) {
        file.add(new File(filePath));
    }

    private void save(OutputStream os, JTextArea sqlFile) {
        if(os != null && sqlFile != null) {
            try {
                os.write((sqlFile.getText()).getBytes());
            } catch (IOException e) {
                return;
            }
            saved = true;
        }
    }
    
    private void initFile(String filePath) {
        file = new ArrayList<File>();
//        sqlFile = new ArrayList<File>(filePath);
        os = new ArrayList<OutputStream>();
        sqlFile = new ArrayList<JTextArea>();
        sqlFileIndex = new ArrayList<JScrollPane>();
        undoMgr = new ArrayList<UndoManager>();
    }
}
