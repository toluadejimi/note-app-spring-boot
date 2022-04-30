package com.example.demo.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@JsonPropertyOrder({
    "id",
    "content",
    "is_completed"
})
public class Note {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("content")
    private String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @JsonProperty("is_completed")
    private Boolean isCompleted;

    /**
    * No args constructor for use in serialization
    *
    */
    public Note() {
    }

    /**
    *
    * @param id
    * @param user
    * @param content
    * @param isCompleted
    */
    public Note(Long id, String content, User user, Boolean isCompleted) {
        super();
        this.id = id;
        this.content = content;
        this.user = user;
        this.isCompleted = isCompleted;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(String content) {
        this.content = content;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("is_completed")
    public Boolean getIsCompleted() {
        return isCompleted;
    }

    @JsonProperty("is_completed")
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}