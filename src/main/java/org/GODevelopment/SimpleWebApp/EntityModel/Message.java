package org.GODevelopment.SimpleWebApp.EntityModel;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)     // Указываем на ключ в таблице БД. И передаем базе самой решать каким будет этот ключ
    private long id;

    // Hibernate automatically translates the entity into a table.
    @NotBlank(message = "Please, fill the message")
    @Length(max = 2048, message = "Message too long")
    private String text;
    @Length(max = 255, message = "tag too long")
    private String tag;
    private String filename;

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

    public long getId() {
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

    public String getAuthorName() {
        return author.getUsername();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
