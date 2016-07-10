package com.rds.jpa.karaf.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Person")
@Table(name = "person")
@Cacheable
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public Person() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
