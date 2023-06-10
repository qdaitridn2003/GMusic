package com.practice.gmusic.entities;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String mail;
    private String pass;
    private String name;

    public User() {
    }

    public User(int id, String mail, String pass, String name) {
        this.id = id;
        this.mail = mail;
        this.pass = pass;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
