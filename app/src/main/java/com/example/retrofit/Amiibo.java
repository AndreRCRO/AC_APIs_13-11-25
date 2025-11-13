package com.example.retrofit;

public class Amiibo {

    private String id;
    private String name;
    private String image;

    private String head;
    private String tail;


    public String getId() {
        if (id != null) return id;
        if (head != null && tail != null) return head + tail;
        return null;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() { return image; }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHead() { return head; }

    public void setHead(String head) {
        this.head = head;
    }

    public String getTail() { return tail; }

    public void setTail(String tail) {
        this.tail = tail;
    }
}
