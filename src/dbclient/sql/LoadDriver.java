package dbclient.sql;

import java.io.File;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Locale;

import dbclient.io.LoadClass;

public class LoadDriver {
    public LoadDriver(String driverFilePath, String driverPackage) {
        try {
            LoadClass load = new LoadClass();
            load.addClass(new File(load.jarFilePath(driverFilePath)));
            Class driver = Class.forName(driverPackage);
            DriverManager.registerDriver((Driver)driver.newInstance());
            Locale.setDefault(Locale.ENGLISH);
        } catch (ClassNotFoundException e) {
        } catch (IllegalAccessException | InstantiationException e) {
        } catch (SQLException e) {
        }
    }

    
}
