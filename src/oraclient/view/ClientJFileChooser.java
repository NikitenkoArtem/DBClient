package oraclient.view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class ClientJFileChooser extends JFileChooser {
    public ClientJFileChooser(String string, FileSystemView fileSystemView) {
        super(string, fileSystemView);
    }

    public ClientJFileChooser(File file, FileSystemView fileSystemView) {
        super(file, fileSystemView);
    }

    public ClientJFileChooser(FileSystemView fileSystemView) {
        super(fileSystemView);
    }

    public ClientJFileChooser(File file) {
        super(file);
    }

    public ClientJFileChooser(String string) {
        super(string);
    }

    public ClientJFileChooser() {
        super();
    }
    
    public JFileChooser chsrParams(JFileChooser chooser, String btnTxt, String dlgTitle) {
        chooser = new JFileChooser();
        chooser.setApproveButtonText(btnTxt);
        chooser.setDialogTitle(dlgTitle);
        chooser.removeChoosableFileFilter(chooser.getFileFilter());
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Все файлы (*.*)", "*"));
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("SQL-Script (*.sql)", "sql"));
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Текстовый файл (*.txt)", "txt"));
        return chooser;
    }
}
