package com.example.demo8;

/**
 * UserDisease, kullanıcının sahip olduğu hastalıkları temsil eden sınıftır.
 */
public class UserDisease {
    private String diseaseName;

    /**
     * UserDisease sınıfının parametreli kurucusu.
     *
     * @param diseaseName Hastalık adı
     */
    public UserDisease(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    /**
     * Kullanıcının sahip olduğu hastalığın adını döndüren metod.
     *
     * @return Hastalık adı
     */
    public String getDiseaseName() {
        return diseaseName;
    }

    /**
     * Kullanıcının sahip olduğu hastalığın adını ayarlayan metod.
     *
     * @param diseaseName Hastalık adı
     */
    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }
}
