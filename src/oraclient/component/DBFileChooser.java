package oraclient.component;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class DBFileChooser extends JFileChooser {
    private Map<String, String> defaultFilter;

    public DBFileChooser(String btnText, String dlgTitle, Map<String, String> extensions) {
        super();
        if (extensions == null) {
            defaultFilter = new HashMap<>();
            initFilter();
            chooserParams(btnText, dlgTitle, defaultFilter);
        } else {
            chooserParams(btnText, dlgTitle, extensions);
        }
    }
    
    private void initFilter() {
        defaultFilter.put("SQL-Script (*.sql)", "sql");
        defaultFilter.put("Текстовые файлы (*.txt)", "txt");
    }

    public JFileChooser chooserParams(String btnText, String dlgTitle, Map<String, String> extensions) {
        this.setApproveButtonText(btnText);
        this.setDialogTitle(dlgTitle);
        this.removeChoosableFileFilter(this.getFileFilter());        
        for (Map.Entry<String, String> entry : extensions.entrySet()) {
            this.addChoosableFileFilter(new FileNameExtensionFilter(entry.getKey(), entry.getValue()));
        }
        return this;
    }
}
