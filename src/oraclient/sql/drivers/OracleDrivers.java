package oraclient.sql.drivers;

import java.util.Locale;

import oraclient.view.ConnectionDialog;

public class OracleDrivers {
    public OracleDrivers() {
    }

    public static void loadDrivers() {
        try {
            Class.forName("oracle.jdbc.OracleDriver").newInstance();
            Locale.setDefault(Locale.ENGLISH);
        } catch (ClassNotFoundException e) {
        } catch (IllegalAccessException | InstantiationException e) {
        }
    }
}
