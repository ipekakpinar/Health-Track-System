package com.example.demo8;

/**
 * Disease class'ında kullanılmak üzere oluşturulmuş abstract classtır.
 */
public abstract class AbstractDisease {
    private int id;
    private String name;


    /**Constructor:*/
    public AbstractDisease(int id, String name) {
        this.id = id;
        this.name = name;
    }



    /**id(int) ve name(String) parametrelerinin getter ve setterları:*/
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Nesneyi okunabilir duruma dönüştüren metod.
     */
    @Override
    public String toString() {
        return getName();
    }
}
