package oraclient.sql.drivers;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoadDrivers {
    public LoadDrivers(String driver) {
        try {
            Class d = Class.forName(driver);
            DriverManager.registerDriver((Driver)d.newInstance());
//            Locale.setDefault(Locale.ENGLISH);
        } catch (ClassNotFoundException e) {
        } catch (IllegalAccessException | InstantiationException e) {
        } catch (SQLException e) {
        }
    }
}
