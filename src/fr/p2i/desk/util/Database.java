package fr.p2i.desk.util;

import fr.p2i.desk.data.BackData;
import fr.p2i.desk.data.BottomData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Database {
    private final String user;
    public String database;
    private final String password;
    private final String port;
    private final String hostname;

    private Connection connection;

    public Database() {
        this.hostname = "93.115.97.68";
        this.port = "3306";
        this.database = "db";
        this.user = "capteur";
        this.password = "password";
        this.connection = null;
    }

    public Connection openConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://"
                + this.hostname + ":" + this.port + "/" + this.database,
                this.user, this.password);
        return connection;
    }

    public boolean checkConnection() {
        return connection != null;
    }

    public Connection getConnection() throws Exception {
        try {
            openConnection();
            return connection;
        } catch (Exception e) {
            throw e;
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing MySQL");
                e.printStackTrace();
            }
        }
    }

    // Sognus code:
    public void insert(SensorData sd) throws Exception {

        Connection c = getConnection();
        Statement s = null;

        s = c.createStatement();

        StringBuilder queries = new StringBuilder("INSERT INTO " + sd.type + " VALUES (");
        String[] st = sd.toString().split(";");
        for (String a : st) {
            queries.append(a).append(",");
        }
        queries.append(");");
        s.executeQuery(queries.toString());
    }

    public List<SensorData> get(String st) throws Exception {
        Connection c = getConnection();
        Statement s = null;
        s = c.createStatement();
        String queries = new String("SELECT * FROM " + st);
        ResultSet rs = s.executeQuery(queries);
        List<SensorData> ls = new ArrayList<>();
        while (rs.next()) {
            long ts = rs.getLong(0);
            int[] a = new int[rs.getFetchSize()];
            for (int i = 0; i < rs.getFetchSize(); i++) {
                a[i] = rs.getInt(i + 1);
            }
            switch (st) {
                case "back":
                    BackData bd = new BackData(ts, a);
                    ls.add(bd);
                case "bottom":
                    BottomData b = new BottomData(ts, a);
                    ls.add(b);
                case "lights":
                    BottomData z = new BottomData(ts, a);
                    ls.add(z);
            }
        }
        return ls;
    }

}
