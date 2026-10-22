package io.github.patrikmolina.api;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


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

    @Test
    void getBookingByIdReturnsBookingData(){

        int bookingId =
                given()
                        .baseUri("https://restful-booker.herokuapp.com")
                .when()
                        .get("/booking")
                .then()
                        .statusCode(200)
                        .extract().path("[0].bookingid");

        given()
                .baseUri("https://restful-booker.herokuapp.com")
                .pathParam("id", bookingId)
                .accept("application/json")
        .when()
                .get("/booking/{id}")
        .then()
                .statusCode(200)
                .body("firstname", notNullValue())
                .body("bookingdates.checkin", notNullValue());
    }

    @Test
    void filterBookingByUnknownNameReturnsEmptyList(){
        given()
                .baseUri("https://restful-booker.herokuapp.com")
                .accept("application/json")
                .queryParam("firstname", "ricardoruben")
        .when()
                .get("/booking")
        .then()
                .statusCode(200)
                .body("size()", equalTo(0));

    }
}
