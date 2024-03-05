package com.example.demo8;

/**
 * Disease sınıfı, AbstractDisease sınıfından türetilen ve hastalık nesnelerini temsil eden bir sınıftır.
 */

public class Disease extends AbstractDisease {
    private int id;
    private String name;


    /**
     * Disease sınıfının yapıcı metodu.
     * @param id   Hastalık ID'si
     * @param name Hastalık adı
     */
    public Disease(int id, String name) {
        super(id, name);
        this.id = id;
        this.name = name;
    }


    /**
     *Getter ve Setterlar:
     */
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public void setId(int id) {

        this.id = id;
    }

    @Override
    public void setName(String name) {

        this.name = name;
    }


    @Override
    public String toString() {

        return getName();
    }



}
