package oraclient.sql.file;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;

import oraclient.view.SqlFileJDialog;
import oraclient.view.FrontEndForm;

public class SqlFile extends File {
    private static String fileLocation;
    private List<File> file;
    private List<OutputStream> os;
    private List<JTextArea> sqlFile;
    private List<JScrollPane> sqlFileIndex;
    private List<UndoManager> undoMgr;
    private String currentfile;
    private boolean saved = false;
    
    static {
        SqlFileJDialog dialog = new SqlFileJDialog();
        fileLocation = dialog.getFileLocation().getText();
    }
    
    public SqlFile() {
        super(fileLocation);
    }

    public void addFile(String filePath) {
        file.add(new File(filePath));
    }

    public void addOutputStream(List<OutputStream> os, String filePath) {
//        os.add(new OutputStream(filePath));
    }

    public void addJTextArea() {
        sqlFile.add(new JTextArea());
    }

    public void addJScrollPane() {
        sqlFileIndex.add(new JScrollPane());
    }

    public void addUndoManager() {
        undoMgr.add(new UndoManager());
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
    
    private void initFile() {
        file = new ArrayList<File>();
        sqlFile = new ArrayList<JTextArea>();
        os = new ArrayList<OutputStream>();
        sqlFile = new ArrayList<JTextArea>();
        sqlFileIndex = new ArrayList<JScrollPane>();
        undoMgr = new ArrayList<UndoManager>();
    }
    
    public void newSqlFile(FrontEndForm form) {
        SqlFileJDialog createSqlFle = new SqlFileJDialog();
        createSqlFle.setVisible(true);
        String filePath = createSqlFle.getFileLocation().getText() + createSqlFle.getFileName().getText();
        if(createSqlFle.isStatus()) {
            if(sqlFile == null) {
                initFile();
            }
//            sqlFile.getDocument().addUndoableEditListener(undoMgr);
//            sqlFileIndex.setViewportView(sqlFile);
            
        }
    }
    
    public void openSqlFile() {
        
    }
        
    public void close() {
//        sqlArea.remove(sqlFileIndex);
        sqlFile = null;
        sqlFileIndex = null;
        undoMgr = null;
//        sqlArea.remove(sqlFile);
//        saved = false;
    }
}
