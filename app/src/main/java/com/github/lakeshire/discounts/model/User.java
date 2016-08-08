package com.github.lakeshire.discounts.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private String email;
    private String password;
    private List<Info> collections = new ArrayList<>();

    public User() {

    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Info> getCollections() {
        return collections;
    }

    public void setCollections(List<Info> collections) {
        this.collections = collections;
    }
}
