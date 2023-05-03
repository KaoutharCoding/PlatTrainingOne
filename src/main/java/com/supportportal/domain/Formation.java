package com.supportportal.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String niveau;
    private String description;
    private String type;
    private String duree;

    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
    private List<Course> courses;

    // Constructors, getters, and setters


    public Formation(Long id, String name, String niveau, String description, String type, String duree, List<Course> courses) {
        this.id = id;
        this.name = name;
        this.niveau = niveau;
        this.description = description;
        this.type = type;
        this.duree = duree;
        this.courses = courses;
    }

    public Formation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }



}