package com.student.util.curdoperation;

import com.student.util.model.Datum;
import com.student.util.model.Products;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ProductCurd1  {
    int idNumber;
    @BeforeClass
    public void inIt(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3030;
        RestAssured.basePath = "/products";
    }

    @Test
    public void test001(){
        Products products = given()
                .when()
                .get()
                .getBody().as(Products.class);
        System.out.println(products.getTotal());

    }

    @Test
    //Create new product data
    public void test002() {
        Datum datum = new Datum();
        datum.setName("Duracell - AAA Batteries (4-Pack)");
        datum.setType("omebatries");
        datum.setPrice(440F);
        datum.setShipping(10);
        datum.setUpc("abcd");
        datum.setDescription("ghjk");
        datum.setManufacturer("Titen");
        datum.setModel("MK");
        datum.setUrl("jfdlkjl jlgjdlkhg");
        datum.setImage("mn mn ");
        Datum datum1 = given()
                .log().all()
                .header("Content-Type", "application/json")
                .when()
                .body(datum)
                .post()
                .getBody()
                .as(Datum.class);


        System.out.println(datum1.getId());
        idNumber = datum1.getId();
    }

    //get by ID
    @Test
    public void test003() {

        Datum datum1 = given()
                //     .log().all()
                .pathParam("id", idNumber)
                .when()
                .get("/{id}")
                .getBody()
                .as(Datum.class);
        System.out.println(datum1.getName());
    }
    // update product
    @Test
    public void test004(){
        Datum datum = new Datum();
        datum.setPrice(456F);
        datum.setShipping(70);
        datum.setUpc("dflkdjksk");
        datum.setDescription("ghjk");
        datum.setManufacturer("BMW");
        datum.setModel("tesla");

        Response response=given()
                .log().all()
                .header("Content-Type", "application/json")
                .pathParam("id", idNumber)
                .when()
                .body(datum)
                .patch("/{id}");
        response.then().statusCode(200);

    }
    // delete by id
   @Test
    public void test005(){

        Response response=given()
                .log().all()
                .pathParam("id", idNumber)
                .when()
                .delete("/{id}");
        response.then().statusCode(200);

    }
    // get by id after delete
    @Test
    public void test006(){

        Response response=given()
                .log().all()
                .pathParam("id", idNumber)
                .when()
                .get("/{id}");
        response.then().statusCode(404);

    }
}
