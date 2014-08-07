package com.example.tagstables.model;

public class Mention {
    int id;
    String mention_tag;
 
    // constructors
    public Mention() {
 
    }
 
    public Mention(String mention_tag) {
        this.mention_tag = mention_tag;
    }
 
    public Mention(int id, String mention_tag) {
        this.id = id;
        this.mention_tag = mention_tag;
    }
 
    // setter
    public void setId(int id) {
        this.id = id;
    }
 
    public void setMentionName(String mention_tag) {
        this.mention_tag = mention_tag;
    }
 
    // getter
    public int getId() {
        return this.id;
    }
 
    public String getMentionName() {
        return this.mention_tag;
    }
}
