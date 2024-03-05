package com.example.demo8;

import javafx.event.ActionEvent;

import java.io.IOException;


/**
 * ControllerInterface, uygulama içindeki farklı sahneler arasında geçiş yapma
 * yeteneğine sahip denetleyici sınıfları için bir arayüzdür.
 *
 * LoginController sınıfında kullanılacaktır.
 */

public interface ControllerInterface {

    /**
     *
     * switchToScene1 ve switchToScene2 metodları, belirtilen olaya (ActionEvent) göre sahne değişimini gerçekleştirir.
     *
     * @param event Olaya (ActionEvent) tepki verme
     * @throws IOException Sahne değişimi sırasında bir giriş/çıkış hatası oluşursa
     */
    void switchToScene1(ActionEvent event) throws IOException;
    void switchToScene2(ActionEvent event) throws IOException;

}
