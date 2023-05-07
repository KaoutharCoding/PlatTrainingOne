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

    @ManyToOne()
    @JoinColumn(name = "sous_activity_id")
    private SousActivite sousActivite;


    @Transient
   private String subActivityName;

    private String ActivityName;

    // Constructors, getters, and setters


    public Formation(Long id, String name, String niveau, String description, String type, String duree) {
        this.id = id;
        this.name = name;
        this.niveau = niveau;
        this.description = description;
        this.type = type;
        this.duree = duree;
       // this.courses = courses;
    }

    public Formation() {
    }

    public String getSubActivityName() {
        return subActivityName;
    }

    public void setSubActivityName(String subActivityName) {
        this.subActivityName = subActivityName;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
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

    public SousActivite getSousActivite() {
        return sousActivite;
    }

    public void setSousActivite(SousActivite sousActivite) {
        this.sousActivite = sousActivite;
    }
}
