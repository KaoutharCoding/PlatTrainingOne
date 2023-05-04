package com.supportportal.domain;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "Activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long id;

    private String name;
    /**
     * In the @OneToMany mapping, you can specify cascade = CascadeType.REMOVE. This means
     * that when an Activity is deleted,
     * the associated Subactivity records will also be deleted.
     *
     * Make sure to adjust the code according to your specific entity and attribute names.
     *
     * With this configuration, when you delete an Activity, the associated Subactivity records will be cascaded and automatically removed from the database.
     */

   // @OneToMany(mappedBy = "activity",cascade = CascadeType.REMOVE)
    //private List<SousActivite> subActivities;
    /**
     *   // The 'mappedBy = "activity"' attribute specifies that
     *   // the 'private SubActivity subActivity ;' field in SubActivity owns the
     *   // relationship (i.e. contains the foreign key for the query to
     *   // find all phones for an employee.
     */
   @OneToMany(mappedBy = "activity")
    private List<SousActivite> subActivities;



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
