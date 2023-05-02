package com.supportportal.domain;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name="subactivite")
public class SousActivite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    private String name;
    /**
     *
     To create the classes Activity and Subactivity with a foreign key relationship in a Spring Boot application, you would typically use JPA (Java Persistence API) for data persistence and mapping. Here's an example implementation using Spring Data JPA:

     First, create the Activity class:

     java
     Copy code
     import javax.persistence.*;

     @Entity
     @Table(name = "activities")
     public class Activity {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String name;

     // Getters and Setters
     // ...
     }
     The Activity class is annotated with @Entity to indicate that it is a persistent entity and will be mapped to a database table. The @Table annotation specifies the name of the database table to which the entity will be mapped. The id field is annotated with @Id to mark it as the primary key, and @GeneratedValue specifies that the primary key will be automatically generated.

     Next, create the Subactivity class:

     java
     Copy code
     import javax.persistence.*;

     @Entity
     @Table(name = "subactivities")
     public class Subactivity {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String name;

     @ManyToOne
     @JoinColumn(name = "activity_id")
     private Activity activity;

     // Getters and Setters
     // ...
     }
     In the Subactivity class, the activity_id field is represented as
     a foreign key relationship using the @ManyToOne annotation.
     The @JoinColumn annotation specifies the name
     of the foreign key column in the subactivities table.
     */
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    public SousActivite() {}

    public SousActivite(Long id, String name, Activity activity) {
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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    //this.activity = activity;
    }



