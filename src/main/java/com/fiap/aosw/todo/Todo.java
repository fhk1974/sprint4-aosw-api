package com.fiap.aosw.todo;

import jakarta.persistence.*;

@Entity
public class Todo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private boolean done;
    private String owner;

    public Todo() {}
    public Todo(String title, String owner) { this.title = title; this.owner = owner; }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
}
