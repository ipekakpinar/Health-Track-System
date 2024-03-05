package com.example.demo8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserDAO, kullanıcı ile ilgili veritabanı işlemlerini gerçekleştiren sınıftır.
 */
public class UserDAO {

    /**
     * Veritabanında belirli bir kullanıcının kimliğini getiren metod.
     *
     * @param username Kullanıcının kullanıcı adı
     * @return Kullanıcının kimliği (userid)
     */
    public int getLoggedInUserId(String username) {
        try (Connection connection = new DatabaseConnection().getConnection()) {
            // Veritabanında kullanıcının kimliğini sorgulayan SQL sorgusu
            String query = "SELECT userid FROM diseasetrack.users WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Kullanıcının kimliğini döndür
                        return resultSet.getInt("userid");
                    }
                }
            }
        } catch (SQLException ex) {
            // SQL isteği sırasında oluşan hataları yakala ve yazdır
            ex.printStackTrace();
        }
        // Kullanıcı kimliği bulunamazsa veya hata oluşursa 0 döndür
        return 0;
    }
}
