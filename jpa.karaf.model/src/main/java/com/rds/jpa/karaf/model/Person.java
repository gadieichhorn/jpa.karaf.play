package com.rds.jpa.karaf.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author gadei
 */
@Entity
public class Person implements Serializable {
    
    /**
     * identity key
     */
    @Id
    public long id;


    
}
