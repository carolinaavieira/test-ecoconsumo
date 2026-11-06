package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.LoginModel;
import model.TokenModel;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class AuthLoginService {

    LoginModel loginModel = new LoginModel();
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    public Response response;
    String baseUrl = "https://web-ecoconsumo-fiap-caawamh6gvc5e8cj.brazilsouth-01.azurewebsites.net";
    private static String token;

    public void setFieldsAuthLogin(String field, String value) {
        switch (field) {
            case "email" -> loginModel.setEmail(value);
            case "senha" -> loginModel.setSenha(value);
            default -> throw new IllegalStateException("Unexpected field: " + field);
        }
    }

    public void accessLogin(String endpoint) {
        String url = baseUrl + endpoint;
        String body = gson.toJson(loginModel);
        response = given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(body)
                .when()
                .post(url)
                .then()
                .extract()
                .response();

    }

    public void recuperarToken() {
        if (response.getStatusCode() == HttpStatus.SC_OK) {
            token = gson.fromJson(response.jsonPath().prettify(), TokenModel.class).getToken();
        }
    }

    public String getToken() {
        return token;
    }
}
