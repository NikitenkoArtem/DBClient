package oraclient.sql.drivers;

import java.sql.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoadDrivers {
    private static List<Driver> drivers = new ArrayList<>();
    
    public LoadDrivers(String driver) {
        try {
            Driver d = (Driver) Class.forName(driver).newInstance();
            drivers.add(d);
//            Locale.setDefault(Locale.ENGLISH);
        } catch (ClassNotFoundException e) {
        } catch (IllegalAccessException | InstantiationException e) {
        }
    }

    public static List<Driver> getDrivers() {
        return drivers;
    }
}
