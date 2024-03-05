package com.example.demo8;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * LeftMenuInterface, uygulamanın sol menüsündeki butonların işlevlerini tanımlayan bir arayüzdür.
 */
public interface LeftMenuInterface {

    /**
     * Kullanıcıyı giriş sayfasına yönlendiren butonun tıklanma olayını işler.
     *
     * @param e ActionEvent nesnesi
     * @throws IOException Eğer bir giriş hatası olursa IOException fırlatır
     */
    @FXML
    void quitButtonOnAction(ActionEvent e) throws IOException;

    /**
     * Ana sayfa butonunun tıklanma olayını işler.
     *
     * @param event MouseEvent nesnesi
     */
    @FXML
    void homeButtonClick(MouseEvent event);

    /**
     * Hastalıklar butonunun tıklanma olayını işler.
     *
     * @param event MouseEvent nesnesi
     */
    @FXML
    void diseasesClick(MouseEvent event);

    /**
     * Şeker butonunun tıklanma olayını işler.
     *
     * @param event MouseEvent nesnesi
     */
    @FXML
    void glucoseClick(MouseEvent event);

    /**
     * Kan basıncı butonunun tıklanma olayını işler.
     *
     * @param event MouseEvent nesnesi
     */
    @FXML
    void bloodPressureClick(MouseEvent event);

    /**
     * Hastalık ekleme butonunun tıklanma olayını işler.
     *
     * @param event MouseEvent nesnesi
     */
    @FXML
    void addDiseasesClick(MouseEvent event);

    /**
     * Menü kontrol metodunu tanımlar. Bu metod, görünen pencereye göre menüyü düzenler.
     */
    void menuControl();
}







