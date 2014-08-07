package com.example.tagstables.model;

public class Hash {
    int id;
    String hash_name;
 
    // constructors
    public Hash() {
 
    }
 
    public Hash(String hash_name) {
        this.hash_name = hash_name;
    }
 
    public Hash(int id, String hash_name) {
        this.id = id;
        this.hash_name = hash_name;
    }
 
    // setter
    public void setId(int id) {
        this.id = id;
    }
 
    public void setHashName(String hash_name) {
        this.hash_name = hash_name;
    }
 
    // getter
    public int getId() {
        return this.id;
    }
 
    public String getHashName() {
        return this.hash_name;
    }
}
