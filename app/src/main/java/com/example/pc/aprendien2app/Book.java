package com.example.pc.aprendien2app;

/**
 * Created by Pc on 17/10/2017.
 */

public class Book {
    private String title;
    private int resource;

    public Book(String title, int resource) {
        this.title = title;
        this.resource = resource;

    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return resource;
    }

}
