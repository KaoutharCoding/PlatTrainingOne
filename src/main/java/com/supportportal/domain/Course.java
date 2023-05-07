package com.supportportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // @Column(name = "course_name")
    private String name;

    private String niveau;
    private String description;
    private String type;
    private String duree;
    private String etat;
    private String quiz;
    private int ordre;

    @Transient
    private String formationName;
    @JsonIgnore
    @Lob
    @Column(name = "file_data")
    private byte[] fileData;

    @Column(name = "original_filename")
    private String originalFilename;
    @Column(name = "file_type")
    private String fileType;
    @Transient
    private MultipartFile file;
    @Column(name = "file_url")
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    // Constructors, getters, and setters


    public Course() {
    }

    public Course(Long id, String name, String niveau, String description, String type, String duree, String etat, String quiz, int ordre, String formationName, byte[] fileData, String originalFilename, String fileType, MultipartFile file, String fileUrl, Formation formation) {
        this.id = id;
        this.name = name;
        this.niveau = niveau;
        this.description = description;
        this.type = type;
        this.duree = duree;
        this.etat = etat;
        this.quiz = quiz;
        this.ordre = ordre;
        this.formationName = formationName;
        this.fileData = fileData;
        this.originalFilename = originalFilename;
        this.fileType = fileType;
        this.file = file;
        this.fileUrl = fileUrl;
        this.formation = formation;
    }

    public Course(String name, String niveau, String description, String type, String duree, String etat, String quiz, int ordre, MultipartFile file, Formation formation) {
        this.name = name;
        this.niveau = niveau;
        this.description = description;
        this.type = type;
        this.duree = duree;
        this.etat = etat;
        this.quiz = quiz;
        this.ordre = ordre;
        this.file = file;
        this.formation = formation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return name;
    }

    public void setCourseName(String name) {
        this.name = name;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.formation.setName(name);
    }

    public String getFormationName() {
        return this.formation.getName();
    }

    public void setFormationName(String formationName) {
        this.formationName = formationName;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
