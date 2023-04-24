package com.supportportal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.supportportal.enumeration.Etat;
import com.supportportal.enumeration.Languages;
import com.supportportal.enumeration.Level;
import com.supportportal.enumeration.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


//@Data

@Entity
@Table(name = "courses")
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    private String courseId;

    private String title;


    private String description;
    private String duration;

    /**
     *
In the above entity class, we use the @Entity annotation to mark it as a JPA entity, and the @Table annotation to specify the name of the table in the database. The @Id annotation marks the primary key field, and the @GeneratedValue annotation specifies that the primary key should be generated automatically.

The @Lob annotation is used to store large objects, such as images, in the database. The @Enumerated annotation is used to store enums in the database.
     */
    /**
     * Note that we've added a imageFilename field to store the original filename of the uploaded image, and a imageData field to store the binary data of the image. We've also added a Transient imgCourse field, which we'll use to handle image uploads.
     */


    private String profileImageUrl;


    @Enumerated(EnumType.STRING)
    private Languages language;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Enumerated(EnumType.STRING)
    private Type type;
  //  @Enumerated(EnumType.STRING)
    //private Etat etat;

    private String category;

    public Course() {
    }

    public Course(Long id, String courseId, String title, String description, String duration, String profileImageUrl, Languages language, Level level, Type type, String category) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.profileImageUrl = profileImageUrl;
        this.language = language;
        this.level = level;
        this.type = type;
       // this.etat = etat;
        this.category = category;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }



    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Languages getLanguage() {
        return language;
    }

    public void setLanguage(Languages language) {
        this.language = language;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}