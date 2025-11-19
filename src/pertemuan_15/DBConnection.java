/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pertemuan_15;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Lenovo IP 330-14IKB
 */
public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/PBO_Pertemuan15";
    private static final String USER = "postgres";
    private static final String PASS = "ZayZiya03";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
