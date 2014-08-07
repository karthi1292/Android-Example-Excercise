package com.example.tagstables.model;

public class Note {
 
    int id;
    String note;
    int color;
    String created_at;
 
    // constructors
    public Note() {
    }
 
    public Note(String note, int color) {
        this.note = note;
        this.color = color;
    }
 
    public Note(int id, String note, int color) {
        this.id = id;
        this.note = note;
        this.color = color;
    }
 
    // setters
    public void setId(int id) {
        this.id = id;
    }
 
    public void setNote(String note) {
        this.note = note;
    }
 
    public void setcolor(int color) {
        this.color = color;
    }
     
    public void setCreatedAt(String created_at){
        this.created_at = created_at;
    }
 
    // getters
    public long getId() {
        return this.id;
    }
 
    public String getNote() {
        return this.note;
    }
 
    public int getcolor() {
        return this.color;
    }
}
