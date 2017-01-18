package com.blogservice.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by z003rn5u on 17.01.2017.
 */
public class Message implements Serializable{

    private Long id;
    private Date date;

    public Message() {
        this(0L, null);
    }

    public Message(Long id, Date date) {
        this.id = id;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
