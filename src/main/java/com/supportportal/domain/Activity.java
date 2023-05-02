package com.supportportal.domain;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false,name = "activity_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "activity")
    private List<SousActivite> subActivities;
    /**
     *   // The 'mappedBy = "activity"' attribute specifies that
     *   // the 'private SubActivity subActivity ;' field in SubActivity owns the
     *   // relationship (i.e. contains the foreign key for the query to
     *   // find all phones for an employee.
     */
  //  @OneToMany(mappedBy = "activity")
    //private List<SousActivite> subActivities;



    public Activity() {
    }

    public Activity(Long id, String name) {
        this.id = id;
        this.name = name;
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


}
