package by.dchebotar.sprboot.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Appeal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    @Enumerated(EnumType.STRING)
    private Status status;

    private Timestamp addtimestamp;

    private Timestamp donetimestamp;

    private String response;

    public Appeal() {
    }

    public Appeal(String text, User user) {
        this.text = text;
        this.author = user;
        this.status = Status.ACCEPTED;
        this.addtimestamp = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getAddtimestamp() {
        return addtimestamp;
    }

    public void setAddtimestamp(Timestamp addtimestamp) {
        this.addtimestamp = addtimestamp;
    }

    public Timestamp getDonetimestamp() {
        return donetimestamp;
    }

    public void setDonetimestamp(Timestamp donetimestamp) {
        this.donetimestamp = donetimestamp;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
