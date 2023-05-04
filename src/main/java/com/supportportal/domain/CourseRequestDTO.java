package com.supportportal.domain;

public class CourseRequestDTO {
    private String name;
    private String niveau;
    private String desc;
    private String type;
    private String duree;
    private String etat;
    private String quiz;
    private int ordre;
    private String formationName;

    // Constructors, getters, and setters
    // ...

    public CourseRequestDTO(){}
    public CourseRequestDTO(String name, String niveau, String desc, String type, String duree, String etat, String quiz, int ordre, String formationName) {
        this.name = name;
        this.niveau = niveau;
        this.desc = desc;
        this.type = type;
        this.duree = duree;
        this.etat = etat;
        this.quiz = quiz;
        this.ordre = ordre;
        this.formationName = formationName;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getFormationName() {
        return formationName;
    }

    public void setFormationName(String formationName) {
        this.formationName = formationName;
    }
}

