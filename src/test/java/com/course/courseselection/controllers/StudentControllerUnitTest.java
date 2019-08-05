package com.course.courseselection.controllers;

import com.course.courseselection.commands.CreateStudentCommand;
import com.course.courseselection.exceptions.RestExceptionHandler;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RunWith(MockitoJUnitRunner.class)
public class StudentControllerUnitTest {

    @InjectMocks
    private StudentController studentController;

    @InjectMocks
    private RestExceptionHandler restExceptionHandler;

    @Before
    public void initRestAssuredMockStandalone() {
        RestAssuredMockMvc.standaloneSetup(studentController, restExceptionHandler);
    }

    @Test
    public void should_return_error_message_when_create_student_given_student_with_empty_name() {
        CreateStudentCommand createCommand = new CreateStudentCommand(null, "simplePassword123", "test@gmail.com");
            given()
                .contentType(JSON)
                .body(createCommand)
            .when()
                .post("/students")
            .then()
                .log()
                .ifValidationFails()
                .statusCode(BAD_REQUEST.value())
                .body("timestamp", notNullValue())
                .body("message", equalTo("required params are not valid"))
                .body("subApiErrors", hasSize(1))
                .body("subApiErrors[0].field", equalTo("name"))
                .body("subApiErrors[0].rejectedValue", equalTo(createCommand.getName()))
                .body("subApiErrors[0].message", equalTo("name can't be blank"));
    }

    @Test
    public void should_return_formatted_error_message_when_create_student_given_student_with_invalid_email() {
        CreateStudentCommand createCommand = new CreateStudentCommand("Rick", "simplePassword123", "test.com");
            given()
                .contentType(JSON)
                .body(createCommand)
            .when()
                .post("/students")
            .then()
                .log()
                .ifValidationFails()
                .statusCode(BAD_REQUEST.value())
                .body("timestamp", notNullValue())
                .body("message", equalTo("required params are not valid"))
                .body("subApiErrors", hasSize(1))
                .body("subApiErrors[0].field", equalTo("email"))
                .body("subApiErrors[0].rejectedValue", equalTo(createCommand.getEmail()))
                .body("subApiErrors[0].message", equalTo("email should be valid"));
    }

    @Test
    public void should_return_formatted_error_message_when_create_student_given_student_with_invalid_password() {
        CreateStudentCommand createCommand = new CreateStudentCommand("Rick", "123", "test@gmail.com");
            given()
                .contentType(JSON)
                .body(createCommand)
            .when()
                .post("/students")
            .then()
                .log()
                .ifValidationFails()
                .statusCode(BAD_REQUEST.value())
                .body("timestamp", notNullValue())
                .body("message", equalTo("required params are not valid"))
                .body("subApiErrors", hasSize(1))
                .body("subApiErrors[0].field", equalTo("password"))
                .body("subApiErrors[0].rejectedValue", equalTo(createCommand.getPassword()))
                .body("subApiErrors[0].message", equalTo("password length cant less then 6"));
    }

    @Test
    public void should_return_formatted_error_message_when_create_student_given_student_with_multi_invalid_params() {
        CreateStudentCommand createCommand = new CreateStudentCommand("Rick", "123", "test.com");
            given()
                .contentType(JSON)
                .body(createCommand)
            .when()
                .post("/students")
            .then()
                .log()
                .ifValidationFails()
                .statusCode(BAD_REQUEST.value())
                .body("timestamp", notNullValue())
                .body("message", equalTo("required params are not valid"))
                .body("subApiErrors", hasSize(2))
                .body("subApiErrors[0].field", equalTo("password"))
                .body("subApiErrors[0].rejectedValue", equalTo(createCommand.getPassword()))
                .body("subApiErrors[0].message", equalTo("password length cant less then 6"))
                .body("subApiErrors[1].field", equalTo("email"))
                .body("subApiErrors[1].rejectedValue", equalTo(createCommand.getEmail()))
                .body("subApiErrors[1].message", equalTo("email should be valid"));
    }

    @Test
    public void should_return_formatted_error_message_when_create_student_given_student_with_empty_params() {
        given()
                .contentType(JSON)
                .when()
                .post("/students")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(BAD_REQUEST.value())
                .body("timestamp", notNullValue())
                .body("message", equalTo("http request body not properly given"))
                .body("subApiErrors", nullValue());
    }
}