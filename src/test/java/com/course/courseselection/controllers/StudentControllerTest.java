package com.course.courseselection.controllers;

import com.course.courseselection.commands.CreateStudentCommand;
import com.course.courseselection.models.Student;
import com.course.courseselection.repositeries.StudentRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.CREATED;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(value = "test")
public class StudentControllerTest {
    private static final int BEGIN_INDEX_OF_STUDENT_ID = 10;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private StudentRepository repository;

    @Before
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void should_return_new_student_when_sign_up_given_student_with_name_password_and_email2() {
        CreateStudentCommand requestBody = new CreateStudentCommand("jone", "12t343434", "test@email.com");
        String uri = given()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/students")
                .then()
                .statusCode(CREATED.value())
                .extract().response().header("location");

        Integer id = Integer.parseInt(uri.substring(BEGIN_INDEX_OF_STUDENT_ID));
        Student retrievedStudent = repository
                .findById(id)
                .orElse(null);

        assertNotNull(retrievedStudent);
    }
}