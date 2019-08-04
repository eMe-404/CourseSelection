package com.course.courseselection.controllers;

import com.course.courseselection.commands.CreateStudentCommand;
import com.course.courseselection.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = "/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Integer> create(@Valid @RequestBody CreateStudentCommand createStudentCommand) {
        Integer id = studentService.create(createStudentCommand);
        URI uri = URI.create(String.format("/students/%d", id));
        return ResponseEntity.created(uri).build();
    }
}
