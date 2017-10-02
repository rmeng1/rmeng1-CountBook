package com.example.rmeng1_countbook;

import java.util.ArrayList;

import java.util.Date;


public class Record {
    private String name;
    private Date date;
    private int initial;
    private int current;

    private String comment;


    public Record() {

    }


    public String getName() {
        if (name == null) return null;
        if (name.isEmpty()) return null;
        return name;
    }


    public Date getDate() {
        return date;
    }


    public double getInitial() {
        return initial;
    }


    public double getCurrent() {
        return current;
    }



    public String getComment() {
        if (comment == null) return null;
        return comment;
    }


    public void setName(String name) {this.name = name;}

    public void setDate(Date date) {
        this.date = date;
    }

    public void setInitial(int initial) {
        this.initial = initial;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @Override
    public String toString(){
        return " " + this.name  + "           initial value " + this.initial +"\n"+ "comment: " + this.comment ;
    }

}