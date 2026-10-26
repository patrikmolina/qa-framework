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

    @Test
    void createBookingReturnsCreatedBooking() {
        String requestBody = """
                    {
                        "firstname" : "John",
                        "lastname" : "Bonachon",
                        "totalprice" : 333,
                        "depositpaid" : true,
                        "bookingdates" : {
                            "checkin" : "2026-11-11",
                            "checkout" : "2026-11-21"
                        },
                        "additionalneeds" : "Breakfast with eggs"
                    }
                """;

        given()
                .baseUri("https://restful-booker.herokuapp.com")
                .contentType("application/json")
                .accept("application/json")
                .body(requestBody)
        .when()
                .post("/booking")
        .then()
                .statusCode(200)
                .body("bookingid", notNullValue())
                .body("booking.firstname", equalTo("John"));
    }

    @Test
    void createdBookingCanBeRetrievedById(){
        String requestBody = """
                    {
                        "firstname" : "Ricardo",
                        "lastname" : "Ruben",
                        "totalprice" : 333,
                        "depositpaid" : true,
                        "bookingdates" : {
                            "checkin" : "2026-11-11",
                            "checkout" : "2026-11-21"
                        },
                        "additionalneeds" : "Breakfast with eggs"
                    }
                """;
        int bookingId =
        given()
                .baseUri("https://restful-booker.herokuapp.com")
                .contentType("application/json")
                .accept("application/json")
                .body(requestBody)
        .when()
                .post("/booking")
        .then()
                .statusCode(200)
                .extract().path("bookingid");

        given()
                .baseUri("https://restful-booker.herokuapp.com")
                .accept("application/json")
                .pathParam("id", bookingId)
        .when()
                .get("/booking/{id}")
        .then()
                .statusCode(200)
                .body("firstname", equalTo("Ricardo"))
                .body("lastname", equalTo("Ruben"))
                .body("bookingdates.checkin", equalTo("2026-11-11"))
                .body("totalprice", equalTo(333));
    }
}
