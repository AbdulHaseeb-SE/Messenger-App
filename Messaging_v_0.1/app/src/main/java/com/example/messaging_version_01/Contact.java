package com.example.messaging_version_01;

import java.util.Comparator;

public class Contact implements  Comparable<Contact>{
    private  String name ;
    private  String phone;
    private  String photo;


    public Contact() {
    }


    public Contact(String name, String phone, String photo) {
        this.name = name;
        this.phone = phone;
        this.photo = photo;
    }
    @Override
    public int compareTo(Contact other) {
        return this.getName().compareTo(other.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }



}

