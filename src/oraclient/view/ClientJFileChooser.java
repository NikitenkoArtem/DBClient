package oraclient.view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class ClientJFileChooser extends JFileChooser {
    public ClientJFileChooser(String btnText, String dlgTitle) {
        super();
        chsrParams(btnText, dlgTitle);
    }
    
    public JFileChooser chsrParams(String btnText, String dlgTitle) {
        this.setApproveButtonText(btnText);
        this.setDialogTitle(dlgTitle);
        this.removeChoosableFileFilter(this.getFileFilter());
        this.addChoosableFileFilter(new FileNameExtensionFilter("(*.*)", "*"));
        this.addChoosableFileFilter(new FileNameExtensionFilter("SQL-Script (*.sql)", "sql"));
        this.addChoosableFileFilter(new FileNameExtensionFilter("(*.txt)", "txt"));
        return this;
    }
}
