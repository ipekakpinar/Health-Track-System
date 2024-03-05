

package com.example.demo8;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;





/**
 * LoginController, kullanıcı girişi ile ilgili işlemleri kontrol eden bir sınıftır.
 */
public class LoginController implements ControllerInterface {

    @FXML
    public Button loginButton;
    @FXML
    private Button cancelButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordPasswordField;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private ActionEvent storedEvent;

    /**
     * Kullanıcının giriş bilgilerini doğrulayan ve giriş sonuçlarına göre işlem yapan metod.
     */
    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM users WHERE username = '" + usernameTextField.getText() +
                "' AND password = '" + passwordPasswordField.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    loginMessageLabel.setText("Welcome!");
                    switchToScene2(storedEvent);
                } else {
                    loginMessageLabel.setText("Invalid Login. Try Again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Bir olayı kullanarak Scene 1'e geçişi sağlayan metod.
     *
     * @param event ActionEvent nesnesi
     * @throws IOException Eğer bir giriş hatası olursa IOException fırlatır
     */
    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Bir olayı kullanarak Scene 2'ye geçişi sağlayan metod.
     *
     * @param event ActionEvent nesnesi
     * @throws IOException Eğer bir giriş hatası olursa IOException fırlatır
     */
    public void switchToScene2(ActionEvent event) throws IOException {
        System.out.println("Switching to Scene 2");
        try {
            root = FXMLLoader.load(getClass().getResource("gui.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * İptal butonunun tıklanma olayını işleyen metod.
     *
     * @param e ActionEvent nesnesi
     */
    @FXML
    private void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Giriş butonunun tıklanma olayını işleyen metod.
     *
     * @param e ActionEvent nesnesi
     */
    @FXML
    private void loginButtonOnAction(ActionEvent e) {
        storedEvent = e;

        if ((!usernameTextField.getText().isBlank()) && (!passwordPasswordField.getText().isBlank())) {
            validateLogin();
        } else {
            loginMessageLabel.setText("Please enter username and password");
        }
    }
}





