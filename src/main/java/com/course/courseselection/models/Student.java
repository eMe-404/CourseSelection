package com.course.courseselection.models;

import com.course.courseselection.commands.CreateStudentCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String password;
    private String email;

    private Student(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public static Student createByCommand(CreateStudentCommand command) {
        return new Student(command.getName(), command.getPassword(), command.getEmail());
    }
}
