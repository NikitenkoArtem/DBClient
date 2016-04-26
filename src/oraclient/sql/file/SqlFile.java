package oraclient.sql.file;

import java.awt.event.ActionEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;

import oraclient.view.SqlFileJDialog;
import oraclient.view.FrontEndForm;

public class SqlFile extends File {
    private List<File> file;
    private List<OutputStream> out;
    private List<InputStream> input;
    private List<JTextArea> textAreas;
    private List<JScrollPane> scrollPanes;
    private List<UndoManager> undoMgrs;
    private String currentfile;
    private boolean saved = false;
    
    
    public SqlFile(String filePath) {
        super(filePath);
        if (file == null) {
            initFile();
        }
    }

    public List<File> getFile() {
        return file;
    }

    public List<OutputStream> getOut() {
        return out;
    }

    public List<InputStream> getInput() {
        return input;
    }

    public List<JTextArea> getTextAreas() {
        return textAreas;
    }

    public List<JScrollPane> getScrollPanes() {
        return scrollPanes;
    }

    public List<UndoManager> getUndoMgrs() {
        return undoMgrs;
    }

    public void addFile(File f) {
        file.add(f);
    }

    public void addOutputStream(OutputStream os) {
        out.add(os);
    }

    public void addInputStream(InputStream in) {
        input.add(in);
    }

    public void addJTextArea(JTextArea area) {
        textAreas.add(area);
    }

    public void addJScrollPane(JScrollPane scrollPane) {
        scrollPanes.add(scrollPane);
    }

    public void addUndoManager(UndoManager mgr) {
        undoMgrs.add(mgr);
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
        textAreas = new ArrayList<JTextArea>();
        out = new ArrayList<OutputStream>();
        textAreas = new ArrayList<JTextArea>();
        scrollPanes = new ArrayList<JScrollPane>();
        undoMgrs = new ArrayList<UndoManager>();
    }
    
    public static void newSqlFile(SqlFileJDialog dialog, JTabbedPane tab) {
        String filePath = dialog.getFileLocation().getText() + dialog.getFileName().getText();
        SqlFile file = new SqlFile(filePath);
        file.addFile(file);
        try {
            file.addOutputStream(new FileOutputStream(filePath));
        } catch (FileNotFoundException ex) {
        }
        file.addJScrollPane(new JScrollPane());
        file.addJTextArea(new JTextArea());
        file.addUndoManager(new UndoManager());
        tab.addTab(file.getName(), file.getScrollPanes().get(0).add(file.getTextAreas().get(0)));
//        if(sqlFile == null) {
//            initFile();
//        }
//            sqlFile.getDocument().addUndoableEditListener(undoMgr);
//            sqlFileIndex.setViewportView(sqlFile);
    }
    
    public void openSqlFile() {
        SqlFileJDialog dialog = new SqlFileJDialog();
        dialog.setVisible(true);
        String location = dialog.getFileLocation().getText();
        if (input == null) {
            input = new ArrayList<InputStream>();
        }
        try {
            input.add(new FileInputStream(location));
            //input.addInputStream(new FileInputStream(location));
        } catch (FileNotFoundException e) {
        }
    }
        
    public void close() {
        out.clear();
        file.clear();
        textAreas.clear();
        scrollPanes.clear();
        undoMgrs.clear();
    }
}
