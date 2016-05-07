package oraclient.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import java.util.ArrayList;
import java.util.List;


import oraclient.view.FileJDialog;

public class ClientInputStream extends InputStream {
    private List<InputStream> input;

    public ClientInputStream() {
        if (input == null) {
            init();
        }
//        openSqlFile();
    }
    
    private void init() {
        input = new ArrayList<>();
    }

    public List<InputStream> getInput() {
        return input;
    }

    public void addInputStream(InputStream in) {
        input.add(in);
    }
    
    public void close() {
        input.clear();
    }    

    @Override
    public int read() throws IOException {
        // TODO Implement this method
        return 0;
    }
}
