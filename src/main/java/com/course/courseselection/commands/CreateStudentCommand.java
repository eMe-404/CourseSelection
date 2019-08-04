package com.course.courseselection.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateStudentCommand {
    @NotBlank(message = "name can't be blank")
    private String name;

    @Size(min = 6, message = "password length cant less then 6")
    private String password;

    @Email(message = "email should be valid")
    private String email;

    @JsonCreator
    public CreateStudentCommand(@JsonProperty("name") String name,
                                @JsonProperty("password") String password,
                                @JsonProperty("email") String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

}
