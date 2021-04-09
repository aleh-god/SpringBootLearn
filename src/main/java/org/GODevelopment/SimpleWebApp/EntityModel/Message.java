package org.GODevelopment.SimpleWebApp.EntityModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Message {
    // Указываем на ключ в таблице БД. И передаем базе самой решать каким будет этот ключ
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    // Hibernate automatically translates the entity into a table.
    private String test;
    private String tag;

    public Message() {
        // Пустой конструктор для работы фреймворка
    }

    public Message(String test, String tag) {
        this.test = test;
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
