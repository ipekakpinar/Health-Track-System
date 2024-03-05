package com.example.demo8;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * DatabaseConnection sınıfı, MySQL veritabanına bağlanmak için kullanılır.
 * Veritabanı bağlantısını oluşturur ve sağlar.
 */

public class DatabaseConnection {
    private static final String DATABASE_NAME = "diseasetrack";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "password";
    private static final String URL = "jdbc:mysql://localhost/" + DATABASE_NAME;

    private Connection databaseLink;



    /**
     * Veritabanına bağlantı oluşturur ve bağlantı nesnesini geri döndürür.
     *
     * @return Veritabanı bağlantı nesnesi
     */
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
}
