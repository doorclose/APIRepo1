package com.student.util.curdoperation;

import com.student.util.model.StudentPojo;
import com.student.util.testbase.TestBase;
import com.student.util.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class StudentCurd extends TestBase {

    static String firstName = "yogini" + TestUtils.getRandomValue();
    static String lastname = "rao" + TestUtils.getRandomValue();

    static String email = TestUtils.getRandomValue()+ "@yahoo.com";
    //static String email = "yogini999@yahoo.com";
    static String programme = "Doctor";

    // static String courses = "Physio", "Phd";
    //static String studentId = "123";
    static int studentId;

    @Test
    public void getStudentInfo() {
        given()
                .when()
                .get("/list")
                .then().statusCode(200);
    }

    @Test
    public void getStudentInfoById() {
        //http://localhost:8080/student/2
        Response response = given().log().all()
                .pathParam("id", "2")
                .when()
                .get("/{id}");
        response.then().statusCode(200);

    }

    //Create student data

    @Test
    public void test001() {
        List<String> courseList = new ArrayList<>();
        courseList.add("Physio");
        courseList.add("Phd");

        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setFirstName(firstName);
        studentPojo.setLastName(lastname);
        studentPojo.setEmail(email);
        studentPojo.setProgramme(programme);
        studentPojo.setCourses(courseList);

        Response response = given().log().all()
                .when()
                .contentType(ContentType.JSON)
                .body(studentPojo)
                .post();
        response.then().statusCode(201);
    }
    //fetch created  student data by list
    @Test
    public void test002(){

        HashMap<String, Object> studentData =given()
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .extract().path("findAll{it.firstName =='"+firstName+"'}.get(0)");

        studentId= (int) studentData.get("id");
        System.out.println(studentId);
    }

    //fetch  data by ID to verify datas has been created
    @Test
    public void test003(){

        Response response =given().log().all()
                .pathParam("id",studentId)
                .when()
                .get("/{id}");
        response.then().log().all().statusCode(200).body("firstName", equalTo(firstName));

    }

    //updata student data
    @Test
    public void test004(){

        List<String> courseList = new ArrayList<>();
        courseList.add("Physio");
        courseList.add("Phd");

        StudentPojo studentpojo = new StudentPojo();
        studentpojo.setFirstName("yogini");
        studentpojo.setLastName("rao");
        studentpojo.setEmail("yoginibarot333@yahoo.com");
        studentpojo.setProgramme("Doctor");
        studentpojo.setCourses(courseList);

        Response response=given()
                .log().all()
                .header("content-Type","application/json")
               .pathParam("id",studentId)
                .when()
                .body(studentpojo)
                .patch("/{id}");
        response.then().statusCode(200);
    }
    //delete by id
    @Test
    public void test005(){

        Response response =given().log().all()
                .pathParam("id",studentId)
                .when()
                .delete("/{id}");
        response.then().log().all().statusCode(204);

    }

   // get by id after delete
    @Test
    public void test006(){

        Response response=given()
                .log().all()
                .pathParam("id", studentId)
                .when()
                .get("/{id}");
        response.then().statusCode(404);

    }
}





