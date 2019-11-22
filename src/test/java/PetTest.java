import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PetTest {
    static long petId;

    public RequestSpecification given() {
     return   RestAssured
                .given()
             .baseUri("https://petstore.swagger.io/v2")
                .log().all()
                .contentType(ContentType.JSON)
                ;

    }

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


        ValidatableResponse response = given()
                .body(body)
                .post(PetEndpoint.CREATE_PET)
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
    public void test2GetPetById() {
        System.out.println(petId);

        given()
                .get(PetEndpoint.GET_PET, petId)
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .body("category.name", is(not("")))
                .log().all()
        ;
    }


    @Test
    public void test3DeletePetById() {
        System.out.println(petId);

       given()
                .delete(PetEndpoint.DELETE_PET, petId)
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .log().all()
        ;
    }


}


