package org.GODevelopment.SimpleWebApp.EntityModel;

import javax.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)     // Указываем на ключ в таблице БД. И передаем базе самой решать каким будет этот ключ
    private Integer id;
    // Hibernate automatically translates the entity into a table.
    private String text;
    private String tag;

    // Указываем, как автор должен сохраняться в БД.
    @ManyToOne(fetch = FetchType.EAGER) // Одному пользователю соотв множество сообщений. У сообщения может быть только уникальный автор из другого списка.
    @JoinColumn(name = "user_id") // Добавляем столбец в эту таблицу и задаем свое имя, а не по умолчанию
    private User author;

    public Message() {
        // Пустой конструктор для работы фреймворка
    }

    public Message(String text, String tag, User user) {
        this.text = text;
        this.tag = tag;
        this.author = user;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getNameAuthor() {
        return author.getUsername();
    }
}
