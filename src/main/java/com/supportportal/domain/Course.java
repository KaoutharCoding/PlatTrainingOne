package com.supportportal.domain;

import javax.persistence.*;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name")
    private String courseName;

    private String niveau;
    private String description;
    private String type;
    private String duree;
    private String etat;
    private String quiz;
    private int ordre;

    @OneToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    // Constructors, getters, and setters


    public Course() {
    }

    public Course(Long id, String courseName, String niveau, String description, String type, String duree, String etat, String quiz, int ordre, Formation formation) {
        this.id = id;
        this.courseName = courseName;
        this.niveau = niveau;
        this.description = description;
        this.type = type;
        this.duree = duree;
        this.etat = etat;
        this.quiz = quiz;
        this.ordre = ordre;
        this.formation = formation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getQuiz() {
        return quiz;
    }

    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }
}
