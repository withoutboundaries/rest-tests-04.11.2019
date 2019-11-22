import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

@FixMethodOrder (MethodSorters.NAME_ASCENDING)
public class PetTest {
   static long petId;


    @Test
    public void test1CreatePet() {

        String body = "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"cat\"\n" +
                "  },\n" +
                "  \"name\": \"Nopetty\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

/*
        //ClassWork
        RestAssured.given()
                .log().all()
                //.header("ContentType", "application/json")
                .contentType(ContentType.JSON)
                .body(body)
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .body("category.name", is(not("")))
                .body("id", is(not("")))
                .log().all()
        ;


        //version1
        long petId = RestAssured.given()
                .body(body)
                .when()
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .extract().path("id")
                ;

        */

        //version2
        ValidatableResponse response = RestAssured.given()
                .log().all()
                //.header("ContentType", "application/json")
                .contentType(ContentType.JSON)
                .body(body)
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .body("category.name", is(not("")))
                .body("id", is(not("")))
                .log().all();
                response.extract().body().path("")
                ;

        String petName = response.extract().path("name");
        System.out.println(petName);

        petId = response.extract().path("id");
        System.out.println(petId);

    }

    @Test
    public void test2GetPetById(){
        System.out.println(petId);

        RestAssured
                .given()
                .get("https://petstore.swagger.io/v2/pet/"+petId)
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .body("category.name", is(not("")))
                .log().all()
                ;
    }


    @Test
    public void test3DeletePetById(){
        System.out.println(petId);

        RestAssured
                .given()
                .delete("https://petstore.swagger.io/v2/pet/"+petId)
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .log().all()
        ;
    }



}


