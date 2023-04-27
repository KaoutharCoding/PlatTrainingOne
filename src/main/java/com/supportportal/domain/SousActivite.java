package com.supportportal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="sous_activite")
public class SousActivite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    private String name;
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private List<Activite> activity;


    public SousActivite(Long id, String name, List<Activite> activity) {
        this.id = id;
        this.name = name;
        this.activity = activity;
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

    public List<Activite> getActivity() {
        return activity;
    }

    public void setActivity(List<Activite> activity) {
        this.activity = activity;
    }
}
