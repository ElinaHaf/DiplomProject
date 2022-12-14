package ru.netology.data;

import jdk.jfr.ContentType;

public class RestApiHelper {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void createCard(DataHelper.CardInfo cardInfo) {
        given()
                .spec(requestSpec)
                .body(cardInfo)
                .when()
                .post("/api/v1/pay", "/api/v1/credit")
                .then()
                .statusCode(200);
    }

}
