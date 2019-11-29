
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;


public class PetTest {
    private PetEndpoint petEndpoint = new PetEndpoint();
    private static long petId;
    private enum Status {AVAILABLE, PENDING, SOLD};
    Status myvar=Status.AVAILABLE;


    private String body = "{\n" +
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


    @Before
    public void beforeMethod() {
        ValidatableResponse response = petEndpoint
                .createPet(body)
                .statusCode(anyOf(is(200), is(201)))
                .body("category.name", is(not("")))
                .body("id", is(not("")));
        petId = response.extract().path("id");
    }


    @Test
    public void createPet() {
        petEndpoint
                .createPet(body)
                .statusCode(anyOf(is(200), is(201)))
                .body("category.name", is(not("")))
                .body("id", is(not("")))
        ;
    }

    @Test
    public void getPetById() {
        petEndpoint
                .getPet(petId)
                .statusCode(anyOf(is(200), is(201)))
                .body("category.name", is(not("")))
        ;
    }


    @Test
    public void deletePetById() {

        petEndpoint
                .deletePet(petId)
                .statusCode(anyOf(is(200), is(201)))
        ;

        petEndpoint
                .getPet(petId)
                .statusCode(is(404))
        ;
    }

    @Test
    public void findPetByStatus() {
         petEndpoint
                .findPet(myvar)
                .statusCode(is(200));

    }

    @Test
    public void updatePet () {

        Body <String,String> newBody = new Body<>();
        newBody.put("name", "BLA");
        newBody.put("category.name", "fox");

        petEndpoint
                .updatePet(newBody)
                .statusCode(is(200))
        ;
    }



}


