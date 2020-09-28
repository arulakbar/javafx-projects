package com.example.Listview.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String notes;

    public Person(String firstName, String lastName, String notes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.notes = notes;
    }

    @Override
    public String toString() {
        return getFirstName()+" "+getLastName();
    }
}
