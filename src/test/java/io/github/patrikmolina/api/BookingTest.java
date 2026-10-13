package io.github.patrikmolina.api;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

public class BookingTest {

    @Test
    void listBookingsReturnsNonEmptyList() {
        given()
                .baseUri("https://restful-booker.herokuapp.com")
        .when()
                .get("/booking")
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }
}
