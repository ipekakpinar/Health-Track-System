
package com.example.demo8;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Health Track System uygulamasını temsil eden ana sınıf.
 */


public class
HealthTrackApplication extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        // Giriş arayüzünü oluşturmak için "login.fxml" dosyasını yükler
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

        // İlk sahnenin stili olarak UNDECORATED (pencere dekorasyonları olmayan) ayarlanır.
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // İlk sahnenin boyutları ile yüklenen FXML kökünü belirtilen boyutlarda ayarlar.
        primaryStage.setScene(new Scene(root, 520, 400));

        // İlk sahneyi gösterir.
        primaryStage.show();

    }


    /**
     * JavaFX uygulamasını başlatmak için kullanılan ana metot.
     */
    public static void main(String[] args) {
        launch();
    }


}

