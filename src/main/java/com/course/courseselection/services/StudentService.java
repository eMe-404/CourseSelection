package com.course.courseselection.services;

import com.course.courseselection.commands.CreateStudentCommand;
import com.course.courseselection.models.Student;
import com.course.courseselection.repositeries.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Integer create(CreateStudentCommand createStudentCommand) {
        Student student = Student.createByCommand(createStudentCommand);
        return studentRepository.save(student).getId();
    }
}
