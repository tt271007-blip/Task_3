package praktikum.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.model.User;

import static io.restassured.RestAssured.given;

public class UserApiClient {

    private static final String BASE_URL = "https://qa-stellarburgers.education-services.ru";
    private static final String REGISTER = "/api/auth/register";
    private static final String USER = "/api/auth/user";

    @Step("API: создание пользователя {user.email}")
    public Response register(User user) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(user)
                .when()
                .post(REGISTER);
    }

    @Step("API: удаление пользователя")
    public void deleteUser(String accessToken) {
        given()
                .baseUri(BASE_URL)
                .header("Authorization", accessToken)
                .when()
                .delete(USER);
    }

    @Step("API: вход пользователя {user.email}")
    public Response login(User user) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(user)
                .when()
                .post("/api/auth/login");
    }
}